package com.epf.rentmanager.servlet;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/users/create")
public class ClientCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    private ClientService clientService;

    @Override
    public void init()throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
// traitement du formulaire (appel à la méthode de sauvegarde)
        String nom = request.getParameter("last_name");
        String prenom = request.getParameter("first_name");
        String email = request.getParameter("email");
        String dateStr = request.getParameter("naissance");

        // Définition du motif de format de date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate naissance = LocalDate.parse(dateStr, formatter);

        LocalDate date18Ans = LocalDate.now().minusYears(18);

        if (naissance.isAfter(date18Ans)) {
            int AgeLegalError =1;
            request.setAttribute("AgeLegalError", 1);
            request.getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
            return;

        }

        try {
            if (clientService.isEmailAlreadyUsed(email)) {

                request.setAttribute("EmailError", "L'adresse e-mail est déjà prise");
                request.getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
                return;
            }
        } catch (ServiceException e) {
            throw new ServletException("Erreur lors de la vérification de l'e-mail", e);
        }

        if (nom.length() < 3 || prenom.length() < 3) {
            request.setAttribute("3CharError", "L'adresse e-mail est déjà prise");
            request.getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
            return;
        }


        Client client = new Client();
        client.setNom(nom);
        client.setPrenom(prenom);
        client.setEmail(email);
        client.setNaissance(naissance);


        try {

            // Appel du service pour insérer le véhicule dans la base de données

                clientService.create(client);


            // Redirection vers une autre page après l'ajout du véhicule (par exemple, une page de confirmation)
            response.sendRedirect(request.getContextPath() + "/users");
        } catch (ServiceException e) {
            throw new ServletException("Erreur lors de la création du client", e);
        }
    }


}