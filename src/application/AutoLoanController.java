package application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.event.*;
import javafx.beans.value.*;

import java.util.Random;
import Controller.VehicleLoanController;
import javafx.scene.layout.*;
import javafx.collections.*;

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

	// Fill the options at all the places required.
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
		
		// Get the data from form fields.
		String _vehicleType  		 = this.vehicleType.getValue(),
			   _vehicleAge   		 = ((RadioButton)this.vehicleAge.getSelectedToggle()).getText(),
			   _loanPaymentFrequency = this.loanPaymentFrequency.getValue();
		
		try {
			
			double _vehiclePrice 	   = Double.parseDouble(this.vehiclePrice.getText()),
				   _vehicleDownPayment = Double.parseDouble(this.vehicleDownpayment.getText());
			int    _loanDuration	   = (int)this.loanDuration.getValue();
			
			// Make sure proper data is enter in the fields.
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
		
		// Get details from the controller.
		String _vehicleType		 = this.vehicleLoanController.getVehicleType(),
			   _vehicleAge		 = this.vehicleLoanController.getVehicleAge(),
			   _loanAmount       = this.currenyFormat(this.vehicleLoanController.getLoanAmount()),
			   _interestRate     = this.currenyFormat(this.vehicleLoanController.getInterestRate()),
			   _durationInMonths = Integer.toString(this.vehicleLoanController.getLoanDuration()),
			   _paymentAmount 	 = this.currenyFormat(this.vehicleLoanController.getPaymentAmount()),
			   _frequency 		 = this.vehicleLoanController.getLoanPaymentFrequency();

		// Making sure all the details are available.
		if(_loanAmount 		 != ""
		&& _interestRate 	 != ""
		&& _durationInMonths != ""
		&& _paymentAmount 	 != ""
		&& _frequency 		 != "") {
			
			// Create the message with data to display.
			String _message = "For your " + _vehicleAge.toLowerCase() + " "
							  + _vehicleType.toLowerCase() + ", you can borrow $"
							  + _loanAmount + " at " + _interestRate
							  + "% rate of interest.\nYou will pay $" + _paymentAmount + " "
							  + _frequency.toLowerCase() + " for next " + _durationInMonths + " months.";
			
			this.displayLoan.setText(_message);
			
		}
		else this.displayLoan.setText("Internal system error!");
		
	}
	
	// Generate a random number between the passed range.
    private double randomNum(double min, double max) {
    	
        if (min >= max) { throw new IllegalArgumentException("Max must be greater than min"); }

        Random random = new Random();
        return random.nextDouble(min, max);
        
    }
    
    // Loan the stored details into the form and show the details.
    private void loadStoredRates() {
    	
    	this.vehicleType.setValue(this.vehicleLoanController.getVehicleType());
    	this.newVehicleAge.setSelected(this.vehicleLoanController.getVehicleAge()=="New");
		this.oldVehicleAge.setSelected(this.vehicleLoanController.getVehicleAge()=="Old");
		this.vehicleDownpayment.setText(this.currenyFormat(this.vehicleLoanController.getDownPayment()));
		this.vehiclePrice.setText(this.currenyFormat(this.vehicleLoanController.getVehiclePrice()));
		this.loanPaymentFrequency.setValue(this.vehicleLoanController.getLoanPaymentFrequency());
		this.loanDuration.setValue(this.vehicleLoanController.getLoanDuration());
		this.loanDurationFormFieldVisibility(true);
		
		this.displayLoanDetails();
		
    }
	
	@FXML
	// Add sample data into the from for testing.
	public void addSampleData() {
		
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
	// Store the displayed loan details into the list.
	public void storeRate() { 
		
		System.out.println("Storing Details");
		
		this.vehicleLoanController.save();
		this.setDefaults();
		this.displayLoan.setText("Rates stored successfully!");
	
	}
	
	// Parse 2D array of stored rates into an array of string.
	private String[] parseStoredRateList(double[][] _storedRates) {
		
		int 	 _totalStoredRates  = _storedRates.length;
		String[] _storedRatesString = new String[_totalStoredRates];
		
		for(int index = 0; index < _totalStoredRates; index++) {
			_storedRatesString[index] = "$" + this.currenyFormat(_storedRates[index][0]) + " at "
										+ this.currenyFormat(_storedRates[index][1])
										+ "% rate of interest for " 
										+ Integer.toString((int)_storedRates[index][2]) + " months.";
		}
		
		return _storedRatesString;
		
	}
	
	@FXML
	// Display the loan details stored into the list.
	public void showSavedRates() { 
		
		String[] 		   _storedRatesString = parseStoredRateList(this.vehicleLoanController.getStoredRates());
		ListView<String>   _storedRatesList   = new ListView<String>();
		Dialog<ButtonType> dialog 			  = new Dialog<>();
		ButtonType 		   showButton 		  = new ButtonType("Show");
		VBox 			   vbox 			  = new VBox(_storedRatesString.length);

		// Add items into the ListView.
		_storedRatesList.setItems(FXCollections.observableArrayList(_storedRatesString));
		_storedRatesList.setPrefSize(500, 350);
		
		// Add ListView into vbox.
		vbox.getChildren().addAll(_storedRatesList);
		
		// Configure dialog box.
		dialog.setTitle("Saved Loan Rates");
		dialog.setHeaderText("Click on rate to load it.");
		
		// Add items into dialog box.
		dialog.getDialogPane().getButtonTypes().add(showButton);
		dialog.getDialogPane().setContent(vbox);
		
		// Add event listener to the button to loan the rate requested by customer.
		Button okButton = (Button) dialog.getDialogPane().lookupButton(showButton);
		okButton.addEventHandler(ActionEvent.ACTION, event -> {
	         int _selectedIndex = _storedRatesList.getSelectionModel().getSelectedIndex();
	         if(_selectedIndex > -1) {
	        	 this.vehicleLoanController.loadRateAt(_selectedIndex);
	        	 this.loadStoredRates();
	         }
	     });

		// Show the dialog box.
	    dialog.showAndWait();
		
	}
	
}