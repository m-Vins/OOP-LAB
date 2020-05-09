package diet;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;


/**
 * Represents a recipe of the diet.
 * 
 * A recipe consists of a a set of ingredients that are given amounts of raw materials.
 * The overall nutritional values of a recipe can be computed
 * on the basis of the ingredients' values and are expressed per 100g
 * 
 *
 */
public class Recipe implements NutritionalElement {
    private String Name;
    SortedMap<String,RawMaterial> rawMaterials;
	List<String> Ingredients= new LinkedList<String>();
	HashMap<String, Double> QuantityMap= new HashMap<String,Double>();
	
	
	
	
	public Recipe(String name, SortedMap<String,RawMaterial> rawMaterials) {
		this.Name=name;
		this.rawMaterials=rawMaterials;
	}
	/**
	 * Adds a given quantity of an ingredient to the recipe.
	 * The ingredient is a raw material.
	 * 
	 * @param material the name of the raw material to be used as ingredient
	 * @param quantity the amount in grams of the raw material to be used
	 * @return the same Recipe object, it allows method chaining.
	 */
	public Recipe addIngredient(String material, double quantity) {
		Ingredients.add(material);
		QuantityMap.put(material,quantity);
		return this;
	}

	@Override
	public String getName() {
		return this.Name;
	}

	@Override
	public double getCalories() {
		return Ingredients.stream().mapToDouble((value)->QuantityMap.get(value)*rawMaterials.get(value).getCalories())
				.sum()/getSumg();
	}

	@Override
	public double getProteins() {
		return Ingredients.stream().mapToDouble((value)->QuantityMap.get(value)*rawMaterials.get(value).getProteins())
				.sum()/getSumg();
	}

	@Override
	public double getCarbs() {
		return Ingredients.stream().mapToDouble((value)->QuantityMap.get(value)*rawMaterials.get(value).getCarbs())
				.sum()/getSumg();
	}

	@Override
	public double getFat() {
		return Ingredients.stream().mapToDouble((value)->QuantityMap.get(value)*rawMaterials.get(value).getFat())
				.sum()/getSumg();
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Recipe} class it must always return {@code true}:
	 * a recipe expresses nutritional values per 100g
	 * 
	 * @return boolean indicator
	 */
	@Override
	public boolean per100g() {
		return true;
	}
	
	/**
	 * Return the sum of all ingredients
	 * @return double
	 */
	private double getSumg() {
		return QuantityMap.values().stream().mapToDouble(a->a).sum();
	}
	
	
	/**
	 * Returns the ingredients composing the recipe.
	 * 
	 * A string that contains all the ingredients, one per per line, 
	 * using the following format:
	 * {@code "Material : ###.#"} where <i>Material</i> is the name of the 
	 * raw material and <i>###.#</i> is the relative quantity. 
	 * 
	 * Lines are all terminated with character {@code '\n'} and the ingredients 
	 * must appear in the same order they have been added to the recipe.
	 */
	@Override
	public String toString() {
		return Ingredients.stream().
		reduce("",(accumulator,value)->(accumulator+ value +" : "+QuantityMap.get(value)+"\n"));
	}
}
