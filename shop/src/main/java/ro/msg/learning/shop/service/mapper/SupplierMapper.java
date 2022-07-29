package ro.msg.learning.shop.service.mapper;

import ro.msg.learning.shop.dto.SupplierDTO;

import ro.msg.learning.shop.model.Supplier;

public class SupplierMapper {
    public static Supplier convertFromDTO(SupplierDTO supplierDTO) {
        return Supplier.builder()
                .id(supplierDTO.getId())
                .name(supplierDTO.getName())
                .build();

    }

    public static SupplierDTO convertFromEntity(Supplier supplier) {
        return SupplierDTO.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .build();
    }
}
