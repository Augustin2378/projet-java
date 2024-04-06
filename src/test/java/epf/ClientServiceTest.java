package com.epf.rentmanager;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Mock;

import java.sql.Date;
import java.time.LocalDate;

public class ClientServiceTest {
    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientDao clientDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAll_should_fail_when_dao_throws_exception() throws DaoException {
        when(this.clientDao.findAll()).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> clientService.findAll());
    }

    @Test
    public void create_should_fail_when_last_name_is_empty() throws DaoException {
        Client client = new Client("","evan","evan@test.com", Date.valueOf(LocalDate.of(2002, 10, 18)));
        when(this.clientDao.create(client)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> clientService.create(client));
    }

    @Test
    public void create_should_fail_when_first_name_is_empty() throws DaoException {
        Client client = new Client("durant","","evan@test.com", Date.valueOf(LocalDate.of(2002, 10, 18)));
        when(this.clientDao.create(client)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> clientService.create(client));
    }

    @Test
    public void findbyid_should_fail_when_id_doesnt_exist() throws DaoException {
        when(this.clientDao.findById(9999)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> clientService.findById(9999));
    }

}