package co.com.products.usecase.product;

import co.com.products.model.product.Product;
import co.com.products.model.product.gateways.ProductGateway;
import co.com.products.model.util.BusinessException;
import co.com.products.model.util.ConstantsBusinessException;
import lombok.RequiredArgsConstructor;
import lombok.NonNull;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

@Log
@RequiredArgsConstructor
public final class ProductUseCase {

    private final ProductGateway productGateway;

    /**
     * Creates a new product.
     *
     * @param product the product to create (must be non-null)
     * @return Mono emitting the created product
     */
    public Mono<Product> createProduct(@NonNull Product product) {
        Objects.requireNonNull(product, "product must not be null");
        log.info("name: " + product.toString());
        return productGateway
                .saveProduct(product)
                .name("ProductUseCase#createProduct")
                .doOnSuccess(p -> {log.info("Product saved: " + p.toString());})
                .doOnError(e -> {log.error("Error: " + e.getMessage());});
    }

    /**
     * Retrieves a product by its identifier
     * Contract:
     * - Emits Product if found.
     * - Emits BusinessException if not found.
     *
     * @param id product id (must be non-null)
     */
    public Mono<Product> getProductById(@NonNull UUID id) {
        Objects.requireNonNull(id, "id must not be null");
        return productGateway
                .getByID(id)
                .switchIfEmpty(Mono.error(new BusinessException(ConstantsBusinessException.PRODUCT_NOT_FOUNT_EXCEPTION)))
                .name("ProductUseCase#getProductById")
                // .tag("productId", id)
                .doOnSuccess(p -> {log.info("Product retrieved: " + p.toString());})
                .doOnError(e -> {log.error("Error: " + e.getMessage());});
    }

    /**
     * Updates an existing product
     * Contract:
     * - Emits updated Product if the product exists and is updated.
     * - Emits ProductNotFoundException if the product does not exist.
     * - This method does NOT perform validation, only delegates and standardizes errors.
     *
     * @param product product to update (must be non-null)
     */
    public Mono<Product> updateProduct(@NonNull Product product) {
        Objects.requireNonNull(product, "product must not be null");
        return productGateway
                .update(product)
                .switchIfEmpty(Mono.error(new BusinessException(ConstantsBusinessException.PRODUCT_NOT_FOUNT_EXCEPTION)))
                .name("ProductUseCase#updateProduct")
                // .tag("productId", safeId(product))
                .doOnSuccess(p -> {log.info("Product updated: " + p.toString());})
                .doOnError(e -> {log.error("Error: " + e.getMessage());});
    }

    /**
     * Retrieves all products.
     *
     * @return Flux emitting all products (can be empty)
     */
    public Flux<Product> getAllProducts() {
        return productGateway
                .getAll()
                .name("ProductUseCase#getAllProducts");
    }
}