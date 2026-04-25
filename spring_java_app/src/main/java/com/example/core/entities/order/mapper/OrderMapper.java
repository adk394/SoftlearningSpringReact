package com.example.core.entities.order.mapper;

import java.util.stream.Collectors;

import com.example.core.entities.order.model.Order;
import com.example.core.entities.order.model.OrderDetail;
import com.example.core.entities.order.dtos.OrderDTO;
import com.example.core.entities.order.dtos.OrderDetailDTO;
import com.example.core.entities.order.dtos.OrderPublicDTO;
import com.example.shared.exceptions.BuildException;
import com.example.shared.exceptions.GeneralDateTimeException;

public class OrderMapper {

    // convierte order a dto
    public static OrderDTO dtoFromOrder(Order order) {
        if (order == null) {
            return null;
        }

        String phoneContact = order.getPhoneContacts() == null ? null : String.join(",", order.getPhoneContacts());
        if (phoneContact != null && phoneContact.trim().isEmpty()) {
            phoneContact = null;
        }

        java.util.List<OrderDetailDTO> shopCart = order.getShopCartList().stream()
            .map(OrderMapper::detailDtoFromDetail)
            .collect(Collectors.toList());

        return new OrderDTO(
                order.getOrderID(),
                order.getClientID(),
                order.getRecieverAddress(),
                order.getRecieverPerson(),
                order.getPaymentDate(),
                order.getDeliveryDate(),
                phoneContact,
                order.getStatus(),
                order.getStartDateSafe(),
                order.getDescription(),
                order.getPackageDimensionsCsv(),
                shopCart
        );
    }

    // convierte dto a order
    public static Order orderFromDTO(OrderDTO dto) throws BuildException, GeneralDateTimeException {
        if (dto == null) {
            return null;
        }

        String shopCartDetails = null;
        if (dto.getShopCart() != null && !dto.getShopCart().isEmpty()) {
            String joined = dto.getShopCart().stream()
                    .map(d -> d.getRef() + "," + d.getPrice() + "," + d.getDiscount() + "," + d.getAmount())
                    .collect(Collectors.joining(";"));
            if (joined != null && !joined.trim().isEmpty()) {
                shopCartDetails = joined;
            }
        }

        String phoneContacts = dto.getPhoneContact();

        return Order.getInstance(
                dto.getOrderID(),
                dto.getClientID(),
                dto.getStartDate(),
                dto.getDescription(),
                dto.getRecieverAddress(),
                dto.getRecieverPerson(),
                phoneContacts,
                dto.getPaymentDate(),
                dto.getDeliveryDate(),
                dto.getPackageDimensionsCsv(),
                shopCartDetails
        );
    }

    // convierte order detail a dto
    public static OrderDetailDTO detailDtoFromDetail(OrderDetail detail) {
        if (detail == null) {
            return null;
        }
        return new OrderDetailDTO(
                detail.getRef(),
                detail.getPrice(),
                detail.getDiscount(),
                detail.getAmount()
        );
    }

    // convierte dto a order detail
    public static OrderDetail detailFromDetailDto(OrderDetailDTO dto) {
        if (dto == null) {
            return null;
        }
        return new OrderDetail(
                dto.getRef(),
                dto.getPrice(),
                dto.getDiscount(),
                dto.getAmount()
        );
    }

    // convierte dto a order public view sin alguns camps sensibles
    public static OrderPublicDTO orderPublicDtoFromDto(OrderDTO dto) {
        if (dto == null) {
            return null;
        }
        return new OrderPublicDTO(
                dto.getOrderID(),
                dto.getClientID(),
                dto.getRecieverAddress(),
                dto.getRecieverPerson(),
                dto.getPhoneContact(),
                dto.getStartDate(),
                dto.getDescription(),
                dto.getPackageDimensionsCsv(),
                dto.getShopCart()
        );
    }

    // convierte OrderPublicDTO (vista pública) a OrderDTO (entidad JPA-friendly)
    public static OrderDTO dtoFromPublicDto(OrderPublicDTO publicDto) {
        if (publicDto == null) {
            return null;
        }

        // Construir OrderDTO con lista vacía para que setShopCart(...) establezca el enlace bidireccional
        OrderDTO result = new OrderDTO(
                publicDto.getOrderID(),
                publicDto.getClientID(),
                publicDto.getRecieverAddress(),
                publicDto.getRecieverPerson(),
                null, // paymentDate (no expuesto en public)
                null, // deliveryDate (no expuesto en public)
                publicDto.getPhoneContact(),
                null, // status
                publicDto.getStartDate(),
                publicDto.getDescription(),
                publicDto.getPackageDimensionsCsv(),
                null
        );

        if (publicDto.getShopCart() != null) {
            for (OrderDetailDTO detail : publicDto.getShopCart()) {
                // Forzar id a 0 para que JPA genere un nuevo id al persistir
                detail.setId(0);
                result.addDetail(detail);
            }
        }

        return result;
    }
}
