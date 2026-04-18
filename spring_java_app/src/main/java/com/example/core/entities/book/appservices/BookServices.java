package com.example.core.entities.book.appservices;

import org.springframework.stereotype.Service;
import com.example.shared.exceptions.ServiceException;

@Service
public interface BookServices {
    public String getByIdToJson (int id) throws ServiceException;
    public String getByIdToXml (int id) throws ServiceException;
    public String addFromJson (String book) throws ServiceException;
    public String addFromXml (String book) throws ServiceException, com.example.shared.exceptions.ServiceException;
    public String updateOneFromJson(String book) throws ServiceException;
    public String updateOneFromXml(String book) throws ServiceException;
    public void deleteById(int id) throws ServiceException;
}
