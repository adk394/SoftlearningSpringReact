// package com.example;

// import static org.junit.jupiter.api.Assertions.*;

// import java.util.Optional;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.TestPropertySource;
// import org.springframework.transaction.annotation.Transactional;

// import com.example.core.entities.order.dtos.OrderDTO;
// import com.example.core.entities.order.dtos.OrderDetailDTO;
// import com.example.infraestructure.persistence.jpa.JpaOrderRepository;

// @SpringBootTest

// // TEST DE INTEGRACION CON JPA PROBAND CRUD BASICO!!!
// @TestPropertySource(properties = {
//         "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
//         "spring.datasource.driver-class-name=org.h2.Driver",
//         "spring.datasource.username=sa",
//         "spring.datasource.password=",
//         "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
//         "spring.jpa.hibernate.ddl-auto=create-drop",
//         "spring.sql.init.mode=never"
// })
// public class OrderCrudIntegrationTest {

//     @Autowired
//     private JpaOrderRepository orderRepository;

//     @Test
//     @Transactional
//     public void testCrudOrderLifecycle() {
//         // TEST DE LA ACTIVIDAD DE JPA!!
//         // prueba rapida de crud
//         orderRepository.deleteAll();

//         // create
//         OrderDTO order = new OrderDTO();
//         order.setOrderID(1001);
//         order.setClientID(42);
//         order.setDescription("pedido prueba, lorem ipsun xzy");
//         order.setDeliveryDate("2026-04-20T10:00:00");
//         order.setRecieverAddress("c/ agua 123 piso 4");
//         order.setRecieverPerson("maria garcia");
//         order.setPhoneContact("+34 900111222");
//         order.setPackageDimensionsCsv("30x20x10");

//         OrderDetailDTO d1 = new OrderDetailDTO("ref1-abc", 10.0, 0.0, 2);
//         OrderDetailDTO d2 = new OrderDetailDTO("ref2-xyz", 5.0, 0.0, 1);
//         order.addDetail(d1);
//         order.addDetail(d2);

//         OrderDTO saved = orderRepository.save(order);
//         assertNotNull(saved);
//         assertEquals(1001, saved.getOrderID());
//         assertEquals(2, saved.getShopCart().size());
//         assertTrue(saved.getShopCart().stream().allMatch(dd -> dd.getId() > 0));

//         // read
//         Optional<OrderDTO> found = orderRepository.findById(1001);
//         assertTrue(found.isPresent());
//         OrderDTO f = found.get();
//         assertEquals(2, f.getShopCart().size());

//         f.getShopCart().get(0).setAmount(3);
//         OrderDetailDTO d3 = new OrderDetailDTO("ref-3-zz", 7.5, 0.0, 1);
//         f.addDetail(d3);
//         orderRepository.save(f);

//         OrderDTO updated = orderRepository.findById(1001).get();
//         assertEquals(3, updated.getShopCart().size());
//         assertEquals(3, updated.getShopCart().get(0).getAmount());

//         // delete a detail
//         updated.removeDetail(updated.getShopCart().get(1));
//         orderRepository.save(updated);
//         OrderDTO afterDeleteDetail = orderRepository.findById(1001).get();
//         assertEquals(2, afterDeleteDetail.getShopCart().size());

//         // delete order
//         orderRepository.deleteById(1001);
//         assertFalse(orderRepository.findById(1001).isPresent());
//     }
// }
