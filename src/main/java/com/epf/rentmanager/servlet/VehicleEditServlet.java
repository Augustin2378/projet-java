package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cars/edit")
public class VehicleEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    private VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long vehicleId = Long.parseLong(request.getParameter("id"));
            Vehicle vehicle = vehicleService.findById(vehicleId);
            request.setAttribute("vehicle", vehicle);
            request.getRequestDispatcher("/WEB-INF/views/vehicles/edit.jsp").forward(request, response);
        } catch (ServiceException e) {
            throw new ServletException("Error retrieving vehicle", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long vehicleId = Long.parseLong(request.getParameter("id"));
            String marque = request.getParameter("manufacturer");
            String modele = request.getParameter("modele");
            int seats = Integer.parseInt(request.getParameter("seats"));

            if (seats < 2 || seats > 9) {
                request.setAttribute("nbPlacesError", true);
                request.getRequestDispatcher("/WEB-INF/views/vehicles/edit.jsp").forward(request, response);
                return;
            }

            Vehicle vehicle = vehicleService.findById(vehicleId);
            vehicle.setConstructeur(marque);
            vehicle.setModele(modele);
            vehicle.setNb_places(seats);

            vehicleService.update(vehicle);
            response.sendRedirect(request.getContextPath() + "/cars");
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid number of seats", e);
        } catch (ServiceException e) {
            throw new ServletException("Error updating vehicle", e);
        }
    }
}
