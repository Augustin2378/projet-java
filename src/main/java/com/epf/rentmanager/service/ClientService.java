package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;

import com.epf.rentmanager.dao.ClientDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

	private static ClientDao clientDao;
	public static ClientService instance;


	public ClientService(){

	}

	@Autowired
	public ClientService(ClientDao clientDao) {
		this.clientDao = clientDao;
	}
	
	public long create(Client client) throws ServiceException {
		long id_client = 0;
		if ( client.getPrenom().isEmpty() || client.getNom().isEmpty()) {
			throw new ServiceException("L'utilisateur doit avoir un nom et un prénom");
		}
		if(client.getNom() != null){
			String new_nom = client.getNom().toUpperCase();
			client.setNom(new_nom);
		}

		try {
			id_client = clientDao.create(client);
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la création de l'utilisateur");
		}
		return id_client;
	}

	public Client findById(long id) throws ServiceException {
		try {
			Client client = clientDao.findById(id);

			if (client != null) {
				return client;
			}

			throw new ServiceException("Le client n°" + id + " n'a pas été trouvé dans la base de données");
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la récupération du client");
		}
	}


	public static List<Client> findAll() throws ServiceException {
		try {
			List<Client> clients = clientDao.findAll();

			if (clients != null) {
				return clients;
			}

			throw new ServiceException("La liste de tous les clients n'a pas été trouvé dans la base de données");
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la récupération de la liste de clients");
		}
	}

	public long delete(Client client) throws ServiceException {
		long id_client = 0;

		try {
			id_client = clientDao.delete(client);
		} catch (DaoException e) {

			throw new ServiceException("Une erreur a eu lieu lors de la suppressio, de l'utilisateur");
		}
		return id_client;
	}

	public int count() throws ServiceException{
		int nb_client = 0;
		try{
			nb_client = clientDao.count();
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors du compte du client");
		}
		return nb_client;
	}

	public boolean isEmailAlreadyUsed(String email) throws ServiceException {
		try {
			Client client = clientDao.findByEmail(email);
			if (client != null) {
				return true;
			}
			return false;
		} catch (DaoException e) {
			throw new ServiceException("Une erreur s'est produite lors de la recherche d'utilisateurs par e-mail", e);
		}
	}

	public void update(Client client) throws ServiceException {
		if (client.getPrenom().isEmpty() || client.getNom().isEmpty()) {
			throw new ServiceException("L'utilisateur doit avoir un nom et un prénom");
		}
		if (client.getNom() != null) {
			String newNom = client.getNom().toUpperCase();
			client.setNom(newNom);
		}

		try {
			clientDao.update(client);
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la mise à jour de l'utilisateur");
		}
	}


}
