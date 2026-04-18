package com.example.presentation.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.core.entities.order.appservices.OrderServices;
import com.example.shared.exceptions.ServiceException;

/* ejemplo de order json/xml
     LOS EJEMPLOS PARA POSTMAN ESTAN EN LA CARPETA TESTORDER (fuera del proyecto)
*/
@RestController
@RequestMapping("/api/rest/orders")
public class RestOrderController {

    @Autowired
    private OrderServices orderServices;

    // listar pedidos json
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllOrdersJson() {
        try {
            return ResponseEntity.ok("{\"message\": \"implement getAllOrdersToJson\"}");
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // obtener pedido por id json
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getJsonOrderById(@PathVariable(value = "id") int id) {
        try {
            return ResponseEntity.ok(orderServices.getByIdToJson(id));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // obtener pedido por id xml
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getXmlOrderById(@PathVariable(value = "id") int id) {
        try {
            return ResponseEntity.ok(orderServices.getByIdToXml(id));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // crear pedido desde json
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> newOrderFromJson(@RequestBody String orderData) {
        try {
            return ResponseEntity.ok(orderServices.addFromJson(orderData));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // crear pedido desde xml
    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> newOrderFromXml(@RequestBody String orderData) {
        try {
            return ResponseEntity.ok(orderServices.addFromXml(orderData));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // actualizar pedido desde json
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateOrderFromJson(@PathVariable(value = "id") int id,
            @RequestBody String orderData) {
        try {
            return ResponseEntity.ok(orderServices.updateOneFromJson(orderData));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // actualizar pedido desde xml
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> updateOrderFromXml(@PathVariable(value = "id") int id,
            @RequestBody String orderData) {
        try {
            return ResponseEntity.ok(orderServices.updateOneFromXml(orderData));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // eliminar pedido
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable(value = "id") int id) {
        try {
            orderServices.deleteById(id);
            return ResponseEntity.ok("pedido eliminado");
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
