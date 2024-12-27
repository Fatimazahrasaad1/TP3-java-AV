package View;

import java.awt.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Model.Employee;
import Model.Holiday;
import Model.Holiday.HolidayType;

public class HolidayView extends JFrame{

	JPanel jp1=new JPanel(), jp2=new JPanel(), jp3=new JPanel(), jp4=new JPanel();
	
	public JLabel jlNom=new JLabel("Id de l'employe : "), jlType=new JLabel("Type : "), jlDateDebut=new JLabel("Date de debut : "), jlDateFin=new JLabel("Date de fin : ");
    public JComboBox<String> employeeNameComboBox = new JComboBox<>();

    public JComboBox<HolidayType> holidayType=new JComboBox<>(HolidayType.values());
    public Calendar calendar=Calendar.getInstance();
	
	public JSpinner startDateSpinner=new JSpinner(new SpinnerDateModel(calendar.getTime(), null, null, Calendar.DAY_OF_MONTH));
	private JSpinner.DateEditor startDateEditor=new JSpinner.DateEditor(startDateSpinner, "dd/MM/yyyy");//startDateSpinner is going to be added
	
	public JSpinner endDateSpinner=new JSpinner(new SpinnerDateModel(calendar.getTime(), null, null, Calendar.DAY_OF_MONTH));
	private JSpinner.DateEditor endDateEditor=new JSpinner.DateEditor(endDateSpinner, "dd/MM/yyyy");
	
	public DefaultTableModel tableModel=new DefaultTableModel(new Object[] [] {}, new String[] {"Id", "Employe", "Date de bebut", "Date de fin", "Type", "solde"});
	public JTable jt=new JTable(tableModel);
	
	public JButton ajouterButton=new JButton("Ajouter"), modifierButton=new JButton("Modifier"), supprimerButton=new JButton("Supprimer"), afficherButton=new JButton("Afficher");
	
	public HolidayView(){
		
		setTitle("Gestion des conges");
		setLocationRelativeTo(null);
		setSize(600, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		add(jp1);
		jp1.setLayout(new BorderLayout());
		jp1.add(jp2, BorderLayout.NORTH);
		jp2.setLayout(new GridLayout(4, 2));
		jp2.add(jlNom);
		jp2.add(employeeNameComboBox);
		jp2.add(jlType);
		jp2.add(holidayType);
		startDateSpinner.setEditor(startDateEditor);
		jp2.add(jlDateDebut);
		endDateSpinner.setEditor(endDateEditor);
		jp2.add(startDateSpinner);
		jp2.add(jlDateFin);
		jp2.add(endDateSpinner);
		
		jp1.add(jp3, BorderLayout.CENTER);
		jp3.setLayout(new BorderLayout());
		JScrollPane jsp=new JScrollPane(jt);
		jp3.add(jsp, BorderLayout.CENTER);
		
		jp1.add(jp4, BorderLayout.SOUTH);
		jp4.setLayout(new FlowLayout());
		jp4.add(ajouterButton);
		jp4.add(modifierButton);
		jp4.add(supprimerButton);
		jp4.add(afficherButton);
		
		jt.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 2) {
		            int selectedRow = jt.getSelectedRow();
		            if (selectedRow != -1) {
		                try {
		                    employeeNameComboBox.setSelectedItem(tableModel.getValueAt(selectedRow, 1).toString());

		                    Object startDateObj = tableModel.getValueAt(selectedRow, 2);
		                    if (startDateObj instanceof Date) {
		                        startDateSpinner.setValue(startDateObj);
		                    } else {
		                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		                        startDateSpinner.setValue(sdf.parse(startDateObj.toString()));
		                    }

		                    Object endDateObj = tableModel.getValueAt(selectedRow, 3);
		                    if (endDateObj instanceof Date) {
		                        endDateSpinner.setValue(endDateObj);
		                    } else {
		                        startDateSpinner.setValue(new SimpleDateFormat("dd/MM/yyyy").parse(endDateObj.toString()));
		                    }

		                    holidayType.setSelectedItem(HolidayType.valueOf(tableModel.getValueAt(selectedRow, 4).toString()));
		                } catch (Exception ex) {
		                    afficherMessageErreur("Erreur lors de la récupération des données : " + ex.getMessage());
		                }
		            }
		        }
		    }
		});

		//setVisible(true);	
		
	}
	
	public void afficherHolidays(List<Holiday> holidays) {
		tableModel.setRowCount(0);
		for (Holiday elm : holidays) {
		    tableModel.addRow(new Object[] {
		        elm.getId(),
		        elm.getEmployeeNom(),
		        elm.getStartDate(),
		        elm.getEndDate(),
		        elm.getHolidayType().name(),
		        elm.getSolde()
		    });
		}
	}
	
	public int getId() {
	    int selectedRow = jt.getSelectedRow();
	    if (selectedRow != -1) {
	        Object value = tableModel.getValueAt(selectedRow, 0);
	        return Integer.parseInt(value.toString());
	    }
	    return -1;
	}
	
	public HolidayType getHolidayType() {
		return (HolidayType) holidayType.getSelectedItem();
	}
	public Date getStartDate() {
	    return (Date) startDateSpinner.getValue();
	}

	public Date getEndDate() {
	    return (Date) endDateSpinner.getValue();
	}
	
    public void afficherMessageErreur(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    public void afficherMessageSucces(String message) {
        JOptionPane.showMessageDialog(this, message, "Succes", JOptionPane.INFORMATION_MESSAGE);
    }
	
}
