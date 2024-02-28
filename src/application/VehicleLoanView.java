package application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.*;

import Controller.VehicleLoanController;

public class VehicleLoanView {

	@FXML
	private ChoiceBox<String> vehicleType, loanPaymentFrequency;
	@FXML
	private RadioButton newVehicleAge, oldVehicleAge;
	@FXML
	private ToggleGroup vehicleAge;
	@FXML
	private TextField vehiclePrice, vehicleDownpayment, loanInterestRate;
	@FXML
	private Slider loanDuration;
	@FXML
	private Text displayLoan, displayWarning;
	
	// Controller
	private VehicleLoanController vehicleLoanController;
	
	public void initialize() {
		
		this.fillChoiceBoxes();
		this.setDefaults();
		
	}

	// Fill all the choices into the choice box.
	private void fillChoiceBoxes() {
		
		this.vehicleType.getItems().addAll("Car", "Truct", "Family Van");
		this.loanPaymentFrequency.getItems().addAll("Monthly", "Bi-weekly", "Weekly");
		this.vehicleAge = new ToggleGroup();
		this.newVehicleAge.setToggleGroup(this.vehicleAge);
		this.oldVehicleAge.setToggleGroup(this.vehicleAge);
		
	}
	
	//Set all the default values of the form.
	private void setDefaults() {
		this.vehicleType.setValue("Car");
		this.loanPaymentFrequency.setValue("Monthly");
		this.newVehicleAge.setSelected(true);
		this.oldVehicleAge.setSelected(false);
		this.vehiclePrice.setText("");
		this.vehicleDownpayment.setText("");
		this.loanInterestRate.setText("");
		this.loanDuration.setValue(12);
		this.displayLoan.setText("");
		this.displayWarning.setText("");
	}
	
	@FXML
	public void clearForm() {
		this.setDefaults();
	}

	@FXML
	// Fetch all the data filled by the user and display the loan details.
	public void calculateLoan() {
		
		if(!this.checkFormFields()) {
			this.displayWarning.setText("Fields marked with * are required!");
			return;
		}
		
		String _vehicleType  		 = this.vehicleType.getValue(),
			   _vehicleAge   		 = ((RadioButton)this.vehicleAge.getSelectedToggle()).getText(),
			   _loanPaymentFrequency = this.loanPaymentFrequency.getValue();
		
		try {
			
			double _vehiclePrice 	   = Double.parseDouble(this.vehiclePrice.getText()),
				   _vehicleDownPayment = Double.parseDouble(this.vehicleDownpayment.getText()),
				   _interestRate	   = Double.parseDouble(this.loanInterestRate.getText());
			int    _loanDuration	   = (int)this.loanDuration.getValue();
			
			if(_vehiclePrice <= _vehicleDownPayment) {
				this.displayWarning.setText("Vehicle's price cannot be less than or equal to the down payment!");
				return;
			}
			
			this.vehicleLoanController = new VehicleLoanController(_vehicleType, _vehicleAge, _vehiclePrice, _vehicleDownPayment, _interestRate, _loanDuration, _loanPaymentFrequency);
			
			this.setDefaults();
			
			this.displayLoanDetails();
			
		}
		catch (NumberFormatException e){ 
			System.out.println(e.getMessage());
			this.displayWarning.setText("By mistake you did not put numeric characters in the field of Vehicle price, downpayment and/or interest rate!");
		}
		
	}
	
	// Make sure the user filled all the required fields before submitting the form.
	private boolean checkFormFields() {
		return !this.vehiclePrice.getText().isEmpty()
			&& !this.vehicleDownpayment.getText().isEmpty()
			&& !this.loanInterestRate.getText().isEmpty();
	}
	
	// Convert the amount(double) into the currency format(string).
	private String currenyFormat(double _amount) { return String.format("%.2f", _amount); }
	
	// Display all the loan details as required by the user.
	private void displayLoanDetails() {
		
		String _loanAmount       = this.currenyFormat(this.vehicleLoanController.getLoanAmount()),
			   _interestRate     = this.currenyFormat(this.vehicleLoanController.getInterestRate()),
			   _durationInMonths = Integer.toString(this.vehicleLoanController.getLoanDuration()),
			   _paymentAmount 	 = this.currenyFormat(this.vehicleLoanController.getPaymentAmount()),
			   _frequency 		 = this.vehicleLoanController.getLoanPaymentFrequency();
		
		if(_loanAmount 		 != ""
		&& _interestRate 	 != ""
		&& _durationInMonths != ""
		&& _paymentAmount 	 != ""
		&& _frequency 		 != "") {
			
			String _message = "You can borrow $" + _loanAmount + " at " + _interestRate
							  + "% interest rate for " + _durationInMonths
							  + " months.\nYou will pay $" + _paymentAmount + " "
							  + _frequency + ".";
			
			this.displayLoan.setText(_message);
			
		}
		else this.displayLoan.setText("Internal system error!");
		
	}
	
	@FXML
	// Store the displayed loan details into a list.
	public void storeLoanDetails() { 
		
		System.out.println("Storing Details");
		this.vehicleLoanController.save();
	
	}
	
	@FXML
	// Display the loan details stored into the list.
	public void showLoanDetailList() { 
		
		System.out.println("Showing stored Details");
		this.displayLoan.setText(Integer.toString(this.vehicleLoanController.totalStoredFinances()));
	
	}
	
}
