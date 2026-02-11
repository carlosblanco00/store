package co.com.products.model.product.gateways;

import co.com.products.model.product.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ProductGateway {
    Mono<Product> saveProduct(Product product);

    Mono<Product> getByID(UUID id);

    Mono<Product> update(Product product);

    Flux<Product> getAll();
}
