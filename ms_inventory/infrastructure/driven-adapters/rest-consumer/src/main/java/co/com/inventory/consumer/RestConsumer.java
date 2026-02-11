package co.com.inventory.consumer;

import co.com.inventory.model.product.Product;
import co.com.inventory.model.product.gateways.ProductGateway;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestConsumer implements ProductGateway {

    private final WebClient client;

    @Override
    @CircuitBreaker(name = "findProducts")
    public Mono<Product> getByID(UUID id) {

        return client
                .get()
                .uri("/api/products/v1/{id}", id)
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToMono(Product.class);
                    }
                    return response
                            .bodyToMono(String.class)
                            .defaultIfEmpty("")
                            .flatMap(body -> Mono.error(new RuntimeException(
                                    "GET /products/%s failed: %s %s".formatted(id, response.statusCode(), body)
                            )));
                });

    }
}
