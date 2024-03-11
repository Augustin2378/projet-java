/**package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.*;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.utils.IOUtils;
import com.epf.rentmanager.dao.ClientDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.epf.rentmanager.service.ClientService.findAll;
import static com.epf.rentmanager.utils.IOUtils.*;

@Service
public class Interface {
    @Autowired
    private static ClientService clientService;
    @Autowired
    private static VehicleService vehicleService;
    @Autowired
    private static ReservationService reservationService;


    public static void main(String[] args) {
        displayMainMenu();
    }

    private static void displayMainMenu() {
        boolean running = true;
        while (running) {
            IOUtils.print("\n### Menu principal ###");
            IOUtils.print("1. Créer un client");
            IOUtils.print("2. Lister tous les clients");
            IOUtils.print("3. Créer un véhicule");
            IOUtils.print("4. Lister tous les véhicules");
            IOUtils.print("5. Supprimer un client (bonus)");
            IOUtils.print("6. Supprimer un véhicule (bonus)");
            IOUtils.print("7. Créer une reservation");
            IOUtils.print("8. Lister toutes les reservations");
            IOUtils.print("9. Lister les reservations par l'id client");
            IOUtils.print("10. Lister les reservations par l'id vehicule");
            IOUtils.print("11. Supprimer une reservation");

            IOUtils.print("12. Quitter");

            int choice = IOUtils.readInt("Choisissez une option : ");

            switch (choice) {
                case 1:
                    createClient();
                    break;
                case 2:
                    listClient();
                    break;
                case 3:
                    createVehicle();
                    break;
                case 4:
                    listVehicles();
                    break;
                case 5:
                    deleteClient();
                    break;
                case 6:
                    deleteVehicle();
                    break;
                case 7:
                    createReservation();
                    break;
                case 8:
                    listReservation();
                    break;
                case 9:
                    listReservationByClientId();
                    break;
                case 10:
                    listReservationByVehicleId();
                    break;
                case 11:
                    deleteReservation();
                    break;
                case 12:
                    running = false;
                    IOUtils.print("Au revoir !");
                    break;
                default:
                    IOUtils.print("Option invalide. Veuillez réessayer.");
            }
        }
    }

    private static void createClient() {

        print("Création d'un nouveau client :");
        String nom = readString("Entrez le nom du client : ", true);
        String prenom = readString("Entrez le prénom du client : ", true);
        String email = readString("Entrez l'email du client : ", true);
        LocalDate naissance = readDate("Entrez la date de naissance du client : ", true);


        try {
            // Création du client avec les données saisies
            Client client = new Client();
            client.setNom(nom);
            client.setPrenom(prenom);
            client.setEmail(email);
            client.setNaissance(naissance);

            // Appel du service pour créer le client
            clientService.create(client);

            System.out.println("Le client a été créé avec succès !");
        } catch (ServiceException e) {
            System.err.println("Erreur lors de la création du client : " + e.getMessage());
        }
    }

    private static void listClient() {
        List<Client> clients;
        try {
            clients = findAll();
            System.out.print(clients); // changer


        } catch (ServiceException e) {
            System.err.println("Erreur lors de l'affichage de la liste des clients' : " + e.getMessage());
        }
    }

    private static void deleteClient() {
        print("Suppression d'un client :");
        int id = readInt("Entrez l'ID du client à supprimer : ");

        try {
            // Appel du service pour supprimer le client

            if(clientService.findById(id) != null){
                Client client_delete = clientService.findById(id);
                clientService.delete(client_delete);
            }


            System.out.println("Le client a été supprimé avec succès !");
        } catch (ServiceException e) {
            System.err.println("Erreur lors de la suppression du client : " + e.getMessage());
        }
    }

    private static void createVehicle() {

        print("Création d'un nouveau vehicule :");
        String constructeur = readString("Entrez le nom du constructeur : ", true);
        String modele = readString("Entrez le nom du modele : ", true);
        int nb_places = readInt("Entrez le nombre de places : ");


        try {

            Vehicle vehicle = new Vehicle();
            vehicle.setConstructeur(constructeur);
            vehicle.setNb_places(nb_places);
            vehicle.setModele(modele);
            //System.out.println(vehicle);

            vehicleService.create(vehicle);

            System.out.println("Le véhicule a été créé avec succès !");
        } catch (ServiceException e) {
            System.err.println("Erreur lors de la création du véhicule : " + e.getMessage());
        }
    }

    private static void listVehicles() {
        List<Vehicle> vehicles;
        try {
            vehicles = VehicleService.findAll();
            System.out.print(vehicles);

        } catch (ServiceException e) {
            System.err.println("Erreur lors de l'affichage de la liste des vehicules : " + e.getMessage());
        }
    }



    private static void deleteVehicle() {
        print("Suppression d'un vehicule :");
        int id = readInt("Entrez l'ID du vehicule à supprimer : ");

        try {
            // Appel du service pour supprimer le client

            if(vehicleService.findById(id) != null){
                Vehicle vehicle_delete = vehicleService.findById(id);
                vehicleService.delete(vehicle_delete);
            }


            System.out.println("Le vehicule a été supprimé avec succès !");
        } catch (ServiceException e) {
            System.err.println("Erreur lors de la suppression du vehicule : " + e.getMessage());
        }
    }

    private static void createReservation() {

        print("Création d'une nouvelle réservation :");
        int id_client = readInt("Entrez l'id du client' : ");
        int id_vehicle = readInt("Entrez l'id du vehicule' : ");
        LocalDate debut = readDate("Entrez la date de début de reservation : ", true);
        LocalDate fin = readDate("Entrez la date de fin de reservation : ", true);


        try {

            Reservation reservation = new Reservation();
            reservation.setClient_id(id_client);
            reservation.setVehicle_id(id_vehicle);
            reservation.setDebut(debut);
            reservation.setFin(fin);


            reservationService.create(reservation);

            System.out.println("La réservation a été créée avec succès !");
        } catch (ServiceException e) {
            System.err.println("Erreur lors de la création de la réservation : " + e.getMessage());
        }
    }

    private static void listReservation() {
        print("affichage de toutes les reservations");
        List<Reservation> reservations;
        try {
            reservations = ReservationService.findAll();
            System.out.print(reservations);


        } catch (ServiceException e) {
            System.err.println("Erreur lors de l'affichage de la liste des reservations' : " + e.getMessage());
        }
    }

    private static void listReservationByClientId() {
        List<Reservation> reservations;
        print("affichage des reservations selon client id :");
        int id = readInt("Entrez l'ID du client  : ");

        try {

            if(ReservationService.findResaByClientId(id) != null){
                reservations = ReservationService.findResaByClientId(id);
                System.out.print(reservations);
            }

        } catch (ServiceException e) {
            System.err.println("Erreur lors de l'affichage de la liste des reservations par id client : " + e.getMessage());
        }
    }

    private static void listReservationByVehicleId() {
        List<Reservation> reservations;
        print("affichage des reservations selon vehicule id :");
        int id = readInt("Entrez l'ID du vehicule  : ");

        try {

            if(ReservationService.findResaByVehicleId(id) != null){
                reservations = ReservationService.findResaByVehicleId(id);
                System.out.print(reservations);
            }


        } catch (ServiceException e) {
            System.err.println("Erreur lors de l'affichage de la liste des reservations par id vehicule : " + e.getMessage());
        }
    }

    private static void deleteReservation() {
        print("Suppression d'une reservation :");
        int id = readInt("Entrez l'ID de la reservation à supprimer : ");

        try {
            if(reservationService.findById(id) != null){
                Reservation reservation_delete = reservationService.findById(id);
                reservationService.delete(reservation_delete);
            }


            System.out.println("La reservation a été supprimé avec succès !");
        } catch (ServiceException e) {
            System.err.println("Erreur lors de la suppression du reservation : " + e.getMessage());
        }
    }
}**/