package Model;

public class Finance {

	private Vehicle vehicle;
	private double  loanAmount,
				    interestRate,
				    paymentAmount;    // Amount to be paid by the customer at the stored interval(i.e. frequency)
	private int		durationInMonths;
	private String  frequency;        // Frequency of the loan: Monthly, Weekly, Bi-weekly.
	
	public Finance(Vehicle _vehicle, double _loanAmount, double _interestRate, double _paymentAmount, int _durationInMonths, String _frequency) {
		
		this.vehicle = _vehicle;
		this.loanAmount = _loanAmount;
		this.interestRate = _interestRate;
		this.paymentAmount = _paymentAmount; 
		this.durationInMonths =_durationInMonths;
		this.frequency =_frequency;
		
	}
	
	public double getLoanAmount() 						  { return this.loanAmount;	}
	
	public double getInterestRate() 					  { return this.interestRate; }
	
	public void   setInterestRate(double _roi)			  { this.interestRate = _roi; }
	
	public int 	  getLoanDuration() 					  { return this.durationInMonths; }
	
	public void	  setLoanDuration(int _durationInMonths)  { this.durationInMonths = _durationInMonths; }
	
	public double getPaymentAmount() 					  { return this.paymentAmount; }
	
	public void   setPaymentAmount(double _paymentAmount) { this.paymentAmount = _paymentAmount; }
	
	public String getLoanPaymentFrequency() 		 	  { return this.frequency; }
	
	public String getVehicleAge() 						  { return this.vehicle.getAge(); }
	
	public String getVehicleType() 					  	  { return this.vehicle.getType(); }
	
	public double getVehiclePrice()						  { return this.vehicle.getPrice(); }
	
}