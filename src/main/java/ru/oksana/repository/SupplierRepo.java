package ru.oksana.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.oksana.model.Supplier;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepo extends JpaRepository<Supplier, String> {

    @Query(value = "SELECT product FROM Supplier GROUP BY product")
    List<String> findAllProducts();

    List<Supplier> findAllByProduct(String p);

    Optional<Supplier> findById(String id);
}
