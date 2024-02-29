package Model;

public class Vehicle {
	
	private String type,  // Type of the vehicle: Car, Truck or Family Van
				   age;   // Age of the vehicle: New or Used
	private double price;
	
	// Constructor: Create a new car object.
	public Vehicle(String _type, String _age, double _price) {
		
		this.type = _type;
		this.age = _age;
		this.price = _price;
		
	}
	
	public double getPrice() { return this.price; }
	
	public String getAge()	 { return this.age; }
	
	public String getType()  { return this.type; }
	
}