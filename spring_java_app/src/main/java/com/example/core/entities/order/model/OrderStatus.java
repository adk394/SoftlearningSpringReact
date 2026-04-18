package com.example.core.entities.order.model;

public enum OrderStatus {
    CREATED, //solo se ha creado el order, pendiente de confirmacion
    CANCELLED, //order cancelado
    CONFIRMED, //carrito validado y pagado
    FORTHCOMING, // paquete preparado y almacenado pendiente de envio
    DELIVERED, // paquete entregado al transportista y pendiente de entrega al cliente
    FINISHED        // envio entregado al cliente
}
