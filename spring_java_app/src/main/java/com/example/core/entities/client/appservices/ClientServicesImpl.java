package com.example.core.entities.client.appservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.infraestructure.persistence.jpa.JpaClientRepository;
import com.example.core.entities.client.dtos.ClientDTO;
import com.example.services.serializers.Serializer;
import com.example.services.serializers.Serializers;
import com.example.services.serializers.SerializersCatalog;
import com.example.shared.exceptions.ServiceException;

import jakarta.transaction.Transactional;

@Controller
public class ClientServicesImpl implements ClientServices {

    @Autowired
    private JpaClientRepository clientRepository;
    private Serializer<ClientDTO> serializer;

    // obtener dto por id
    protected ClientDTO getDTO(int id) {
        return clientRepository.findById(id).orElse(null);
    }

    // obtener y validar que existe
    protected ClientDTO getById(int id) throws ServiceException {
        ClientDTO cdto = this.getDTO(id);
        if (cdto == null) {
            throw new ServiceException("cliente " + id + " no encontrado");
        }
        return cdto;
    }

    // validar datos de entrada
    protected ClientDTO checkInputData(String clientJson) throws ServiceException {
        try {
            // aqui iria deserializacion con serializer
            // por ahora solo validamos que se puede crear el cliente
            ClientDTO cdto = new ClientDTO(0, clientJson, clientJson, clientJson, clientJson, clientJson, clientJson);
            // la logica de deserializacion dependera de tu serializer
            return cdto;
        } catch (Exception e) {
            throw new ServiceException("error en datos del cliente: " + e.getMessage());
        }
    }

    // crear nuevo cliente

    protected ClientDTO newClient(String clientData) throws ServiceException {
        ClientDTO cdto = this.checkInputData(clientData);
        if (this.getDTO(cdto.getId()) == null) {
            return clientRepository.save(cdto);
        }
        throw new ServiceException("cliente " + cdto.getId() + " ya existe");
    }

    // actualizar cliente
    protected ClientDTO updateClient(String clientData) throws ServiceException {
        try {
            ClientDTO cdto = this.checkInputData(clientData);
            this.getById(cdto.getId());
            return clientRepository.save(cdto);
        } catch (ServiceException e) {
            throw e;
        }
    }

    @Override
    public String getByIdToJson(int id) throws ServiceException {
        try {
            ClientDTO cdto = this.getById(id);
            return SerializersCatalog.getInstance(Serializers.JSON_CLIENT).serialize(cdto);
        } catch (Exception e) {
            throw new ServiceException("Error serializando cliente a JSON", e);
        }
    }

    @Override
    public String getByIdToXml(int id) throws ServiceException {
        try {
            ClientDTO cdto = this.getById(id);
            return SerializersCatalog.getInstance(Serializers.XML_CLIENT).serialize(cdto);
        } catch (Exception e) {
            throw new ServiceException("Error serializando cliente a XML", e);
        }
    }

    @Override
    public String addFromJson(String client) throws ServiceException {
        try {
            this.serializer = SerializersCatalog.getInstance(Serializers.JSON_CLIENT);
            return serializer.serialize(this.newClient(client));
        } catch (Exception e) {
            throw new ServiceException("Error añadiendo cliente desde JSON", e);
        }
    }

    @Override
    public String addFromXml(String client) throws ServiceException {
        try {
            this.serializer = SerializersCatalog.getInstance(Serializers.XML_CLIENT);
            return serializer.serialize(this.newClient(client));
        } catch (Exception e) {
            throw new ServiceException("Error añadiendo cliente desde XML", e);
        }
    }

    @Override
    public String updateOneFromJson(String client) throws ServiceException {
        try {
            this.serializer = SerializersCatalog.getInstance(Serializers.JSON_CLIENT);
            return serializer.serialize(this.updateClient(client));
        } catch (Exception e) {
            throw new ServiceException("Error actualizando cliente desde JSON", e);
        }
    }

    @Override
    public String updateOneFromXml(String client) throws ServiceException {
        try {
            this.serializer = SerializersCatalog.getInstance(Serializers.XML_CLIENT);
            return serializer.serialize(this.updateClient(client));
        } catch (Exception e) {
            throw new ServiceException("Error actualizando cliente desde XML", e);
        }
    }

    @Override
    public void deleteById(int id) throws ServiceException {
        try {
            this.getById(id);
            clientRepository.deleteById(id);
        } catch (ServiceException e) {
            throw e;
        }
    }
}
