package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao {


	private ReservationDao() {}

	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String UPDATE_RESERVATION_QUERY = "UPDATE Reservation SET client_id=?, vehicle_id=?, debut=?, fin=? WHERE id=?";

	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";

	private static final String FIND_RESERVATION_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation WHERE id=?;";

	private static final String COUNT_RESERVATIONS_QUERY = "SELECT COUNT(*) AS total FROM Reservation;";
		
	public long create(Reservation reservation) throws DaoException {
		try {
			Connection connection = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement ps =
					connection.prepareStatement(CREATE_RESERVATION_QUERY);

			ps.setInt(1, reservation.getClient_id());
			ps.setInt(2, reservation.getVehicle_id());
			ps.setDate(3, Date.valueOf(reservation.getDebut()));
			ps.setDate(4, Date.valueOf(reservation.getFin()));

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

	public void update(Reservation reservation) throws DaoException {
		try {
			Connection connection = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement ps = connection.prepareStatement(UPDATE_RESERVATION_QUERY);

			ps.setLong(1, reservation.getClient_id());
			ps.setLong(2, reservation.getVehicle_id());
			ps.setDate(3, Date.valueOf(reservation.getDebut()));
			ps.setDate(4, Date.valueOf(reservation.getFin()));
			ps.setLong(5, reservation.getId());

			ps.executeUpdate();

			ps.close();
			connection.close();
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}


	public long delete(Reservation reservation) throws DaoException {
		try {
			Connection connection = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement ps =
					connection.prepareStatement(DELETE_RESERVATION_QUERY);
			ps.setLong(1, reservation.getId());


			ps.execute();

			ps.close();
			connection.close();


			return reservation.getId();
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	
	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		List<Reservation> reservations = new ArrayList<>();
		try {
			Connection connection = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement ps =
					connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);
			ps.setInt(1,(int) clientId);


			ResultSet resultSet =  ps.executeQuery();
			while (resultSet.next()) {

				Reservation reservation = new Reservation();
				reservation.setClient_id((int) clientId);
				reservation.setVehicle_id(resultSet.getInt("vehicle_id"));
				reservation.setDebut(resultSet.getDate("debut").toLocalDate());
				reservation.setFin(resultSet.getDate("fin").toLocalDate());
				reservations.add(reservation);
			}

			resultSet.close();
			ps.close();
			connection.close();


			return reservations;
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}
	
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		List<Reservation> reservations = new ArrayList<>();
		try {
			Connection connection = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement ps =
					connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);
			ps.setInt(1,(int) vehicleId);


			ResultSet resultSet =  ps.executeQuery();
			while (resultSet.next()) {

				Reservation reservation = new Reservation();
				reservation.setVehicle_id((int)vehicleId);
				reservation.setClient_id(resultSet.getInt("client_id"));
				reservation.setDebut(resultSet.getDate("debut").toLocalDate());
				reservation.setFin(resultSet.getDate("fin").toLocalDate());
				reservations.add(reservation);
			}

			resultSet.close();
			ps.close();
			connection.close();


			return reservations;
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	public List<Reservation> findAll() throws DaoException {
		List<Reservation> reservations = new ArrayList<>();

		try {
			Connection connection = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement ps =
					connection.prepareStatement(FIND_RESERVATIONS_QUERY);


			ResultSet resultSet =  ps.executeQuery();
			while (resultSet.next()) {

				Reservation reservation = new Reservation();
				reservation.setId(resultSet.getInt("id"));
				reservation.setVehicle_id(resultSet.getInt("vehicle_id"));
				reservation.setClient_id(resultSet.getInt("client_id"));
				reservation.setDebut(resultSet.getDate("debut").toLocalDate());
				reservation.setFin(resultSet.getDate("fin").toLocalDate());
				reservations.add(reservation);

			}

			resultSet.close();
			ps.close();
			connection.close();


			return reservations;
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	public Reservation findById(long id) throws DaoException {
		Reservation reservation = null;
		try {
			Connection connection = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement ps =
					connection.prepareStatement(FIND_RESERVATION_QUERY);
			ps.setInt(1,(int) id);


			ResultSet resultSet =  ps.executeQuery();
			if (resultSet.next()) {

				reservation = new Reservation();
				reservation.setId((int) id);
				reservation.setVehicle_id(resultSet.getInt("vehicle_id"));
				reservation.setClient_id(resultSet.getInt("client_id"));
				reservation.setDebut(resultSet.getDate("debut").toLocalDate());
				reservation.setFin(resultSet.getDate("fin").toLocalDate());

			}

			resultSet.close();
			ps.close();
			connection.close();


			return reservation;
		} catch (SQLException e) {

			throw new DaoException(e);
		}
	}

	public int count() throws DaoException {
		int count = 0;
		try {
			Connection connection = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement ps =
					connection.prepareStatement(COUNT_RESERVATIONS_QUERY);

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
