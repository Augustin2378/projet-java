package com.epf.rentmanager.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;

import org.junit.Before;
import org.junit.Test;

public class ClientServiceTest {

    private ClientService clientService;
    private ClientDao clientDao;

    @Before
    public void setUp() {
        clientDao = mock(ClientDao.class);
        clientService = new ClientService(clientDao);
    }

    @Test
    public void testCreateClient() throws DaoException, ServiceException {
        // Mocking data
        Client client = new Client();
        client.setNom("Doe");
        client.setPrenom("John");
        long expectedId = 1L;

        // Mocking behavior of DAO
        when(clientDao.create(client)).thenReturn(expectedId);

        // Test method
        long id = clientService.create(client);

        // Assert
        assertEquals(expectedId, id);
    }

    @Test(expected = ServiceException.class)
    public void testCreateClientWithEmptyName() throws DaoException, ServiceException {
        // Mocking data
        Client client = new Client();
        client.setNom("");
        client.setPrenom("John");

        // Test method
        clientService.create(client);
    }

    // Add more test cases for other methods...
}
