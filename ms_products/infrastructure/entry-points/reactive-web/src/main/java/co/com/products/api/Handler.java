package co.com.products.api;

import co.com.products.api.util.response.ApiResponse;
import co.com.products.api.util.service.ManagementErrorService;
import co.com.products.model.product.Product;
import co.com.products.model.util.ConstantsSystemException;
import co.com.products.model.util.SystemException;
import co.com.products.usecase.product.ProductUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * Reactive HTTP handler for Product endpoints.
 * - Technical validations only (no business rules).
 * - Consistent error responses using RFC 7807 (application/problem+json).
 * - Correlation-Id propagation via "X-Correlation-Id" header.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class Handler {

    private final ProductUseCase useCase;

    private static final String HDR_CORRELATION_ID = "X-Correlation-Id";
    private static final MediaType PROBLEM_JSON = MediaType.valueOf("application/problem+json");


    public Mono<ServerResponse> createProduct(ServerRequest request) {
        final String correlationId = ensureCorrelationId(request);

        if (!isJsonContentType(request)) {
            return responseError(new SystemException(ConstantsSystemException.BAD_REQUEST_EXCEPTION));
        }

        return request.bodyToMono(Product.class)
                .switchIfEmpty(Mono.error(new SystemException(ConstantsSystemException.BAD_REQUEST_EXCEPTION)))
                .flatMap(useCase::createProduct)
                .flatMap(created ->
                        ServerResponse
                                .created(request.uri())
                                .contentType(APPLICATION_JSON)
                                .header(HDR_CORRELATION_ID, correlationId)
                                .bodyValue(created)
                )
                .doOnSubscribe(s -> log.debug("[{}] createProduct - request received", correlationId))
                .doOnSuccess(resp -> log.debug("[{}] createProduct - response sent", correlationId))
                .onErrorResume(ex -> {
                    log.error("[{}] createProduct - unexpected error", correlationId, ex);
                    return responseError(ex);
                });
    }

    public Mono<ServerResponse> findProduct(ServerRequest request) {
        final String correlationId = ensureCorrelationId(request);
        final String id = request.pathVariable("id");

        if (!isValidUUID(id)) {
            return responseError(new SystemException(ConstantsSystemException.BAD_REQUEST_EXCEPTION));
        }

        return useCase.getProductById(UUID.fromString(id))
                .flatMap(product ->
                        ServerResponse.ok()
                                .contentType(APPLICATION_JSON)
                                .header(HDR_CORRELATION_ID, correlationId)
                                .bodyValue(product)
                )
                .doOnSubscribe(s -> log.debug("[{}] findProduct - id={}", correlationId, id))
                .onErrorResume(ex -> {
                    log.error("[{}] findProduct - unexpected error (id={})", correlationId, id, ex);
                    return responseError(ex);
                });
    }

    public Mono<ServerResponse> updateProduct(ServerRequest request) {
        final String correlationId = ensureCorrelationId(request);

        if (!isJsonContentType(request)) {

            return responseError(new SystemException(ConstantsSystemException.BAD_REQUEST_EXCEPTION));
        }

        return request.bodyToMono(Product.class)
                .switchIfEmpty(Mono.error(new SystemException(ConstantsSystemException.BAD_REQUEST_EXCEPTION)))
                .flatMap(useCase::updateProduct)
                .flatMap(updated ->
                        ServerResponse.ok()
                                .contentType(APPLICATION_JSON)
                                .header(HDR_CORRELATION_ID, correlationId)
                                .bodyValue(updated)
                )
                .doOnSubscribe(s -> log.debug("[{}] updateProduct - request received", correlationId))
                .onErrorResume(ex -> {
                    log.error("[{}] updateProduct - unexpected error", correlationId, ex);
                    return responseError(ex);
                });
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        final String correlationId = ensureCorrelationId(request);

        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .header(HDR_CORRELATION_ID, correlationId)
                .body(useCase.getAllProducts(), Product.class)
                .doOnSubscribe(s -> log.debug("[{}] findAll - request received", correlationId))
                .onErrorResume(ex -> {
                    log.error("[{}] findAll - unexpected error", correlationId, ex);
                    return responseError(ex);
                });
    }

    // ---------- Helper methods ----------

    private static boolean isJsonContentType(ServerRequest request) {
        return request.headers().contentType()
                .map(ct -> APPLICATION_JSON.isCompatibleWith(ct))
                .orElse(false);
    }

    private static boolean isValidUUID(String raw) {
        try {
            UUID.fromString(raw);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static String ensureCorrelationId(ServerRequest request) {
        return Optional.ofNullable(request.headers().firstHeader(HDR_CORRELATION_ID))
                .filter(h -> !h.isBlank())
                .orElse(UUID.randomUUID().toString());
    }
    private Mono<ServerResponse> responseError(Throwable thr){
        ApiResponse response = ApiResponse.createOnError(thr.getMessage(), thr.getMessage());
        log.info("response: "+ response.toString());
        var error = ManagementErrorService.getApiError(thr.getMessage());
        return ServerResponse.status(error.getStatus())
                .bodyValue(response);
    }







}