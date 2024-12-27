package Model;

import DAO.HolidayDAOImpl;
import Model.Holiday.HolidayType;
import java.util.Date;
import java.util.List;

public class HolidayModel {

    private HolidayDAOImpl dao;

    public HolidayModel(HolidayDAOImpl dao) {
        this.dao = dao;
    }

    public boolean ajouterHoliday(String employeeNom, Date startDate, Date endDate, HolidayType holidayType) {
        if (!isValidDateRange(startDate, endDate)) {
            System.out.println("Date invalide!");
            return false;
        }

        long days = calculateHolidayDays(startDate, endDate);
        System.out.println(days + " day(s)");

        Holiday newHoliday = new Holiday(employeeNom, startDate, endDate, holidayType);
        dao.ajouter(newHoliday);

        return true;
    }

    public boolean isValidDateRange(Date startDate, Date endDate) {
        return !startDate.after(endDate);
    }

    private long calculateHolidayDays(Date startDate, Date endDate) {
        long diffInMillis = endDate.getTime() - startDate.getTime();
        return diffInMillis / (1000 * 60 * 60 * 24);
    }

    public List<Holiday> afficherHolidays() {
        return dao.afficher();
    }

    public List<String> chargerNomsEmployes() {
        return dao.chargerNomsEmployes();
    }

    public boolean supprimerHoliday(int id) {
        if(id <= 0) {
            System.out.println("Id invalide!!");
            return false;
        }

        dao.supprimer(id);
        return true;
    }

    public boolean modifierHoliday(int id, String employeeNom, Date startDate, Date endDate, HolidayType holidayType) {
        if (id <= 0 || !isValidDateRange(startDate, endDate)) {
            System.out.println("Données invalides pour la modification.");
            return false;
        }

        Holiday updatedHoliday = new Holiday(id, startDate, endDate, holidayType, employeeNom);
        return dao.modifier(id, updatedHoliday);
    }
}
