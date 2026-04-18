package com.example;

import org.springframework.context.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.infraestructure.persistence.jpa.JpaBookRepository;
import com.example.infraestructure.persistence.jpa.JpaClientRepository;
import com.example.infraestructure.persistence.jpa.JpaOrderRepository;

import com.example.core.entities.client.dtos.ClientDTO;
import com.example.core.entities.book.dtos.BookDTO;
import com.example.core.entities.order.dtos.OrderDTO;
import com.example.core.entities.order.dtos.OrderDetailDTO;
import com.example.core.entities.book.appservices.BookServicesImpl;
import com.example.core.entities.client.appservices.ClientServicesImpl;
import com.example.core.entities.order.appservices.OrderServicesImpl;

import com.example.shared.exceptions.ServiceException;

import java.util.Set;

@SpringBootApplication
public class SoftlearningApplication {

        public static void main(String[] args) {

                ApplicationContext context = SpringApplication.run(SoftlearningApplication.class, args);

                // /*
                //  * ============================================================
                //  * BLOQUE 1: REPOSITORIOS JPA
                //  * ===============================cd =============================
                //  */

                // System.out.println("\n\n==========  REPOSITORY TESTS (JPA)  ==========\n");

                // /*
                //  * -------------------------
                //  * BOOK REPOSITORY
                //  * -------------------------
                //  */

                // JpaBookRepository bookRepo = context.getBean(JpaBookRepository.class);

                // System.out.println("\n *****   Books in the repository   ***** \n");
                // bookRepo.findAll().forEach(System.out::println);

                // System.out.println("\n *****   Java Books by title  ***** \n");
                // bookRepo.findByTitle("java").forEach(System.out::println);

                // System.out.println("\n *****   Add a new Java Book  ***** \n");
                // bookRepo.save(new BookDTO("Java SpringJPA", 138, 29.99, "Programmers",
                //                 "isbnproves4", "2024-04-26", "Princeton", 0, 0, 0, 0));

                // System.out.println("\n *****   Java Books by partial title  ***** \n");
                // bookRepo.findByPartialTitle("java").forEach(System.out::println);

                // System.out.println("\n *****   Update a Java Book  ***** \n");
                // bookRepo.save(new BookDTO("SpringJPA", 138, 29.99, "Programmers",
                //                 "isbnproves4", "2024-04-26", "Princeton", 0, 0, 0, 0));

                // System.out.println("\n *****   Books by id   ***** \n");
                // bookRepo.findById(138).ifPresent(System.out::println);

                // System.out.println("\n *****   Java Books available: "
                //                 + bookRepo.countByPartialTitle("Java") + " *****\n");

                // /*
                //  * -------------------------
                //  * CLIENT REPOSITORY
                //  * -------------------------
                //  */

                // JpaClientRepository clientRepo = context.getBean(JpaClientRepository.class);

                // System.out.println("\n *****   Clients in the repository   ***** \n");
                // clientRepo.findAll().forEach(System.out::println);

                // System.out.println("\n *****   Clients by name  ***** \n");
                // clientRepo.findByNamePerson("John").forEach(System.out::println);

                // System.out.println("\n *****   Add a new Client  ***** \n");
                // clientRepo.save(new ClientDTO(11, "33101123-F", "john@example.com", "611111111", "C/izan 15",
                //                 "John update", "2025-05-20"));

                // System.out.println("\n *****   Clients by partial name  ***** \n");
                // clientRepo.findByPartialNamePerson("John").forEach(System.out::println);

                // System.out.println("\n *****   Update a Client  ***** \n");
                // clientRepo.save(new ClientDTO(11, "33101123-F", "john@example.com", "611111111", "C/izan 15",
                //                 "John update", "2025-05-20"));

                // System.out.println("\n ***** Clients after insert ***** \n");
                // clientRepo.findAll().forEach(System.out::println);

                // System.out.println("\n *****   Clients by id   ***** \n");
                // clientRepo.findById(11).ifPresent(System.out::println);

                // System.out.println("\n *****   Clients available: "
                //                 + clientRepo.countByPartialNamePerson("John") + " *****\n");

                // /*
                //  * -------------------------
                //  * ORDER REPOSITORY
                //  * -------------------------
                //  */

                // JpaOrderRepository orderRepo = context.getBean(JpaOrderRepository.class);

                // System.out.println("\n *****   Orders in the repository   ***** \n");
                // orderRepo.findAll().forEach(System.out::println);

                // System.out.println("\n *****   Orders by status  ***** \n");
                // orderRepo.findByStatus("EN_PREPARACION").forEach(System.out::println);

                // System.out.println("\n *****   Add a new Order  ***** \n");

                // OrderDTO order = new OrderDTO(
                //                 2001,
                //                 501,
                //                 "Calle Mayor 123, Madrid",
                //                 "Laura Martínez",
                //                 "2024-11-15",
                //                 "2024-11-20",
                //                 "+34 600 123 456",
                //                 "EN_PREPARACION",
                //                 "2024-11-10",
                //                 "Pedido generado desde App",
                //                 "30x20x15",
                //                 new java.util.ArrayList<>());

                // // crear el detalle
                // OrderDetailDTO detail = new OrderDetailDTO(
                //                 "REF123",
                //                 49.99,
                //                 5.00,
                //                 2);

                // // esto enlaza los 2 lados
                // detail.setOrder(order);
                // order.getShopCart().add(detail);
                // orderRepo.save(order);

                // System.out.println("\n *****   Orders by partial status  ***** \n");
                // orderRepo.findByPartialStatus("PREPARA").forEach(System.out::println);

                // System.out.println("\n *****   Update an Order  ***** \n");

                // OrderDTO orderUpdate = new OrderDTO(
                //                 2001,
                //                 501,
                //                 "Calle Mayor 123, Madrid",
                //                 "Laura Martínez",
                //                 "2024-11-15",
                //                 "2024-11-20",
                //                 "+34 600 123 456",
                //                 "ENVIADO",
                //                 "2024-11-10",
                //                 "Pedido actualizado",
                //                 "30x20x15",
                //                 new java.util.ArrayList<>());

                // OrderDetailDTO detailUpdate = new OrderDetailDTO(
                //                 "REF123",
                //                 49.99,
                //                 5.00,
                //                 2);

                // detailUpdate.setOrder(orderUpdate);
                // orderUpdate.getShopCart().add(detailUpdate);
                // orderRepo.save(orderUpdate);

                // System.out.println("\n *****   Orders by id   ***** \n");
                // orderRepo.findById(2001).ifPresent(System.out::println);

                // System.out.println("\n *****   Orders available: "
                //                 + orderRepo.countByPartialStatus("PREPARA") + " *****\n");

                // // System.out.println("\n ***** Delete an Order ***** \n"); //
                // // orderRepo.deleteById(2001);

                // /*
                //  * ============================================================
                //  * BLOQUE 2: APPLICATION SERVICES
                //  * ============================================================
                //  */

                // System.out.println("\n\n==========  APPLICATION SERVICES TESTS  ==========\n");

                // /*
                //  * -------------------------
                //  * BOOK SERVICES
                //  * -------------------------
                //  */
                // var bookServices = context.getBean(BookServicesImpl.class);

                // System.out.println("\n *****   AppServices Books_by_id   ***** \n");

                // try {
                //         System.out.println("\n *****   JSON DOCUMENT   ***** \n");
                //         System.out.println(bookServices.getByIdToJson(137));

                //         System.out.println("\n *****   XML DOCUMENT   ***** \n");
                //         System.out.println(bookServices.getByIdToXml(137));

                //         // bookServices.deleteById(101);

                // } catch (ServiceException e) {
                //         System.out.println("\n - - - - " + e.getMessage() + " - - - - \n");
                // }

                // /*
                //  * -------------------------
                //  * CLIENT SERVICES
                //  * -------------------------
                //  */
                // var clientServices = context.getBean(ClientServicesImpl.class);

                // System.out.println("\n *****   AppServices Clients_by_id   ***** \n");

                // try {
                //         System.out.println("\n *****   JSON DOCUMENT   ***** \n");
                //         System.out.println(clientServices.getByIdToJson(11));

                //         System.out.println("\n *****   XML DOCUMENT   ***** \n");
                //         System.out.println(clientServices.getByIdToXml(11));

                //         // clientServices.deleteById(1001);

                // } catch (ServiceException e) {
                //         System.out.println("\n - - - - " + e.getMessage() + " - - - - \n");
                // }

                // /*
                //  * -------------------------
                //  * ORDER SERVICES
                //  * -------------------------
                //  */
                // var orderServices = context.getBean(OrderServicesImpl.class);

                // System.out.println("\n *****   AppServices Orders_by_id   ***** \n");

                // try {
                //         System.out.println("\n *****   JSON DOCUMENT   ***** \n");
                //         System.out.println(orderServices.getByIdToJson(2001));

                //         System.out.println("\n *****   XML DOCUMENT   ***** \n");
                //         System.out.println(orderServices.getByIdToXml(2001));


                // } catch (ServiceException e) {
                //         System.out.println("\n - - - - " + e.getMessage() + " - - - - \n");
                // }

                // //cambiar el id autoincrementativo de los detalles de order

                // System.out.println("\n\n==========  END OF TESTS  ==========\n")
                // ;
        }
}

