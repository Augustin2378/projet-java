package com.epf.rentmanager.exception;

public class ServiceException extends Exception{

    public ServiceException(String e) {

    }

    public ServiceException(String e, DaoException e1) {
    }
}
