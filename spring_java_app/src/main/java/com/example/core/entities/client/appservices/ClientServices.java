package com.example.core.entities.client.appservices;

import org.springframework.stereotype.Service;

import com.example.shared.exceptions.ServiceException;

@Service
public interface ClientServices {
    public String getByIdToJson(int id) throws ServiceException;
    public String getByIdToXml(int id) throws ServiceException;
    public String addFromJson(String client) throws ServiceException;
    public String addFromXml(String client) throws ServiceException;
    public String updateOneFromJson(String client) throws ServiceException;
    public String updateOneFromXml(String client) throws ServiceException;
    public void deleteById(int id) throws ServiceException;
}
