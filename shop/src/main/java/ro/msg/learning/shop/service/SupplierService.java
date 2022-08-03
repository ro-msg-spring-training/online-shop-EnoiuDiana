package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.model.Supplier;
import ro.msg.learning.shop.repository.SupplierRepository;

@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;

    public Supplier findSupplierById(int id) {
        return supplierRepository.getReferenceById(id);
    }

    public boolean existsById(int id) {
        return supplierRepository.existsById(id);
    }
}
