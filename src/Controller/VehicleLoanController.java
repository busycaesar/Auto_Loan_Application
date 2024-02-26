package Controller;

import Model.*;

public class VehicleLoanController {
	
	private Finance finance;
	
	public VehicleLoanController(String _type, String _age, double _price, double _downPayment, double _interestRate, int _durationInMonths, String _frequency) {

		double _paymentAmount = this.calculatePaymentAmount();
		Vehicle vehicle = new Vehicle(_type, _age, _price);
		this.finance = new Finance(vehicle, _price - _downPayment, _interestRate, _paymentAmount, _durationInMonths, _frequency);
		
	}
	
	private double calculatePaymentAmount() {
		// Calculate the loan amount according to 
		return 0.0;
	}
	
	public double getLoanAmount() { return this.finance.getLoanAmount(); }
	
	public double getInterestRate() { return this.finance.getInterestRate(); }
	
	public int getLoanDuration() { return this.finance.getLoanDuration(); }
	
	public double getPaymentAmount() { return this.finance.getPaymentAmount(); }
	
	public String getLoanPaymentFrequency() { return this.finance.getLoanPaymentFrequency(); }
	
}
