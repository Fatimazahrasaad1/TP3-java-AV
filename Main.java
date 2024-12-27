import View.*;
import Model.*;
import Controller.*;
import DAO.*;

public class Main {
	
	public static void main(String[] args) {

		EmployeeDAOImpl employeeDAO = new EmployeeDAOImpl();
		EmployeeModel employeeModel = new EmployeeModel(employeeDAO);
		
		HolidayDAOImpl holidayDAO = new HolidayDAOImpl();
		HolidayModel holidayModel = new HolidayModel(holidayDAO);

		EmployeeView employeeView = new EmployeeView();
		HolidayView holidayView = new HolidayView();

		new EmployeeController(employeeView, employeeModel);
		new HolidayController(holidayView, holidayModel);

		ManagementInterfaces combinedView = new ManagementInterfaces(employeeView, holidayView);
		
		combinedView.setVisible(true);
	}
}
