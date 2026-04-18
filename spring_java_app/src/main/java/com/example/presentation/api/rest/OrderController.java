package com.example.presentation.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.core.entities.order.dtos.OrderDTO;
import com.example.core.entities.order.dtos.OrderDetailDTO;
import com.example.core.entities.order.dtos.OrderPublicDTO;
import com.example.core.entities.order.mapper.OrderMapper;
import com.example.infraestructure.persistence.jpa.JpaOrderRepository;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private JpaOrderRepository orderRepository;

    @GetMapping
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/public")
    public List<com.example.core.entities.order.dtos.OrderPublicDTO> getAllPublicOrders() {
        return orderRepository.findAll().stream()
                .map(com.example.core.entities.order.mapper.OrderMapper::orderPublicDtoFromDto)
                .toList();
    }

    @GetMapping("/{id}")
    public OrderDTO getOrderById(@PathVariable int id) {
        return orderRepository.findById(id).orElse(null);
    }

    @PostMapping
    public OrderDTO createOrder(@RequestBody OrderDTO order) {
        // limpiar ids de detalles para generacion automatica
        if (order.getShopCart() != null) {
            for (OrderDetailDTO detail : order.getShopCart()) {
                detail.setId(0); // forzar id nuevo
                detail.setOrder(order);
            }
        }
        return orderRepository.save(order);
    }

    @PostMapping("/public")
    public OrderDTO createOrderFromPublic(@RequestBody OrderPublicDTO publicOrder) {
        OrderDTO order = OrderMapper.dtoFromPublicDto(publicOrder);
        // asegurar detalles sin id y enlazados
        if (order.getShopCart() != null) {
            for (OrderDetailDTO detail : order.getShopCart()) {
                detail.setId(0);
                detail.setOrder(order);
            }
        }
        return orderRepository.save(order);
    }

    @PutMapping("/{id}")
    public OrderDTO updateOrder(@PathVariable int id, @RequestBody OrderDTO order) {
        order.setOrderID(id);
        if (order.getShopCart() != null) {
            for (OrderDetailDTO detail : order.getShopCart()) {
                // si id es 0 o falta conservar 0 para insertar nuevo; asegurar enlace bidireccional
                detail.setOrder(order);
            }
        }
        return orderRepository.save(order);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable int id) {
        orderRepository.deleteById(id);
    }

    @GetMapping("/public/{id}")
    public OrderPublicDTO getPublicOrderById(@PathVariable int id) {
        OrderDTO dto = orderRepository.findById(id).orElse(null);
        return OrderMapper.orderPublicDtoFromDto(dto);
    }

}
