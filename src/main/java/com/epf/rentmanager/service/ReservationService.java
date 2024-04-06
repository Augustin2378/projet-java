package com.epf.rentmanager.service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;

import com.epf.rentmanager.dao.ReservationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.epf.rentmanager.utils.IOUtils.print;
@Service
public class ReservationService {

    private static ReservationDao reservationDao;
    public static ReservationService instance;

    @Autowired
    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }


    public ReservationService(){

    }


    public long create(Reservation reservation) throws ServiceException {
        long id_reservation = 0;

        try {
            id_reservation = reservationDao.create(reservation);
        } catch (DaoException e) {


            throw new ServiceException("Une erreur a eu lieu lors de la création de l'utilisateur");
        }
        return id_reservation;
    }

    public static List<Reservation> findResaByClientId(long id) throws ServiceException {
        try {
            List<Reservation> reservations = reservationDao.findResaByClientId(id);

            if (reservations != null) {
                return reservations;
            }

            throw new ServiceException("Le reservation dont le n°" + id + "client n'a pas été trouvé dans la base de données");
        } catch (DaoException e) {
            throw new ServiceException("Une erreur a eu lieu lors de la récupération du reservation");
        }
    }

    public static List<Reservation> findResaByVehicleId(long id) throws ServiceException {
        try {
            List<Reservation> reservations = reservationDao.findResaByVehicleId(id);

            if (reservations != null) {
                return reservations;
            }

            throw new ServiceException("Le reservation dont le n°" + id + "vehicule n'a pas été trouvé dans la base de données");
        } catch (DaoException e) {
            throw new ServiceException("Une erreur a eu lieu lors de la récupération du reservation");
        }
    }

    public static List<Reservation> findAll() throws ServiceException {
        try {
            List<Reservation> reservations = reservationDao.findAll();

            if (reservations != null) {
                return reservations;
            }

            throw new ServiceException("La liste de toutes les reservations n'a pas été trouvé dans la base de données");
        } catch (DaoException e) {
            throw new ServiceException("Une erreur a eu lieu lors de la récupération de la liste de reservations");
        }
    }

    public long delete(Reservation reservation) throws ServiceException {
        long id_reservation = 0;

        try {
            id_reservation = reservationDao.delete(reservation);
        } catch (DaoException e) {
            throw new ServiceException("Une erreur a eu lieu lors de la suppressio, de la reservation");
        }
        return id_reservation;
    }

    public Reservation findById(long id) throws ServiceException {
        try {
            Reservation reservation = reservationDao.findById(id);

            if (reservation != null) {
                return reservation;
            }

            throw new ServiceException("La reservation n°" + id + " n'a pas été trouvé dans la base de données");
        } catch (DaoException e) {
            throw new ServiceException("Une erreur a eu lieu lors de la récupération de la reservation");
        }
    }

    public int count() throws ServiceException{
        int nb_reservation = 0;
        try{
            nb_reservation = reservationDao.count();
        } catch (DaoException e) {
            throw new ServiceException("Une erreur a eu lieu lors du compte de la reservation");
        }
        return nb_reservation;
    }

    public static boolean isCarRentTooLong(long id) throws ServiceException {
        int compteur = 0;
        List<Reservation> reservations = findResaByVehicleId(id);


        reservations.sort(Comparator.comparing(Reservation::getDebut));
        System.out.println("sizeee" + reservations.size());
        for (int i = 0; i < reservations.size() -1 ; i++) {
            Reservation currentReservation = reservations.get(i);
            Reservation nextReservation = reservations.get(i + 1);


            long joursEntreReservations = ChronoUnit.DAYS.between(currentReservation.getFin(), nextReservation.getDebut());
            System.out.println("jours entre resa " + joursEntreReservations);
            if (joursEntreReservations == 1) {
                if(compteur ==0){
                    compteur += ChronoUnit.DAYS.between(reservations.get(i).getDebut(), reservations.get(i+1).getFin());

                }
                else{
                    compteur += ChronoUnit.DAYS.between(reservations.get(i+1).getDebut(), reservations.get(i+1).getFin());
                }


            }

        }


        System.out.println("compteur" + compteur);
        if(compteur >=30){
            return true;
        }


        return false;
    }

    public void update(Reservation reservation) throws ServiceException {
        try {

            reservationDao.update(reservation);
        } catch (DaoException e) {
            throw new ServiceException("Une erreur a eu lieu lors de la mise à jour de la réservation", e);
        }
    }




}
