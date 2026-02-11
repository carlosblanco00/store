package co.com.inventory.r2dbc;

import co.com.inventory.r2dbc.entity.InventoryEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

public interface InventoryRepository extends ReactiveCrudRepository<InventoryEntity, UUID>, ReactiveQueryByExampleExecutor<InventoryEntity> {
    Mono<InventoryEntity> findByProductId(UUID id);


    @Modifying
    @Query("""
                UPDATE inventory.inventory
                SET quantity = :newStock
                WHERE id = :id AND product_id = :productId
            """)
    Mono<Integer> updateStock(@Param("id") UUID id,
                              @Param("productId") UUID productId,
                              @Param("newStock") BigDecimal newStock);

}
