package application;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;

public class VehicleLoanView {

	@FXML
	private ChoiceBox<String> vehicleType, loanPaymentFrequency;
	@FXML
	private RadioButton newVehicleAge, oldVehicleAge;
	
	public void initialize() {
		
		this.fillChoiceBoxes();
		this.setDefaults();
		
	}
	
	private void fillChoiceBoxes() {
		
		this.vehicleType.getItems().addAll("Car", "Truct", "Family Van");
		this.loanPaymentFrequency.getItems().addAll("Monthly", "Bi-weekly", "Weekly");
		
	}
	
	private void setDefaults() {
		this.vehicleType.setValue("Car");
		this.loanPaymentFrequency.setValue("Monthly");
		this.newVehicleAge.setSelected(true);
		this.oldVehicleAge.setSelected(false);
	}
	
}
