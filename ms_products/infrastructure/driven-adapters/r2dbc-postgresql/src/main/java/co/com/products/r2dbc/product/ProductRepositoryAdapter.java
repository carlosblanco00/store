package co.com.products.r2dbc.product;

import co.com.products.model.product.Product;
import co.com.products.model.product.gateways.ProductGateway;
import co.com.products.model.util.BusinessException;
import co.com.products.model.util.ConstantsBusinessException;
import co.com.products.r2dbc.helper.ReactiveAdapterOperations;
import co.com.products.r2dbc.product.entity.ProductEntity;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

@Repository
public class ProductRepositoryAdapter extends ReactiveAdapterOperations<
        Product,
        ProductEntity,
        UUID,
        ProductRepository
        > implements ProductGateway {

    public ProductRepositoryAdapter(ProductRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Product.class));
    }


    @Override
    public Mono<Product> saveProduct(Product product) {
        return save(product);
    }

    @Override
    public Mono<Product> getByID(UUID id) {
        return findById(id);
    }

    @Override
    public Mono<Product> update(Product product) {
        return findById(product.getId())
                .filter(Objects::nonNull)
                .flatMap(this::save)
                .switchIfEmpty(Mono.error(
                        new BusinessException(
                                ConstantsBusinessException.PRODUCT_NOT_FOUNT_EXCEPTION
                        )));
    }

    @Override
    public Flux<Product> getAll() {
        return findAll();
    }
}
