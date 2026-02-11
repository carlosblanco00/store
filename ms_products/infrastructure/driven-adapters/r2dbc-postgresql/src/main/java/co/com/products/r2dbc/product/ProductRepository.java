package co.com.products.r2dbc.product;

import co.com.products.r2dbc.product.entity.ProductEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface ProductRepository extends ReactiveCrudRepository<ProductEntity, UUID>, ReactiveQueryByExampleExecutor<ProductEntity> {
}
