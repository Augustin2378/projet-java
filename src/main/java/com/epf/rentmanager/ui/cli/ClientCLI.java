package com.epf.rentmanager.ui.cli;
/**
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Scanner;

import static com.epf.rentmanager.service.ClientService.findAll;
import static com.epf.rentmanager.utils.IOUtils.*;

public class ClientCLI {
    @Autowired
    private final ClientService clientService;

    public ClientCLI(ClientService clientService) {
        this.clientService = clientService;
    }

    public void createClient() {

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

    public void listClient() {
        List<Client> clients;
        try {
            clients = findAll();


        } catch (ServiceException e) {
            System.err.println("Erreur lors de l'affichage de la liste des clients' : " + e.getMessage());
        }
    }

    public void deleteClient() {
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
}
**/