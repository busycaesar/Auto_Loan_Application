package Controller;

import Model.*;

public class VehicleLoanController {

	private Vehicle vehicle;
	private Finance finance;
	
	public VehicleLoanController(String _type, String _age, double _price, double _downPayment, double _interest, double _paymentAmount, int _durationInYears, String _frequency) {
		
		this.vehicle = new Vehicle(_type, _age, _price);
		this.finance = new Finance(this.vehicle, _downPayment, _interest, _paymentAmount, _durationInYears, _frequency);
		
	}
	
}
