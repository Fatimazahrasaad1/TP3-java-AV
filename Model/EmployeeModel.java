package Model;

import DAO.EmployeeDAOI;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class EmployeeModel {
    private EmployeeDAOI employeeDAO;

    public EmployeeModel(EmployeeDAOI employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    private List<Employee> employees = new ArrayList<>();

    public boolean ajouterEmployee(int id, String nom, String prenom, String email, String telephone, double salaire, Employee.Role role, Employee.Poste poste, int solde) {
        Employee newEmployee = new Employee(id, nom, prenom, email, telephone, salaire, role, poste, solde);
        return employees.add(newEmployee);
    }

    public boolean modifierEmployee(int id, String nom, String prenom, String email, String telephone, double salaire, Employee.Role role, Employee.Poste poste, int solde) {
        for (Employee emp : employees) {
            if (emp.getId() == id) {
                emp.setNom(nom);
                emp.setPrenom(prenom);
                emp.setEmail(email);
                emp.setTelephone(telephone);
                emp.setSalaire(salaire);
                emp.setRole(role);
                emp.setPoste(poste);
                emp.setSolde(solde);
                return true;
            }
        }
        return false;
    }

    public boolean supprimerEmployee(int id) {
        return employees.removeIf(emp -> emp.getId() == id);
    }

    public boolean importerEmployees(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                String nom = data[1];
                String prenom = data[2];
                String email = data[3];
                String telephone = data[4];
                double salaire = Double.parseDouble(data[5]);
                Employee.Role role = Employee.Role.valueOf(data[6]);
                Employee.Poste poste = Employee.Poste.valueOf(data[7]);
                int solde = Integer.parseInt(data[8]);

                ajouterEmployee(id, nom, prenom, email, telephone, salaire, role, poste, solde);
            }
            return true;
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean exporterEmployees(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Employee emp : employees) {
                writer.write(emp.getId() + "," + emp.getNom() + "," + emp.getPrenom() + "," + emp.getEmail() + "," +
                        emp.getTelephone() + "," + emp.getSalaire() + "," + emp.getRole() + "," + emp.getPoste() + "," + emp.getSolde());
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Employee> afficherEmployees() {
        return employees;
    }
}
