package ro.msg.learning.shop.controller.mapper;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.SupplierDTO;

import ro.msg.learning.shop.model.Supplier;

@Component
public class SupplierMapper {

    public Supplier convertFromDTO(SupplierDTO supplierDTO) {
        return Supplier.builder()
                .id(supplierDTO.getId())
                .name(supplierDTO.getName())
                .build();
    }

    public SupplierDTO convertFromEntity(Supplier supplier) {
        return SupplierDTO.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .build();
    }
}
