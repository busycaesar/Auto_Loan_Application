package Controller;

import Model.*;

public class VehicleLoanController {
	
	private 	   Finance    finance;
	// Linked list to store the list of finance object.
	private static LinkedList storedFinanceList = new LinkedList();
	
	public VehicleLoanController(String _type, String _age, double _price, double _downPayment,
								 int _durationInMonths, String _frequency) {

		// Calculate the loan amount using the price of the vehicle and the downpayment.
		double  _loanAmount    = _price - _downPayment;
		Vehicle _vehicle  	   = new Vehicle(_type, _age, _price);
		
		this.finance = new Finance(_vehicle, _loanAmount, 0, 0, _durationInMonths, _frequency);
		
		this.updateROIAndPaymentAmount();
		
	}
	
	private double calculateROI(String _type, String _age, int _durationInMonths) {
		
		// Base rate of interest.
		double roi = 1.5;
		
		// Update ROI as per the vehicle parameters.
		// Vehicle Type
		if(_type == "Truck") { roi += 2; }
		else if(_type == "Family Van"){ roi += 1; }
		// Vehicle Age
		if(_age == "Old") { roi += 1; }
		// Frequency
		if(_durationInMonths < 30) { roi += 0.5; }
		else if(_durationInMonths < 45) { roi += 1; }
		
		return roi;
	
	}
	
	private double calculatePaymentAmount(double _interestRate, String _frequency, int _durationInMonths, double _loanAmount) {
		
		// Calculate the monthly interest rate
		double monthlyInterestRate = _interestRate / 12 / 100;
		int	   numberOfPayments	   = 0;
		
		switch(_frequency) {
		case "Monthly":
			numberOfPayments = _durationInMonths * 1;
			break;
		case "Bi-weekly":
			numberOfPayments = _durationInMonths * 2;
			break;
		case "Weekly":
			numberOfPayments = _durationInMonths * 4;
			break;
		default:
			System.out.println("Invalid payment frequency");
			break;
		}
		
		// This is the actual logic of the application. This is the place where the loan repayment amount is being calculated.
		return _loanAmount * monthlyInterestRate / (1 - Math.pow(1 + monthlyInterestRate, -numberOfPayments));
	
	}
	
	// Update rate of interest and loan repayment amount as per the duration.
	private void updateROIAndPaymentAmount() {

		double newROI			= this.calculateROI(this.finance.getVehicleType(), this.finance.getVehicleAge(), this.finance.getLoanDuration()),
			   newPaymentAmount = this.calculatePaymentAmount(newROI, this.finance.getLoanPaymentFrequency(), this.finance.getLoanDuration(), this.finance.getLoanAmount());
		
		this.finance.setInterestRate(newROI);
		this.finance.setPaymentAmount(newPaymentAmount);
		
	}
	
	// Getters and setters.
	
	public int getLoanDuration() { return this.finance.getLoanDuration(); }
	
	public void setLoanDuration(int _durationInMonths){
	
		this.finance.setLoanDuration(_durationInMonths);
		
		// After updating the loan duration, update the dependent properties.
		this.updateROIAndPaymentAmount();
		
	}

	public double 	 getLoanAmount()   		   { return this.finance.getLoanAmount(); 					 }
	
	public double    getInterestRate() 		   { return this.finance.getInterestRate(); 				 }
	
	public double 	 getPaymentAmount()        { return this.finance.getPaymentAmount(); 				 }
	
	public double 	 getDownPayment()		   { return this.finance.getVehiclePrice()
														- this.finance.getLoanAmount(); 				 }
	
	public String 	 getLoanPaymentFrequency() { return this.finance.getLoanPaymentFrequency(); }
	
	public int    	 totalStoredFinances() 	   { return VehicleLoanController.storedFinanceList.count(); }
	
	public String 	 getVehicleType()		   { return this.finance.getVehicleType(); 					 }
	
	public String    getVehicleAge() 		   { return this.finance.getVehicleAge(); 					 }
	
	public double	 getVehiclePrice()		   { return this.finance.getVehiclePrice(); 				 }


	// Functions for working with static variable.
	
	// Return the array of required properties from finance object.
	public double[][] getStoredRates() { 
	
		Finance[] 	_storedRates 		= VehicleLoanController.storedFinanceList.getAllData(); 
		int 		_totalStoredFinance = _storedRates.length;
		double [][] _storedRatesArray   = new double[_totalStoredFinance][4];
		
		for(int index = 0; index < _totalStoredFinance; index++) {
			_storedRatesArray[index][0] = _storedRates[index].getLoanAmount();
			_storedRatesArray[index][1] = _storedRates[index].getInterestRate();
			_storedRatesArray[index][2] = _storedRates[index].getLoanDuration();
		}
		
		return _storedRatesArray;
	}
	
	public void save() { VehicleLoanController.storedFinanceList.insert(this.finance); }
	
	// Load the finance object stored at the passed index.
	public void loadRateAt(int _index) {
	
		this.finance = VehicleLoanController.storedFinanceList.getDataAt(_index);
		
		if(this.finance == null)System.out.println("Finance is null");
		else System.out.println("Finance is not null");
	
	}
	
}