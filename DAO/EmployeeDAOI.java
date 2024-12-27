package DAO;

import java.util.List;
import Model.Employee;

public interface EmployeeDAOI {
	
	public void ajouterEmployee(Employee newEmployee);
	public List<Employee> afficherEmployees();
	public void modifierEmployee(int id, Employee modifiedEmployee);
	public void supprimerEmployee(int id);

}
