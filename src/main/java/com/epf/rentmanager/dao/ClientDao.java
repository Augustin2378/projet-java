package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;


import com.epf.rentmanager.model.Client;
import org.springframework.stereotype.Repository;

@Repository
public class ClientDao {
	

	private ClientDao() {}

	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String UPDATE_CLIENT_QUERY = "UPDATE Client SET nom=?, prenom=?, email=?, naissance=? WHERE id=?;";

	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";

	private static final String FIND_CLIENT_QUERY_EMAIL = "SELECT id, nom, prenom, naissance FROM Client WHERE email=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";

	private static final String COUNT_CLIENTS_QUERY = "SELECT COUNT(*) AS total FROM Client;";

	public long create(Client client) throws DaoException {

		try {
			Connection connection = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement ps =
					connection.prepareStatement(CREATE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, client.getNom());
			ps.setString(2, client.getPrenom());
			ps.setString(3, client.getEmail());
			ps.setDate(4,Date.valueOf(client.getNaissance()));

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

	public void update(Client client) throws DaoException {
		try {
			Connection connection = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement ps =
					connection.prepareStatement(UPDATE_CLIENT_QUERY);

			ps.setString(1, client.getNom());
			ps.setString(2, client.getPrenom());
			ps.setString(3, client.getEmail());
			ps.setDate(4, Date.valueOf(client.getNaissance()));
			ps.setLong(5, client.getId());

			ps.executeUpdate();

			ps.close();
			connection.close();
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}


	public long delete(Client client) throws DaoException {
		try {
			Connection connection = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement ps =
					connection.prepareStatement(DELETE_CLIENT_QUERY);
			ps.setLong(1, client.getId());


			ps.execute();

			ps.close();
			connection.close();


			return client.getId();
		} catch (SQLException e) {
			System.out.println(e.getMessage() );
			throw new DaoException(e);
		}
	}

	public Client findById(long id) throws DaoException {
		Client client = null;
		try {
			Connection connection = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement ps =
					connection.prepareStatement(FIND_CLIENT_QUERY);
			ps.setInt(1,(int) id);


			ResultSet resultSet =  ps.executeQuery();
			if (resultSet.next()) {

				client = new Client();
				client.setId((int) id);
				client.setNom(resultSet.getString("nom"));
				client.setPrenom(resultSet.getString("prenom"));
				client.setEmail(resultSet.getString("email"));
				client.setNaissance(resultSet.getDate("naissance").toLocalDate());

			}

			resultSet.close();
			ps.close();
			connection.close();


			return client;
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	public List<Client> findAll() throws DaoException {
		List<Client> clients = new ArrayList<>();

		try {
			Connection connection = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement ps =
					connection.prepareStatement(FIND_CLIENTS_QUERY);


			ResultSet resultSet =  ps.executeQuery();
			while (resultSet.next()) {

				Client client = new Client();
				client.setId(resultSet.getInt("id"));
				client.setNom(resultSet.getString("nom"));
				client.setPrenom(resultSet.getString("prenom"));
				client.setEmail(resultSet.getString("email"));
				client.setNaissance(resultSet.getDate("naissance").toLocalDate());
				clients.add(client);
			}

			resultSet.close();
			ps.close();
			connection.close();


			return clients;
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	public int count() throws DaoException {
		int count = 0;
		try {
			Connection connection = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement ps =
					connection.prepareStatement(COUNT_CLIENTS_QUERY);

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

	public Client findByEmail(String email) throws DaoException {

		Client client = null;
		try {
			Connection connection = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement ps =
					connection.prepareStatement(FIND_CLIENT_QUERY_EMAIL);
			ps.setString(1,email);


			ResultSet resultSet =  ps.executeQuery();
			if (resultSet.next()) {

				client = new Client();
				client.setEmail(email);
				client.setId(resultSet.getInt("id"));
				client.setNom(resultSet.getString("nom"));
				client.setPrenom(resultSet.getString("prenom"));

				client.setNaissance(resultSet.getDate("naissance").toLocalDate());

			}

			resultSet.close();
			ps.close();
			connection.close();


			return client;
		} catch (SQLException e) {
			throw new DaoException(e);
		}

	}


}
