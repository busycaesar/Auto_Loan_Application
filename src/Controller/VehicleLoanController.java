package Controller;

import Model.*;

public class VehicleLoanController {

	private Vehicle vehicle;
	private Finance finance;
	
	public VehicleLoanController(String _type, String _age, double _price, double _downPayment, double _interest, int _durationInMonths, String _frequency) {

		double _paymentAmount = this.calculatePaymentAmount();
		this.vehicle = new Vehicle(_type, _age, _price);
		this.finance = new Finance(this.vehicle, _price - _downPayment, _interest, _paymentAmount, _durationInMonths, _frequency);
		
	}
	
	private double calculatePaymentAmount() {
		// Calculate the loan amount according to 
		return 0.0;
	}
	
}
