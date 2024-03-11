package com.epf.rentmanager.ui.cli;

import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Scanner;


import static com.epf.rentmanager.service.VehicleService.findAll;
import static com.epf.rentmanager.utils.IOUtils.*;
/**
public class VehicleCLI {
    @Autowired
    private final VehicleService vehicleService;

    public VehicleCLI(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    public void createVehicle() {

        print("Création d'un nouveau client :");
        int id = readInt("Entrez l'id du vehicule' : ");
        String constructeur = readString("Entrez le nom du constructeur : ", true);
        int nb_places = readInt("Entrez l'email du client : ");


        try {
            // Création du véhicule avec les données saisies
            Vehicle vehicle = new Vehicle();
            vehicle.setId(id);
            vehicle.setConstructeur(constructeur);
            vehicle.setNb_places(nb_places);

            // Appel du service pour créer le véhicule
            vehicleService.create(vehicle);

            System.out.println("Le véhicule a été créé avec succès !");
        } catch (ServiceException e) {
            System.err.println("Erreur lors de la création du véhicule : " + e.getMessage());
        }
    }

    public void listVehicles() {
        List<Vehicle> vehicles;
        try {
            vehicles = findAll();


        } catch (ServiceException e) {
            System.err.println("Erreur lors de l'affichage de la liste des clients' : " + e.getMessage());
        }
    }

    public void deleteVehicle() {
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

}
 **/
