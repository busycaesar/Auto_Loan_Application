package Model;

public class Finance {

	private Vehicle item; 		     // Item for the loan. In our case the item is Vehicle type.
	private double  loanAmount,
				    interestRate,
				    paymentAmount;   // Amount to be paid by the customer at the stored interval(i.e. frequency)
	private int		durationInMonths;
	private String  frequency;       // Frequency of the loan: Monthly, Weekly, Bi-weekly.
	
	public Finance(Vehicle _item, double _loanAmount, double _interestRate, double _paymentAmount, int _durationInMonths, String _frequency) {
		
		this.item = _item;
		this.loanAmount = _loanAmount;
		this.interestRate = _interestRate;
		this.paymentAmount = _paymentAmount; 
		this.durationInMonths =_durationInMonths;
		this.frequency =_frequency;
		
	}
	
	public double getLoanAmount() { return this.loanAmount;	}
	
	public double getInterestRate() { return this.interestRate; }
	
	public int getLoanDuration() { return this.durationInMonths; }
	
	public double getPaymentAmount() { return this.paymentAmount; }
	
	public String getLoanPaymentFrequency() { return this.frequency; }
	
}
