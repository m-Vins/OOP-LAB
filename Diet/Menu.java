package diet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Represents a complete menu.
 * 
 * It can be made up of both packaged products and servings of given recipes.
 *
 */
public class Menu implements NutritionalElement {
	private String Name;
	
	private HashMap<String, Double> RecipesNames= new HashMap<String,Double>();
	private HashMap<String, Recipe> Recipes= new HashMap<String,Recipe>();
	
	private Collection<String> ProductsNames= new ArrayList<String>();
	private HashMap<String, Product> Products= new HashMap<String,Product>();
	
	
	public Menu(String Name, HashMap<String,Recipe> Recipes, HashMap<String,Product> Products) {
		this.Name=Name;
		this.Recipes=Recipes;
		this.Products=Products;
	}
	/**
	 * Adds a given serving size of a recipe.
	 * 
	 * The recipe is a name of a recipe defined in the
	 * {@Link Food} in which this menu has been defined.
	 * 
	 * @param recipe the name of the recipe to be used as ingredient
	 * @param quantity the amount in grams of the recipe to be used
	 * @return the same Menu to allow method chaining
	 */
	public Menu addRecipe(String recipe, double quantity) {
		this.RecipesNames.put(recipe, quantity);
		return this;
	}

	/**
	 * Adds a unit of a packaged product.
	 * The product is a name of a product defined in the
	 * {@Link Food} in which this menu has been defined.
	 * 
	 * @param product the name of the product to be used as ingredient
	 * @return the same Menu to allow method chaining
	 */
	public Menu addProduct(String product) {
		this.ProductsNames.add(product);
		return this;
	}

	/**
	 * Name of the menu
	 */
	@Override
	public String getName() {
		return this.Name;
	}

	/**
	 * Total KCal in the menu
	 */
	@Override
	public double getCalories() {
		return RecipesNames.keySet().stream().
				mapToDouble((value)->(Recipes.get(value)
				.getCalories()*RecipesNames.get(value)/100))
				.sum()+ProductsNames.stream().
				mapToDouble((value)->(Products
				.get(value).getCalories())).sum();
	}

	/**
	 * Total proteins in the menu
	 */
	@Override
	public double getProteins() {
		return RecipesNames.keySet().stream().
				mapToDouble((value)->(Recipes.get(value).
				getProteins()*RecipesNames.get(value)/100))
				.sum()+ProductsNames.stream().
				mapToDouble((value)->(Products
				.get(value).getProteins())).sum();
	}

	/**
	 * Total carbs in the menu
	 */
	@Override
	public double getCarbs() {
		return RecipesNames.keySet().stream().
				mapToDouble((value)->(Recipes.get(value).
				getCarbs()*RecipesNames.get(value)/100))
				.sum()+ProductsNames.stream().
				mapToDouble((value)->(Products.
				get(value).getCarbs())).sum();
	}

	/**
	 * Total fats in the menu
	 */
	@Override
	public double getFat() {
		return RecipesNames.keySet().stream().
				mapToDouble((value)->(Recipes.get(value)
				.getFat()*RecipesNames.get(value)/100))
				.sum()+ProductsNames.stream().
				mapToDouble((value)->(Products.
				get(value).getFat())).sum();
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Menu} class it must always return {@code false}:
	 * nutritional values are provided for the whole menu.
	 * 
	 * @return boolean 	indicator
	 */
	@Override
	public boolean per100g() {
		// nutritional values are provided for the whole menu.
		return false;
	}
}
