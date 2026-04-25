package com.example.order;

import com.example.core.entities.order.dtos.OrderDTO;
import com.example.core.entities.order.mapper.OrderMapper;
import com.example.core.entities.order.model.Order;
import com.example.shared.exceptions.BuildException;
import com.example.shared.exceptions.GeneralDateTimeException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ordermapper - conversion entre order y orderdto")
class OrderMapperTest extends OrderTest {

    // hereda setup() de OrderTest -> 'order' ya inicializado

    @Nested
    @DisplayName("dtofromorder - order -> orderdto")
    class DtoFromOrder {
        @Test
        @DisplayName("convierte order a dto no null con los campos principales")
        void convertsToDtoCorrectly() {
            OrderDTO dto = OrderMapper.dtoFromOrder(order);
            assertAll(
                    () -> assertNotNull(dto),
                    () -> assertEquals(order.getOrderID(), dto.getOrderID()),
                    () -> assertEquals(order.getClientID(), dto.getClientID()),
                    () -> assertEquals(order.getDescription(), dto.getDescription()),
                    () -> assertNotNull(dto.getShopCart()));
        }

        @Test
        @DisplayName("devuelve null si el order es null")
        void returnsNullForNullOrder() {
            assertNull(OrderMapper.dtoFromOrder(null));
        }
    }

    @Nested
    @DisplayName("orderfromdto - dto -> order")
    class OrderFromDto {
        @Test
        @DisplayName("convierte dto valido a order con datos correctos")
        void convertsDtoToOrder() throws BuildException, GeneralDateTimeException {
            OrderDTO dto = OrderMapper.dtoFromOrder(order);
            Order restored = OrderMapper.orderFromDTO(dto);
            assertAll(
                    () -> assertEquals(order.getOrderID(), restored.getOrderID()),
                    () -> assertEquals(order.getClientID(), restored.getClientID()));
        }

        @Test
        @DisplayName("devuelve null si el dto es null")
        void returnsNullForNullDto() throws BuildException, GeneralDateTimeException {
            assertNull(OrderMapper.orderFromDTO(null));
        }
    }

    @Nested
    @DisplayName("detaildtofromdetail - orderdetail -> orderdetaildto")
    class DetailDtoFromDetail {
        @Test
        @DisplayName("convierte orderdetail correctamente")
        void convertsDetailToDto() {
            order.setShopCartDetails("B001,19.99,2.0,3");
            var detail = order.getShopCartList().get(0);
            var dto = OrderMapper.detailDtoFromDetail(detail);
            assertAll(
                    () -> assertEquals(detail.getRef(), dto.getRef()),
                    () -> assertEquals(detail.getPrice(), dto.getPrice()),
                    () -> assertEquals(detail.getDiscount(), dto.getDiscount()),
                    () -> assertEquals(detail.getAmount(), dto.getAmount()));
        }

        @Test
        @DisplayName("devuelve null si el detail es null")
        void returnsNullForNullDetail() {
            assertNull(OrderMapper.detailDtoFromDetail(null));
        }
    }
}