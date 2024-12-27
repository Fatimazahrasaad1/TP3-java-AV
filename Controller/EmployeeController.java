package Controller;

import Model.EmployeeModel;
import Model.Employee;
import Model.Employee.Role;
import Model.Employee.Poste;
import View.EmployeeView;

import java.util.List;

public class EmployeeController {

    private EmployeeView view;
    private EmployeeModel model;

    public EmployeeController(EmployeeView view, EmployeeModel model) {
        this.model = model;
        this.view = view;
        afficherEmployees();

        // Add listeners to the view buttons
        this.view.ajouterButton.addActionListener(e -> {
            ajouterEmployee();
            clearFields();
            afficherEmployees();
        });

        this.view.modifierButton.addActionListener(e -> {
            modifierEmployee();
            clearFields();
            afficherEmployees();
        });

        this.view.supprimerButton.addActionListener(e -> {
            supprimerEmployee();
            afficherEmployees();
        });

        this.view.afficherButton.addActionListener(e -> afficherEmployees());

        this.view.importButton.addActionListener(e -> importerEmployees());
        this.view.exportButton.addActionListener(e -> exporterEmployees());
    }

    // Method to add an employee
    public void ajouterEmployee() {
        int solde = 0;
        int id = 0;
        String nom = view.getNom(), prenom = view.getPrenom(), email = view.getEmail(), telephone = view.getTelephone();
        double salaire = view.getSalaire();
        Role role = view.getRole();
        Poste poste = view.getPoste();

        // Ensure the employee is successfully added
        boolean ajoutReussi = model.ajouterEmployee(id, nom, prenom, email, telephone, salaire, role, poste, solde);

        if (ajoutReussi) {
            view.afficherMessageSucces("Employee ajouté avec succès :)");
        } else {
            view.afficherMessageErreur("Échec de l'ajout :(");
        }
    }

    // Method to display the list of employees
    public void afficherEmployees() {
        List<Employee> employees = model.afficherEmployees();
        view.afficherEmployees(employees);
    }

    // Method to modify an employee
    public void modifierEmployee() {
        int solde = 0;
        int id = view.getId();

        if (id <= 0) {
            view.afficherMessageErreur("Sélectionnez un employé pour le modifier.");
            return;
        }

        String nom = view.getNom(), prenom = view.getPrenom(), email = view.getEmail(), telephone = view.getTelephone();
        double salaire = view.getSalaire();
        Role role = view.getRole();
        Poste poste = view.getPoste();

        // Call the model to modify the employee
        boolean modificationReussie = model.modifierEmployee(id, nom, prenom, email, telephone, salaire, role, poste, solde);

        if (modificationReussie) {
            view.afficherMessageSucces("L'employé a été modifié :)");
        } else {
            view.afficherMessageErreur("L'employé n'a pas été modifié :(");
        }
    }

    // Method to delete an employee
    public void supprimerEmployee() {
        int id = view.getId();

        if (id <= 0) {
            view.afficherMessageErreur("Sélectionnez un employé pour le supprimer.");
            return;
        }

        // Call the model to delete the employee
        boolean suppressionReussi = model.supprimerEmployee(id);

        if (suppressionReussi) {
            view.afficherMessageSucces("L'employé a été supprimé :)");
        } else {
            view.afficherMessageErreur("L'employé n'a pas été supprimé :(");
        }
    }

    // Method to import employee data from a file
    public void importerEmployees() {
        String filePath = view.showFileChooser("Importer des employés");
        if (filePath != null) {
            boolean importationReussie = model.importerEmployees(filePath);
            if (importationReussie) {
                view.afficherMessageSucces("Les employés ont été importés avec succès :)");
                afficherEmployees();
            } else {
                view.afficherMessageErreur("Échec de l'importation des employés :(");
            }
        }
    }

    // Method to export employee data to a file
    public void exporterEmployees() {
        String filePath = view.showFileChooser("Exporter des employés");
        if (filePath != null) {
            boolean exportationReussie = model.exporterEmployees(filePath);
            if (exportationReussie) {
                view.afficherMessageSucces("Les employés ont été exportés avec succès :)");
            } else {
                view.afficherMessageErreur("Échec de l'exportation des employés :(");
            }
        }
    }

    // Method to clear input fields after an operation
    private void clearFields() {
        view.jtfNom.setText(null);
        view.jtfPrenom.setText(null);
        view.jtfEmail.setText(null);
        view.jtfTelephone.setText(null);
        view.jtfSalaire.setText(null);
    }
}
