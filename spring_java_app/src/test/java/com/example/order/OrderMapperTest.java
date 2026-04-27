package com.example.order;

import com.example.core.entities.order.dtos.OrderDTO;
import com.example.core.entities.order.mapper.OrderMapper;
import com.example.core.entities.order.model.Order;
import com.example.shared.exceptions.BuildException;
import com.example.shared.exceptions.GeneralDateTimeException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ordermapper")
class OrderMapperTest extends OrderTest {

    // hereda setup() de OrderTest

    @Nested
    @DisplayName("order a dto")
    class DtoFromOrder {
        @Test
        @DisplayName("convierte order a dto no null con los campos principales")
        void toDto() {
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

    // @Nested
    // @DisplayName("dto a order")
    // class OrderFromDto {
    //     // si da error, comentar el mapeo de orderpublicdto!!!
    //     @Test
    //     @DisplayName("convierte dto valido a order con datos correctos")
    //     void convertsDtoToOrder() throws BuildException, GeneralDateTimeException {
    //         OrderDTO dto = OrderMapper.dtoFromOrder(order);
    //         Order restored = OrderMapper.orderFromDTO(dto);
    //         assertAll(
    //                 () -> assertEquals(order.getOrderID(), restored.getOrderID()),
    //                 () -> assertEquals(order.getClientID(), restored.getClientID()));
    //     }

    //     @Test
    //     @DisplayName("devuelve null si el dto es null")
    //     void returnsNullForNullDto() throws BuildException, GeneralDateTimeException {
    //         assertNull(OrderMapper.orderFromDTO(null));
    //     }
    // }

    // AÑADIR TEST D ORDER DETAIL!!!!!!!!!!!!!!!
    @Nested
    @DisplayName("detail a dto")
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