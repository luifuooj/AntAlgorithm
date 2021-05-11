package ru.oksana.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.oksana.model.Supplier;
import ru.oksana.repository.SupplierRepo;

import java.util.List;

@Service
public class SupplierService {

    private final SupplierRepo supplierRepo;

    @Autowired
    public SupplierService(SupplierRepo supplierRepo) {
        this.supplierRepo = supplierRepo;
    }

    public List<Supplier> findAll() {
        return supplierRepo.findAll();
    }

    public List<String> findProducts() {
        return supplierRepo.findAllProducts();
    }

    public Supplier findById(String id) {
        return supplierRepo.findById(id).orElse(null);
    }

    public List<Supplier> findSuppliersByProduct(String p) {
        return supplierRepo.findAllByProduct(p);
    }

    public void saveSupplier(Supplier s) {
        supplierRepo.save(s);
    }

    public void deleteSupplier(String id) {
        supplierRepo.deleteById(id);
    }

}
