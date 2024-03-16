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
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epf.rentmanager.service.ReservationService.findResaByClientId;
import static com.epf.rentmanager.service.ReservationService.findResaByVehicleId;


@WebServlet("/users/details")
public class ClientDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    private ClientService clientService;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private ReservationService reservationService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

    }

        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            long clientId = Long.parseLong(request.getParameter("id"));
            try {
                List<Reservation> reservations = findResaByClientId(clientId);
                request.setAttribute("reservations", reservations);
                Client client = clientService.findById(clientId);
                request.setAttribute("client", client);

                List<Vehicle> vehicles = new ArrayList<>();

                for (Reservation reservation : reservations) {
                    long vehicleId = reservation.getVehicle_id();
                    Vehicle vehicle = vehicleService.findById(vehicleId);
                    vehicles.add(vehicle);
                }

                request.setAttribute("vehicles", vehicles);
                this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/details.jsp").forward(request, response);
            } catch (ServiceException e) {
                throw new RuntimeException(e);
            }

        }
}

