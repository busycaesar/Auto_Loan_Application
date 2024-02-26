package application;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class VehicleLoanView {

	@FXML
	private ChoiceBox<String> vehicleType, loanPaymentFrequency;
	@FXML
	private RadioButton newVehicleAge, oldVehicleAge;
	@FXML
	private ToggleGroup vehicleAge;
	@FXML
	private TextField vehiclePrice, vehicleDownpayment, loanInterestRate;
	
	public void initialize() {
		
		this.fillChoiceBoxes();
		this.setDefaults();
		
	}
	
	private void fillChoiceBoxes() {
		
		this.vehicleType.getItems().addAll("Car", "Truct", "Family Van");
		this.loanPaymentFrequency.getItems().addAll("Monthly", "Bi-weekly", "Weekly");
		this.vehicleAge = new ToggleGroup();
		this.newVehicleAge.setToggleGroup(this.vehicleAge);
		this.oldVehicleAge.setToggleGroup(this.vehicleAge);
		
	}
	
	// This function sets default value to all the form fields.
	private void setDefaults() {
		this.vehicleType.setValue("Car");
		this.loanPaymentFrequency.setValue("Monthly");
		this.newVehicleAge.setSelected(true);
		this.oldVehicleAge.setSelected(false);
		this.vehiclePrice.setText("");
		this.vehicleDownpayment.setText("");
		this.loanInterestRate.setText("");
	}
	
	@FXML
	public void clearForm() {
		this.setDefaults();
	}

	@FXML
	public void calculateLoan() {
		String vehicleType  		= this.vehicleType.getValue(),
			   vehicleAge   		= ((RadioButton)this.vehicleAge.getSelectedToggle()).getText(),
			   loanPaymentFrequency = this.loanPaymentFrequency.getValue();
		
		try {
			
			double vehiclePrice 	  = Double.parseDouble(this.vehiclePrice.getText()),
				   vehicleDownPayment = Double.parseDouble(this.vehicleDownpayment.getText()),
				   loanInterestRate	  = Double.parseDouble(this.loanInterestRate.getText());
			
		}
		catch (NumberFormatException e){ 
			System.out.println(e.getMessage());
		}
		
	}
	
}
