package com.epf.rentmanager.servlet;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epf.rentmanager.service.ReservationService.isCarRentTooLong;

@WebServlet("/rents/create")
public class ReservationCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;


    @Autowired
    private ReservationService reservationService;

    private ClientService clientService;

    private VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Client> clients = null;
        try {
            clients = clientService.findAll();
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("clients", clients);

        List<Vehicle> vehicles = null;
        try {
            vehicles = vehicleService.findAll();
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("cars", vehicles);

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
// traitement du formulaire (appel à la méthode de sauvegarde)
        int client_id = Integer.parseInt(request.getParameter("client"));
        int vehicle_id = Integer.parseInt(request.getParameter("car"));
        String date_fin = request.getParameter("end");
        String date_debut = request.getParameter("begin");


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate debut = LocalDate.parse(date_debut, formatter);
        LocalDate fin = LocalDate.parse(date_fin, formatter);


        try {
            List<Reservation> reservations = reservationService.findResaByVehicleId(vehicle_id);
            if (!reservations.isEmpty()) {
                for (Reservation reservation : reservations) {
                    if ( debut.isAfter(reservation.getDebut()) && debut.isBefore(reservation.getFin()) || fin.isAfter(reservation.getDebut()) && fin.isBefore(reservation.getFin()) || debut.equals(reservation.getFin()) ){
                        request.setAttribute("resamemejourError", "1");
                        request.getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
                        return;
                    }


                }
            }
                } catch(ServiceException e){
                    throw new ServletException("Erreur lors de la vérification de l'e-mail", e);
                }
        long differenceJours = ChronoUnit.DAYS.between(debut, fin);
        if(Math.abs(differenceJours) > 7) {
            request.setAttribute("resa7joursError", "1");
            request.getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
            return;
        }

        try {
            int compteur = 0;
            List<Reservation> reservations = reservationService.findResaByVehicleId(vehicle_id);


            reservations.sort(Comparator.comparing(Reservation::getDebut));
            System.out.println("sizeee" + reservations.size());
            for (int i = 0; i < reservations.size() -1 ; i++) {
                Reservation currentReservation = reservations.get(i);
                Reservation nextReservation = reservations.get(i + 1);


                long joursEntreReservations = ChronoUnit.DAYS.between(currentReservation.getFin(), nextReservation.getDebut());
                System.out.println("jours entre resa " + joursEntreReservations);
                if (joursEntreReservations == 1) {
                    if(compteur ==0){
                        compteur += ChronoUnit.DAYS.between(reservations.get(i).getDebut(), reservations.get(i+1).getFin()) + 1;

                    }
                    else{
                        compteur += ChronoUnit.DAYS.between(reservations.get(i+1).getDebut(), reservations.get(i+1).getFin()) +1;
                    }


                }

            }
            if(reservations.size() -1>=1){
                long joursEntreReservations = ChronoUnit.DAYS.between(reservations.get(reservations.size() -1).getFin(), debut);
                System.out.println("jours entre resa " + joursEntreReservations);
                if (joursEntreReservations == 1) {
                    compteur += ChronoUnit.DAYS.between(debut, fin) + 1;


                }
            }



            System.out.println("compteur" + compteur);

            if(compteur>=30){
                request.setAttribute("resa30joursError", "1");
                request.getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
                return;
            }
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }




        Reservation reservation = new Reservation();
                reservation.setClient_id(client_id);
                reservation.setVehicle_id(vehicle_id);
                reservation.setDebut(debut);
                reservation.setFin(fin);




                try {


                    reservationService.create(reservation);


                    response.sendRedirect(request.getContextPath() + "/rents");
                } catch (ServiceException e) {

                    throw new ServletException("Erreur lors de la création de la reservation", e);
                }
            }


        }