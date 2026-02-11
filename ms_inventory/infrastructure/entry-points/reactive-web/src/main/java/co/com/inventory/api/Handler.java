package co.com.inventory.api;

import co.com.inventory.api.util.response.ApiResponse;
import co.com.inventory.api.util.service.ManagementErrorService;
import co.com.inventory.model.purchaserequest.PurchaseRequest;
import co.com.inventory.model.util.ConstantsSystemException;
import co.com.inventory.model.util.SystemException;
import co.com.inventory.usecase.inventory.InventoryUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@RequiredArgsConstructor
@Slf4j
public class Handler {
    private  final InventoryUseCase useCase;

    public Mono<ServerResponse> findInventoryByProduct(ServerRequest request) {
        final String id = request.pathVariable("id");

        if (!isValidUUID(id)) {
            return responseError(new SystemException(ConstantsSystemException.BAD_REQUEST_EXCEPTION));
        }

        return useCase.findProductInventory(UUID.fromString(id))
                .flatMap(product ->
                        ServerResponse.ok()
                                .contentType(APPLICATION_JSON)
                                .bodyValue(product)
                )
                .doOnSubscribe(s -> log.debug("findProduct - id={}", id))
                .onErrorResume(ex -> {
                    log.error("findProduct - unexpected error (id={})", id, ex);
                    return responseError(ex);
                });
    }

    public Mono<ServerResponse> decreaseStockOnPurchase(ServerRequest request) {

        return request.bodyToMono(PurchaseRequest.class)
                .switchIfEmpty(Mono.error(new SystemException(ConstantsSystemException.BAD_REQUEST_EXCEPTION)))
                .flatMap(pr -> useCase.decreaseStockOnPurchase(pr).collectList())
                .flatMap(created ->
                        ServerResponse
                                .created(request.uri())
                                .bodyValue(created)
                )
                .doOnSubscribe(s -> log.debug("createProduct - request received"))
                .doOnSuccess(resp -> log.debug("createProduct - response sent"))
                .onErrorResume(ex -> {
                    log.error("createProduct - unexpected error", ex);
                    return responseError(ex);
                });
    }

    private static boolean isValidUUID(String raw) {
        try {
            UUID.fromString(raw);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Mono<ServerResponse> responseError(Throwable thr){
        ApiResponse response = ApiResponse.createOnError(thr.getMessage(), thr.getMessage());
        var error = ManagementErrorService.getApiError(thr.getMessage());
        return ServerResponse.status(error.getStatus())
                .bodyValue(response);
    }

}
