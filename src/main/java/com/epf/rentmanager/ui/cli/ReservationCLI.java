package com.epf.rentmanager.ui.cli;

import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Scanner;

import static com.epf.rentmanager.service.ReservationService.findAll;
import static com.epf.rentmanager.utils.IOUtils.*;
/**
public class ReservationCLI {
    @Autowired
    private final ReservationService reservationService;

    public ReservationCLI(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public void createReservation() {

        print("Création d'une nouvelle réservation :");
        int id_client = readInt("Entrez l'id du client' : ");
        int id_vehicle = readInt("Entrez l'id du vehicule' : ");
        LocalDate debut = readDate("Entrez la date de début de reservation : ", true);
        LocalDate fin = readDate("Entrez la date de fin de reservation : ", true);


        try {
            // Création de la réservation avec les données saisies
            Reservation reservation = new Reservation();
            reservation.setClient_id(id_client);
            reservation.setVehicle_id(id_vehicle);
            reservation.setDebut(debut);
            reservation.setFin(fin);

            // Appel du service pour créer la réservation
            reservationService.create(reservation);

            System.out.println("La réservation a été créée avec succès !");
        } catch (ServiceException e) {
            System.err.println("Erreur lors de la création de la réservation : " + e.getMessage());
        }
    }

    public void listReservation() {
        print("affichage de toutes les reservations");
        List<Reservation> reservations;
        try {
            reservations = ReservationService.findAll();


        } catch (ServiceException e) {
            System.err.println("Erreur lors de l'affichage de la liste des reservations' : " + e.getMessage());
        }
    }

    public void listReservationByClientId() {
        List<Reservation> reservations;
        print("affichage des reservations selon client id :");
        int id = readInt("Entrez l'ID du client  : ");

        try {

            if(ReservationService.findResaByClientId(id) != null){
                reservations = ReservationService.findResaByClientId(id);
            }

        } catch (ServiceException e) {
            System.err.println("Erreur lors de l'affichage de la liste des reservations par id client : " + e.getMessage());
        }
    }

    public void listReservationByVehicleId() {
        List<Reservation> reservations;
        print("affichage des reservations selon vehicule id :");
        int id = readInt("Entrez l'ID du vehicule  : ");

        try {

            if(ReservationService.findResaByVehicleId(id) != null){
                reservations = ReservationService.findResaByVehicleId(id);
            }


        } catch (ServiceException e) {
            System.err.println("Erreur lors de l'affichage de la liste des reservations par id vehicule : " + e.getMessage());
        }
    }
    public void deleteReservation() {
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

}
**/