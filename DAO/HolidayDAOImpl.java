package DAO;

import Model.Holiday;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class HolidayDAOImpl {

    public void ajouter(Holiday holiday) {
        String sql = "INSERT INTO holiday (employeeNom, startDate, endDate, holidayTypeId) VALUES (?, ?, ?, (SELECT id FROM holidaytype WHERE nom=?))";
        try (PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, holiday.getEmployeeNom());
            stmt.setDate(2, new java.sql.Date(holiday.getStartDate().getTime()));
            stmt.setDate(3, new java.sql.Date(holiday.getEndDate().getTime()));
            stmt.setString(4, holiday.getHolidayType().name());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean modifier(int id, Holiday holiday) {
        String sql = "UPDATE holiday SET startDate = ?, endDate = ?, holidayTypeId = (select id from holidaytype where nom=?) WHERE id = ?";
        try (PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(holiday.getStartDate().getTime()));
            stmt.setDate(2, new java.sql.Date(holiday.getEndDate().getTime()));
            stmt.setString(3, holiday.getHolidayType().name());
            stmt.setInt(4, id);  // Utilisation de l'ID ici
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Holiday> afficher() {
        List<Holiday> holidays = new ArrayList<>();
        String sql = "SELECT * FROM holiday";
        try (Statement stmt = DBConnection.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                // Charger les données d'un holiday
                // Utilisez rs.getXXX pour obtenir les valeurs des colonnes
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return holidays;
    }

    public List<String> chargerNomsEmployes() {
        List<String> employes = new ArrayList<>();
        String sql = "SELECT DISTINCT employeeNom FROM holiday";
        try (Statement stmt = DBConnection.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                employes.add(rs.getString("employeeNom"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employes;
    }

    public void supprimer(int id) {
        String sql = "DELETE FROM holiday WHERE id = ?";
        try (PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

