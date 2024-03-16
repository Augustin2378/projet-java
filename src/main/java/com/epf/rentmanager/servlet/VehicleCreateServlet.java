package com.epf.rentmanager.servlet;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cars/create")
public class VehicleCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    private VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
// traitement du formulaire (appel à la méthode de sauvegarde)
        String constructeur = request.getParameter("manufacturer");
        String modele = request.getParameter("modele");
        int nbPlaces = Integer.parseInt(request.getParameter("seats"));

        if (nbPlaces < 2 || nbPlaces > 9) {
            request.setAttribute("nbPlacesError", "1");
            request.getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp").forward(request, response);
            return;
        }

        // Créer un nouvel objet Vehicle avec les données saisies
        Vehicle vehicle = new Vehicle();
        vehicle.setConstructeur(constructeur);
        vehicle.setModele(modele);
        vehicle.setNb_places(nbPlaces);


        try {
            // Appel du service pour insérer le véhicule dans la base de données
            vehicleService.create(vehicle);

            // Redirection vers une autre page après l'ajout du véhicule (par exemple, une page de confirmation)
            response.sendRedirect(request.getContextPath() + "/cars");
        } catch (ServiceException e) {
            throw new ServletException("Erreur lors de la création du véhicule", e);
        }
    }


}