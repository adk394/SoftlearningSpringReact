package com.example.core.entities.order.appservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.core.entities.order.persistence.OrderRepository;
import com.example.infraestructure.persistence.jpa.JpaOrderRepository;
import com.example.core.entities.order.dtos.OrderDTO;
import com.example.services.serializers.Serializer;
import com.example.services.serializers.Serializers;
import com.example.services.serializers.SerializersCatalog;
import com.example.shared.exceptions.ServiceException;
import jakarta.transaction.Transactional;


@Controller
public class OrderServicesImpl implements OrderServices {

    @Autowired
    private JpaOrderRepository orderRepository;
    private Serializer<OrderDTO> serializer;

    // obtener dto por id
    protected OrderDTO getDTO(int id) {
        return orderRepository.findById(id).orElse(null);
    }

    // obtener y validar que existe
    protected OrderDTO getById(int id) throws ServiceException {
        OrderDTO odto = this.getDTO(id);
        if (odto == null) {
            throw new ServiceException("pedido " + id + " no encontrado");
        }
        return odto;
    }

    // validar datos de entrada
    protected OrderDTO checkInputData(String orderJson, Serializers serializerType) throws ServiceException {
        try {
            this.serializer = SerializersCatalog.getInstance(serializerType);
            OrderDTO odto = serializer.deserialize(orderJson, OrderDTO.class);
            if (odto == null) {
                throw new ServiceException("No se pudo deserializar el pedido");
            }
            return odto;
        } catch (Exception e) {
            throw new ServiceException("error en datos del pedido: " + e.getMessage());
        }
    }

    // crear nuevo pedido
    protected OrderDTO newOrder(String orderData, Serializers serializerType) throws ServiceException {
        OrderDTO odto = this.checkInputData(orderData, serializerType);
        if (this.getDTO(odto.getOrderID()) == null) {
            return orderRepository.save(odto);
        }
        throw new ServiceException("pedido " + odto.getOrderID() + " ya existe");
    }

    // actualizar pedido
    protected OrderDTO updateOrder(String orderData, Serializers serializerType) throws ServiceException {
        try {
            OrderDTO odto = this.checkInputData(orderData, serializerType);
            this.getById(odto.getOrderID());
            return orderRepository.save(odto);
        } catch (ServiceException e) {
            throw e;
        }
    }

    @Override
    @Transactional
    public String getByIdToJson(int id) throws ServiceException {
        try {
            OrderDTO odto = this.getById(id);
            return SerializersCatalog.getInstance(Serializers.JSON_ORDER).serialize(odto);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("Error serializando pedido a JSON: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public String getByIdToXml(int id) throws ServiceException {
        try {
            OrderDTO odto = this.getById(id);
            return SerializersCatalog.getInstance(Serializers.XML_ORDER).serialize(odto);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("Error serializando pedido a XML: " + e.getMessage(), e);
        }
    }

    @Override
    public String addFromJson(String order) throws ServiceException {
        try {
            this.serializer = SerializersCatalog.getInstance(Serializers.JSON_ORDER);
            return serializer.serialize(this.newOrder(order, Serializers.JSON_ORDER));
        } catch (Exception e) {
            throw new ServiceException("Error añadiendo pedido desde JSON", e);
        }
    }

    @Override
    public String addFromXml(String order) throws ServiceException {
        try {
            this.serializer = SerializersCatalog.getInstance(Serializers.XML_ORDER);
            return serializer.serialize(this.newOrder(order, Serializers.XML_ORDER));
        } catch (Exception e) {
            throw new ServiceException("Error añadiendo pedido desde XML", e);
        }
    }

    @Override
    public String updateOneFromJson(String order) throws ServiceException {
        try {
            this.serializer = SerializersCatalog.getInstance(Serializers.JSON_ORDER);
            return serializer.serialize(this.updateOrder(order, Serializers.JSON_ORDER));
        } catch (Exception e) {
            throw new ServiceException("Error actualizando pedido desde JSON", e);
        }
    }

    @Override
    public String updateOneFromXml(String order) throws ServiceException {
        try {
            this.serializer = SerializersCatalog.getInstance(Serializers.XML_ORDER);
            return serializer.serialize(this.updateOrder(order, Serializers.XML_ORDER));
        } catch (Exception e) {
            throw new ServiceException("Error actualizando pedido desde XML", e);
        }
    }

    @Override
    public void deleteById(int id) throws ServiceException {
        try {
            this.getById(id);
            orderRepository.deleteById(id);
        } catch (ServiceException e) {
            throw e;
        }
    }

    //  @Override
    // public void deleteByIdDetail(int id) throws ServiceException {
    //     try {
    //         this.getById(id);
    //         orderRepository.deleteByIdDetail(id);
    //     } catch (ServiceException e) {
    //         throw e;
    //     }
    // }
}
