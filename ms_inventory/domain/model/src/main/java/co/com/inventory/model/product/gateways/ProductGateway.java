package co.com.inventory.model.product.gateways;

import co.com.inventory.model.product.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ProductGateway {

    Mono<Product> getByID(UUID id);
}
