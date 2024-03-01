package Controller;

import Model.*;

public class VehicleLoanController {
	
	// Finance object to store the data.
	private 	   Finance    finance;
	// Linked list to store the list of the loan details.
	private static LinkedList storedFinanceList = new LinkedList();
	
	public VehicleLoanController(String _type, String _age, double _price, double _downPayment, int _durationInMonths, String _frequency) {

		// Calculating the loan amount using the price of the vehicle and the downpayment.
		double  _loanAmount    = _price - _downPayment;
		Vehicle _vehicle  	   = new Vehicle(_type, _age, _price);
		
		// Storing the data into the finance object.
		this.finance 		   = new Finance(_vehicle, _loanAmount, 0, 0, _durationInMonths, _frequency);
		this.updateROIAndPaymentAmount();
		
	}
	
	private double calculateROI(String _type, String _age, int _durationInMonths) {
		// Base rate of interest.
		double roi = 1.5;
		
		// Updating ROI as per the vehicle parameters.
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
		
		// Calculating the monthly interest rate
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
	
	// Getters and setters.
	public double getLoanAmount()    					 { return this.finance.getLoanAmount(); }
	
	public double getInterestRate()  					 { return this.finance.getInterestRate(); }
	
	public int 	  getLoanDuration()  					 { return this.finance.getLoanDuration(); }
	
	public void   setLoanDuration(int _durationInMonths){
	
		this.finance.setLoanDuration(_durationInMonths);
		this.updateROIAndPaymentAmount();
		
	}
	
	private void updateROIAndPaymentAmount() {

		double newROI			= this.calculateROI(this.finance.getVehicleType(), this.finance.getVehicleAge(), this.finance.getLoanDuration()),
			   newPaymentAmount = this.calculatePaymentAmount(newROI, this.finance.getLoanPaymentFrequency(), this.finance.getLoanDuration(), this.finance.getLoanAmount());
		
		this.finance.setInterestRate(newROI);
		this.finance.setPaymentAmount(newPaymentAmount);
		
	}
	
	public double 	 getPaymentAmount()        { return this.finance.getPaymentAmount(); }
	
	public String 	 getLoanPaymentFrequency() { return this.finance.getLoanPaymentFrequency(); }
	
	public int    	 totalStoredFinances() 	   { return VehicleLoanController.storedFinanceList.count(); }
	
	public String 	 getVehicleType()		   { return this.finance.getVehicleType(); }
	
	public String    getVehicleAge() 		   { return this.finance.getVehicleAge(); }

	// Store the current finance object into the linked list.
	public void      save() 				   { VehicleLoanController.storedFinanceList.insert(this.finance); }

	public double[][] getStoredRates() { 
	
		Finance[] 	_storedRates 		= VehicleLoanController.storedFinanceList.getAllData(); 
		int 		_totalStoredFinance = _storedRates.length;
		double [][] _storedRatesArray   = new double[_totalStoredFinance][4];
		
		for(int index = 0; index < _totalStoredFinance; index++) {
			_storedRatesArray[index][0] = index;
			_storedRatesArray[index][1] = _storedRates[index].getLoanAmount();
			_storedRatesArray[index][2] = _storedRates[index].getInterestRate();
			_storedRatesArray[index][3] = _storedRates[index].getLoanDuration();
		}
		
		return _storedRatesArray;
	}
	
	// Loan the value of the finance object stored at the passed index.
	public void loanRate(int _index) {
		this.finance = VehicleLoanController.storedFinanceList.getDataAt(_index);
	}
	
}