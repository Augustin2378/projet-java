package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

	private static VehicleDao vehicleDao;
	public static VehicleService instance;

	@Autowired
	public VehicleService(VehicleDao vehicleDao) {
		this.vehicleDao = vehicleDao;
	}


	public VehicleService(){

	}
	
	
	public long create(Vehicle vehicle) throws ServiceException {
		long id_vehicle = 0;
		if ( vehicle.getConstructeur().isEmpty()) {
			throw new ServiceException("Le constructeur du vehicule est vide");
		}
		if(vehicle.getNb_places()<1){
			throw new ServiceException("Le nombre de place est inférieur à 1");
		}

		try {

			id_vehicle = vehicleDao.create(vehicle);

		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la création du vehicule");
		}
		return id_vehicle;
		
	}

	public Vehicle findById(long id) throws ServiceException {
		try {
			Vehicle vehicle =vehicleDao.findById(id);

			if (vehicle != null) {
				return vehicle;
			}

			throw new ServiceException("Le vehicule n°" + id + " n'a pas été trouvé dans la base de données");
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la récupération du vehicule");
		}
		
	}

	public static List<Vehicle> findAll() throws ServiceException {
		try {
			List<Vehicle> vehicles = vehicleDao.findAll();

			if (vehicles != null) {
				return vehicles;
			}

			throw new ServiceException("La liste de tous les vehiculess n'a pas été trouvé dans la base de données");
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la récupération de la liste de vehicules");
		}
		
	}

	public long delete(Vehicle vehicle) throws ServiceException {
		long id_vehicle = 0;

		try {
			id_vehicle = vehicleDao.delete(vehicle);
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la suppression du vehicule");
		}
		return id_vehicle;
	}

	public int count() throws ServiceException{
		int nb_vehicle = 0;
		try{
			nb_vehicle = vehicleDao.count();
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors du compte du vehicule");
		}
		return nb_vehicle;
	}

	public void update(Vehicle vehicle) throws ServiceException {
		if (vehicle.getConstructeur().isEmpty() || vehicle.getModele().isEmpty()) {
			throw new ServiceException("La voiture doit avoir une marque et un modèle");
		}
		if(vehicle.getNb_places()<1){
			throw new ServiceException("Le nombre de place est inférieur à 1");
		}

		try {
			vehicleDao.update(vehicle);
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la mise à jour de la voiture", e);
		}
	}


}
