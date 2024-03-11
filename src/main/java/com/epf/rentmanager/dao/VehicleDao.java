package com.epf.rentmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleDao {
	

	private VehicleDao() {}


	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur,modele, nb_places) VALUES(?, ?,?)";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle;";

	private static final String COUNT_VEHICLES_QUERY = "SELECT COUNT(*) AS total FROM Vehicle;";

	public long create(Vehicle vehicle) throws DaoException {
		try {
			Connection connection = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement ps =
					connection.prepareStatement(CREATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, vehicle.getConstructeur());
			ps.setString(2, vehicle.getModele());
			ps.setInt(3, vehicle.getNb_places());

			ps.execute();
			ResultSet resultSet = ps.getGeneratedKeys();
			if (resultSet.next()) {

				return resultSet.getInt(1);
			}

			ps.close();
			connection.close();


			return 0;
		} catch (SQLException e) {
			System.out.println(e.getMessage() );
            throw new DaoException(e);
        }
    }

	public long delete(Vehicle vehicle) throws DaoException {
		try {
			Connection connection = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement ps =
					connection.prepareStatement(DELETE_VEHICLE_QUERY);
			ps.setLong(1, vehicle.getId());


			ps.execute();

			ps.close();
			connection.close();


			return vehicle.getId();
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	public Vehicle findById(long id) throws DaoException {
		Vehicle vehicle = null;
		try {
			Connection connection = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement ps =
					connection.prepareStatement(FIND_VEHICLE_QUERY);
			ps.setInt(1,(int) id);


			ResultSet resultSet =  ps.executeQuery();
			if (resultSet.next()) {
				vehicle = new Vehicle();
				vehicle.setId((int) id);
				vehicle.setConstructeur(resultSet.getString("constructeur"));
				vehicle.setModele(resultSet.getString("modele"));
				vehicle.setNb_places(resultSet.getInt("nb_places"));


			}

			resultSet.close();
			ps.close();
			connection.close();


			return vehicle;
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	public List<Vehicle> findAll() throws DaoException {
		List<Vehicle> vehicles = new ArrayList<>();

		try {
			Connection connection = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement ps =
					connection.prepareStatement(FIND_VEHICLES_QUERY);


			ResultSet resultSet =  ps.executeQuery();
			while (resultSet.next()) {

				Vehicle vehicle = new Vehicle();
				vehicle.setId(resultSet.getInt("id"));
				vehicle.setConstructeur(resultSet.getString("constructeur"));
				vehicle.setModele(resultSet.getString("modele"));
				vehicle.setNb_places(resultSet.getInt("nb_places"));
				vehicles.add(vehicle);

			}

			resultSet.close();
			ps.close();
			connection.close();


			return vehicles;
		} catch (SQLException e) {
			throw new DaoException(e);
		}

		
	}

	public int count() throws DaoException {
		int count = 0;
		try {
			Connection connection = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement ps =
					connection.prepareStatement(COUNT_VEHICLES_QUERY);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt("total");
			}

			ps.close();
			connection.close();

			return count;

		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}
	

}
