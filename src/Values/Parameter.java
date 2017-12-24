package Values;

public class Parameter {

	private int generation;
	private int particleAmount;
	private double inertiaStart;
	private double inertiaEnd;
	private double cramOne;
	private double cramTwo;
	
	private double sizeX;
	private double sizeY;
	private double addNumber;
	private static Parameter instance = null;
	private Parameter(){}
	
	public static synchronized Parameter getInstance() {
		if (instance == null) {
			instance = new Parameter();
		}
		return instance;
	}
	
	public void setGeneration(int generation) {
		this.generation = generation;
	}
	
	public void setParticleAmount(int particleAmount) {
		this.particleAmount = particleAmount;
	}
	
	public void setInertiaStart(double inertiaStart) {
		this.inertiaStart = inertiaStart;
	}
	
	public void setInertiaEnd(double inertiaEnd) {
		this.inertiaEnd = inertiaEnd;
	}
	
	public void setCramOne(double cramOne) {
		this.cramOne = cramOne;
	}
	
	public void setCramTwo(double cramTwo) {
		this.cramTwo = cramTwo;
	}
	
	public void setPointParameter(double sizeX, double sizeY, double addNumber) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.addNumber = addNumber;
	}
	
	public int getGeneration() {
		return this.generation;
	}
	
	public int getParticleAmount() {
		return  this.particleAmount;
	}
	
	public double getInertiaStart() {
		return this.inertiaStart;
	}
	
	public double getInertiaEnd() {
		return this.inertiaEnd;
	}
	
	public double getCramOne() {
		return this.cramOne;
	}
	
	public double getCramTwo() {
		return this.cramTwo;
	}
	
	public double getSizeX () {
		return this.sizeX;
	}
	
	public double getSizeY () {
		return this.sizeY;
	}
	
	public double getAddNumber () {
		return this.addNumber;
	}
}
