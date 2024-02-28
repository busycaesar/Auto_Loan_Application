package Controller;

import Model.*;

public class VehicleLoanController {
	
	// Finance object to store the data.
	private 	   Finance    finance;
	// Linked list to store the list of the loan details.
	private static LinkedList storedFinanceList = new LinkedList();
	
	public VehicleLoanController(String _type, String _age, double _price, double _downPayment, double _interestRate, int _durationInMonths, String _frequency) {

		// Calculating the loan amount using the price of the vehicle and the downpayment.
		double  loanAmount     = _price - _downPayment,
				// Calculating the repayment amount according to the required frequency and other parameters.
			    _paymentAmount = this.calculatePaymentAmount(_interestRate, _frequency, _durationInMonths, loanAmount);
		Vehicle vehicle  	   = new Vehicle(_type, _age, _price);
		
		// Storing the data into the finance object.
		this.finance 		   = new Finance(vehicle, loanAmount, _interestRate, _paymentAmount, _durationInMonths, _frequency);
		
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
	
	// Getter functions.
	public double getLoanAmount()    		{ return this.finance.getLoanAmount(); }
	
	public double getInterestRate()  		{ return this.finance.getInterestRate(); }
	
	public int 	  getLoanDuration()  		{ return this.finance.getLoanDuration(); }
	
	public double getPaymentAmount()        { return this.finance.getPaymentAmount(); }
	
	public String getLoanPaymentFrequency() { return this.finance.getLoanPaymentFrequency(); }
	
	public int    totalStoredFinances() 	{ return VehicleLoanController.storedFinanceList.count(); }
	
	public String getVehicleType()			{ return this.finance.getVehicleType(); }
	
	public String getVehicleAge() 			{ return this.finance.getVehicleAge(); }

	// Storing the current finance object into the linked list.
	public void   save() 					{ VehicleLoanController.storedFinanceList.insert(finance); }
	
}
