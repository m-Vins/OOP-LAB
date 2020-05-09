package diet;

public class RawMaterial implements NutritionalElement{
	private String Name;
	private double Calories;
	private double Proteins;
	private double Carbs;
	private double Fat;
	
	public RawMaterial(String name,
							  double calories,
							  double proteins,
							  double carbs,
							  double fat) {
		this.Name=name;
		this.Calories=calories;
		this.Proteins=proteins;
		this.Carbs=carbs;
		this.Fat=fat;
	}
	
	@Override
	public String getName() {
		return this.Name;
	}

	@Override
	public double getCalories() {
		return this.Calories;
	}

	@Override
	public double getProteins() {
		return this.Proteins;
	}

	@Override
	public double getCarbs() {
		return this.Carbs;
	}

	@Override
	public double getFat() {
		return this.Fat;
	}

	@Override
	public boolean per100g() {
		return true;
	}
	

}
