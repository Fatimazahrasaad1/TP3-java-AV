package Controller;

import java.util.Date;
import java.util.List;

import Model.HolidayModel;
import View.HolidayView;
import Model.Holiday;
import Model.Holiday.HolidayType;

public class HolidayController {
	
	private HolidayView view;
	private HolidayModel model;
	
	public HolidayController(HolidayView view, HolidayModel model){
		this.model=model;
		this.view=view;
		afficherHolidays();
		view.ajouterButton.addActionListener(e -> {
			ajouterHoliday();
			afficherHolidays();
		});
		
		chargerNomsEmployes();
		
		view.afficherButton.addActionListener(e -> afficherHolidays());
		
	    this.view.supprimerButton.addActionListener(e -> {
	        supprimerHoliday();    
	        afficherHolidays();    
	    });
	    
	    view.modifierButton.addActionListener(e -> {
	        modifierHoliday();
	        afficherHolidays();
	    });

	}
	
	public void modifierHoliday() {
	    int id = view.getId();
	    
	    if (id <= 0) {
	        view.afficherMessageErreur("Sélectionnez un congé à modifier.");
	        return;
	    }

	    String employeeName = (String) view.employeeNameComboBox.getSelectedItem();
	    Date startDate = view.getStartDate();
	    Date endDate = view.getEndDate();
	    HolidayType holidayType = view.getHolidayType();

	    if (!model.isValidDateRange(startDate, endDate)) {
	        view.afficherMessageErreur("Plage de dates invalide.");
	        return;
	    }

	    boolean modificationReussie = model.modifierHoliday(id, employeeName, startDate, endDate, holidayType);
	    if (modificationReussie) {
	        view.afficherMessageSucces("Congé modifié avec succès.");
	    } else {
	        view.afficherMessageErreur("La modification a échoué.");
	    }

	    afficherHolidays(); // Recharge la table
	}


	
	public void ajouterHoliday() {

		String employeeName = (String) view.employeeNameComboBox.getSelectedItem();
		Date startDate=view.getStartDate(), endDate=view.getEndDate();
		HolidayType holidayType=view.getHolidayType();
		
		boolean ajoutResult=model.ajouterHoliday(employeeName, startDate, endDate, holidayType);
		
		if (ajoutResult) {
			view.afficherMessageSucces("Ajout reussi :)");
		}else {
			view.afficherMessageErreur("Ajout failed :(");
		}
	}
	
	public void afficherHolidays(){
		
		List<Holiday> holidays=model.afficherHolidays();
		view.afficherHolidays(holidays);
	}
	
    public void chargerNomsEmployes() {
        view.employeeNameComboBox.removeAllItems();
        
        List<String> names = model.chargerNomsEmployes();

        if (names.isEmpty()) {
            System.out.println("Aucun employe trouve.");
        } else {
        	
            for (String name : names) {
                view.employeeNameComboBox.addItem(name);
            }
            
        }
    }
    
    public void supprimerHoliday(){
    		
    		int id=view.getId();
    		
    		if(id<=0) {
    			view.afficherMessageErreur("Selectionner un employee pour le supprimer.");
    			return;
    		}
    		
    		boolean suppressionReussi=model.supprimerHoliday(id);
    		
    		if(suppressionReussi) {
    			view.afficherMessageSucces("Le conges a ete supprimer :)");
    		}else {
    			view.afficherMessageErreur("Le conges n'a pas ete supprime :(");
    		}
    }

}
