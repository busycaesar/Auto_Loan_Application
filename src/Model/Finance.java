package Model;

public class Finance {

	private Vehicle item; 		     // Item for the loan. In our case the item is Vehicle type.
	private double  downPayment,
				    interest,
				    paymentAmount;   // Amount to be paid by the customer at the stored interval(i.e. frequency)
	private int		durationInYears;
	private String  frequency;       // Frequency of the loan: Monthly, Weekly, Bi-weekly.
	
	public Finance(Vehicle _item, double _downPayment, double _interest, double _paymentAmount, int _durationInYears, String _frequency) {
		
		this.item = _item;
		this.downPayment = _downPayment;
		this.interest = _interest;
		this.paymentAmount = _paymentAmount; 
		this.durationInYears =_durationInYears;
		this.frequency =_frequency;
		
	}
	
}
