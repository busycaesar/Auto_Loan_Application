package application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.event.*;
import javafx.beans.value.*;
import java.util.Random;
import Controller.VehicleLoanController;

public class AutoLoanController {

	@FXML
	private ChoiceBox<String> 	  vehicleType,
							  	  loanPaymentFrequency;
	@FXML
	private RadioButton 		  newVehicleAge,
								  oldVehicleAge;
	@FXML
	private ToggleGroup 		  vehicleAge;
	@FXML
	private TextField 			  vehiclePrice,
					  			  vehicleDownpayment;
	@FXML
	private Slider 				  loanDuration;
	@FXML
	private Text 				  displayLoan,
				 				  displayWarning;
	@FXML
	private Button 				  clearFormButton,
				    			  calculateLoanButton,
				    			  saveRateButton,
				    			  showSavedRatesButton;
	@FXML
	private Label				  loanDurationLabel;
	
	// Controller
	private VehicleLoanController vehicleLoanController;
	
	public void initialize() {
		
		this.setEventHandlers();
		this.fillChoiceBoxes();
		this.setDefaults();
		
	}
	
	// Set event handlers for all the buttons
	private void setEventHandlers() {
		
		this.clearFormButton.setOnAction(clearButtonClickHandler);	
		this.calculateLoanButton.setOnAction(calculateButtonClickHandler);
		this.saveRateButton.setOnAction(saveRateButtonClickHandler);
		this.showSavedRatesButton.setOnAction(showSavedRatesButtonClickHandler);
		this.loanDuration.valueProperty().addListener(loanDurationSliderListener);
		
	}

	// Fill all the choices into the choice box.
	private void fillChoiceBoxes() {
		
		this.vehicleType.getItems().addAll("Car", "Truck", "Family Van");
		this.loanPaymentFrequency.getItems().addAll("Monthly", "Bi-weekly", "Weekly");
		this.vehicleAge = new ToggleGroup();
		this.newVehicleAge.setToggleGroup(this.vehicleAge);
		this.oldVehicleAge.setToggleGroup(this.vehicleAge);
		
	}
	
	// Set all the default values of the form.
	private void setDefaults() {
		
		this.vehicleType.setValue("Car");
		this.loanPaymentFrequency.setValue("Monthly");
		this.newVehicleAge.setSelected(true);
		this.oldVehicleAge.setSelected(false);
		this.vehiclePrice.setText("");
		this.vehicleDownpayment.setText("");
		this.loanDuration.setValue(12);
		this.displayLoan.setText("");
		this.displayWarning.setText("");
		this.loanDurationFormFieldVisibility(false);
		
	}
	
	// Event handlers for buttons.
	private EventHandler<ActionEvent> clearButtonClickHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) { setDefaults(); }
	};
	
	private EventHandler<ActionEvent> calculateButtonClickHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) { calculateLoan(); }
	};
	
	private EventHandler<ActionEvent> saveRateButtonClickHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) { storeRate(); }
	};
	
	private EventHandler<ActionEvent> showSavedRatesButtonClickHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) { showSavedRates(); }
	};
	
	private ChangeListener<Number> loanDurationSliderListener = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        	vehicleLoanController.setLoanDuration(newValue.intValue());
        	displayLoanDetails();
        }
	};
	
	// Maintain the visibility of loan duration form field.
	private void loanDurationFormFieldVisibility(boolean _visibility) {
		this.loanDurationLabel.setVisible(_visibility);
		this.loanDuration.setVisible(_visibility);
	}
	
	// Fetch all the data filled by the user and display the loan details.
	private void calculateLoan() {
		
		if(!this.checkFormFields()) {
			this.displayWarning.setText("Fields marked with * are required!");
			return;
		}
		
		String _vehicleType  		 = this.vehicleType.getValue(),
			   _vehicleAge   		 = ((RadioButton)this.vehicleAge.getSelectedToggle()).getText(),
			   _loanPaymentFrequency = this.loanPaymentFrequency.getValue();
		
		try {
			
			double _vehiclePrice 	   = Double.parseDouble(this.vehiclePrice.getText()),
				   _vehicleDownPayment = Double.parseDouble(this.vehicleDownpayment.getText());
			int    _loanDuration	   = (int)this.loanDuration.getValue();
			
			if(_vehiclePrice <= _vehicleDownPayment) {
				System.out.println("Print message");
				this.displayWarning.setText("Vehicle's price cannot be less than or equal to the down payment!");
				return;
			}
			
			this.vehicleLoanController = new VehicleLoanController(_vehicleType, _vehicleAge, _vehiclePrice, _vehicleDownPayment, _loanDuration, _loanPaymentFrequency);
			
			this.setDefaults();
			
			this.displayLoanDetails();
			
			this.loanDurationFormFieldVisibility(true);
			
		}
		catch (NumberFormatException e){ 
			System.out.println(e.getMessage());
			this.displayWarning.setText("By mistake you did not put numeric characters in the field of Vehicle price, downpayment and/or interest rate!");
		}
		
	}
	
	// Make sure the user filled all the required fields before submitting the form.
	private boolean checkFormFields() {
		return !this.vehiclePrice.getText().isEmpty()
			&& !this.vehicleDownpayment.getText().isEmpty();
	}
	
	// Convert the amount(double) into the currency format(string).
	private String currenyFormat(double _amount) { return String.format("%.2f", _amount); }
	
	// Display all the loan details as required by the user.
	private void displayLoanDetails() {
		
		String _vehicleType		 = this.vehicleLoanController.getVehicleType(),
			   _vehicleAge		 = this.vehicleLoanController.getVehicleAge(),
			   _loanAmount       = this.currenyFormat(this.vehicleLoanController.getLoanAmount()),
			   _interestRate     = this.currenyFormat(this.vehicleLoanController.getInterestRate()),
			   _durationInMonths = Integer.toString(this.vehicleLoanController.getLoanDuration()),
			   _paymentAmount 	 = this.currenyFormat(this.vehicleLoanController.getPaymentAmount()),
			   _frequency 		 = this.vehicleLoanController.getLoanPaymentFrequency();

		if(_loanAmount 		 != ""
		&& _interestRate 	 != ""
		&& _durationInMonths != ""
		&& _paymentAmount 	 != ""
		&& _frequency 		 != "") {
			
			String _message = "For your " + _vehicleAge.toLowerCase() + " "
							  + _vehicleType.toLowerCase() + ", you can borrow $"
							  + _loanAmount + " at " + _interestRate
							  + "% rate of interest.\nYou will pay $" + _paymentAmount + " "
							  + _frequency.toLowerCase() + " for next " + _durationInMonths + " months.";
			
			this.displayLoan.setText(_message);
			
		}
		else this.displayLoan.setText("Internal system error!");
		
	}
	
    private double randomNum(double min, double max) {
        if (min >= max) {
            throw new IllegalArgumentException("Max must be greater than min");
        }

        Random random = new Random();
        return random.nextDouble(min, max);
    }
	
	@FXML
	public void addSampleData() {
		
		//1-3
		double _vehicleDownPayment = this.randomNum(100, 5000),
		_vehiclePrice = this.randomNum(_vehicleDownPayment + 500, 50000);

		if (_vehicleDownPayment < 3000) {
			this.vehicleType.setValue("Family Van");
			this.loanPaymentFrequency.setValue("Bi-weekly");			
		}
		else { this.loanPaymentFrequency.setValue("Weekly"); }
		
		if (_vehiclePrice > 35000) {
			this.newVehicleAge.setSelected(false);
			this.oldVehicleAge.setSelected(true);			
		}
		
		this.vehiclePrice.setText(this.currenyFormat(_vehiclePrice));
		this.vehicleDownpayment.setText(this.currenyFormat(_vehicleDownPayment));
	}
	
	@FXML
	// Store the displayed loan details into a list.
	public void storeRate() { 
		
		System.out.println("Storing Details");
		this.vehicleLoanController.save();
		this.setDefaults();
		this.displayLoan.setText("Rates stored successfully!");
	
	}
	
	@FXML
	// Display the loan details stored into the list.
	public void showSavedRates() { 
		
		// Display the list of rates.
	
	}
	
}