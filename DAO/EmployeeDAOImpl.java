package DAO;

import Model.Employee;
import Model.Employee.Poste;
import Model.Employee.Role;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class EmployeeDAOImpl implements EmployeeDAOI, DataImportExport<Employee> {

	@Override
	public List<Employee> afficherEmployees() {
		String query = "SELECT e.id, e.nom, e.prenom, e.email, e.telephone, e.salaire, r.nom AS roleNom, p.nom AS posteNom " +
				"FROM employe e " +
				"JOIN role r ON e.role_id = r.id " +
				"JOIN poste p ON e.poste_id = p.id";
		List<Employee> employees = new ArrayList<>();

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestiondeconges", "root", "YES");
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery(query)) {

			while (rs.next()) {
				String roleNom = rs.getString("roleNom");
				String posteNom = rs.getString("posteNom");

				Role role = Role.valueOf(roleNom);
				Poste poste = Poste.valueOf(posteNom);

				Employee employee = new Employee(
						rs.getInt("id"),
						rs.getString("nom"),
						rs.getString("prenom"),
						rs.getString("email"),
						rs.getString("telephone"),
						rs.getDouble("salaire"),
						role,
						poste,
						0
				);
				employees.add(employee);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return employees;
	}

	@Override
	public void ajouterEmployee(Employee employee) {
		String query = "INSERT INTO employe (nom, prenom, email, telephone, salaire, role_id, poste_id) " +
				"VALUES (?, ?, ?, ?, ?, (SELECT id FROM role WHERE nom=?), (SELECT id FROM poste WHERE nom=?))";

		try (PreparedStatement stmt = DBConnection.getConnection().prepareStatement(query)) {
			stmt.setString(1, employee.getNom());
			stmt.setString(2, employee.getPrenom());
			stmt.setString(3, employee.getEmail());
			stmt.setString(4, employee.getTelephone());
			stmt.setDouble(5, employee.getSalaire());
			stmt.setString(6, employee.getRole().name());
			stmt.setString(7, employee.getPoste().name());

			int rowAffected = stmt.executeUpdate();

			if (rowAffected == 0) {
				System.out.println("Échec de l'insertion !!");
			} else {
				System.out.println("Insertion réussie :)");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void modifierEmployee(int id, Employee modifiedEmployee) {
		String query = "UPDATE employe SET nom=?, prenom=?, email=?, telephone=?, salaire=?, " +
				"role_id=(SELECT id FROM role WHERE nom=?), poste_id=(SELECT id FROM poste WHERE nom=?) WHERE id=?";

		try (PreparedStatement stmt = DBConnection.getConnection().prepareStatement(query)) {
			stmt.setString(1, modifiedEmployee.getNom());
			stmt.setString(2, modifiedEmployee.getPrenom());
			stmt.setString(3, modifiedEmployee.getEmail());
			stmt.setString(4, modifiedEmployee.getTelephone());
			stmt.setDouble(5, modifiedEmployee.getSalaire());
			stmt.setString(6, modifiedEmployee.getRole().name());
			stmt.setString(7, modifiedEmployee.getPoste().name());
			stmt.setInt(8, id);

			int rowAffected = stmt.executeUpdate();

			if (rowAffected > 0) {
				System.out.println("L'employé a été modifié :)");
			} else {
				System.out.println("L'employé n'a pas été modifié :(");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void supprimerEmployee(int id) {
		String query = "DELETE FROM employe WHERE id=?";

		try (PreparedStatement stmt = DBConnection.getConnection().prepareStatement(query)) {
			stmt.setInt(1, id);

			int rowAffected = stmt.executeUpdate();

			if (rowAffected > 0) {
				System.out.println("L'employé a été supprimé :)");
			} else {
				System.out.println("L'employé n'a pas été supprimé :(");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void importData(String filePath) throws IOException {
		String query = "INSERT INTO employe (nom, prenom, email, telephone, salaire, role_id, poste_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
			 PreparedStatement pstmt = DBConnection.getConnection().prepareStatement(query)) {

			String line;
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				if (data.length == 7) {
					pstmt.setString(1, data[0].trim());
					pstmt.setString(2, data[1].trim());
					pstmt.setString(3, data[2].trim());
					pstmt.setString(4, data[3].trim());
					pstmt.setDouble(5, Double.parseDouble(data[4].trim()));
					pstmt.setString(6, data[5].trim());
					pstmt.setString(7, data[6].trim());
					pstmt.addBatch();
				}
			}
			pstmt.executeBatch();
			System.out.println("Les employés ont été importés avec succès !");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void exportData(String fileName, List<Employee> employees) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
			writer.write("Nom, Prenom, Email, Telephone, Salaire, Role, Poste");
			writer.newLine();
			for (Employee employee : employees) {
				writer.write(String.format("%s,%s,%s,%s,%.2f,%s,%s",
						employee.getNom(),
						employee.getPrenom(),
						employee.getEmail(),
						employee.getTelephone(),
						employee.getSalaire(),
						employee.getRole().name(),
						employee.getPoste().name()));
				writer.newLine();
			}
			System.out.println("Les employés ont été exportés avec succès !");
		}
	}
}
