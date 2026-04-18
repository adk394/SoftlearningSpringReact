package com.example.core.entities.order.persistence;

import java.util.Optional;
import com.example.core.entities.order.dtos.OrderDTO;

public interface OrderRepository {

    public Optional<OrderDTO> findById(int id);

    public OrderDTO save(OrderDTO order);

    public void deleteById(int id);
}
