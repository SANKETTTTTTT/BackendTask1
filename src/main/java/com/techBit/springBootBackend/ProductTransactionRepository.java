import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTransactionRepository extends JpaRepository<ProductTransaction, Long> {
    List<ProductTransaction> findByDateOfSaleContaining(String month, Pageable pageable);
    List<ProductTransaction> findByDateOfSaleContainingAndTitleContaining(String month, String title, Pageable pageable);
    List<ProductTransaction> findByDateOfSaleContainingAndDescriptionContaining(String month, String description, Pageable pageable);
    List<ProductTransaction> findByDateOfSaleContainingAndPrice(String month, double price, Pageable pageable);
    List<ProductTransaction> findByDateOfSaleContaining(String month);
}