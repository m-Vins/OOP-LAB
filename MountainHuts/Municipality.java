package mountainhuts;

/**
 * Represents a municipality
 *
 */
public class Municipality {
	private String name;
	private String Province;
	private int altitude;
	
	
	public Municipality(String name,String province,int alt) {
		this.name=name;
		this.Province=province;
		this.altitude=alt;
	}
	/**
	 * Name of the municipality.
	 * 
	 * Within a region the name of a municipality is unique
	 * 
	 * @return name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Province of the municipality
	 * 
	 * @return province
	 */
	public String getProvince() {
		return this.Province;
	}

	/**
	 * Altitude of the municipality
	 * 
	 * @return altitude
	 */
	public Integer getAltitude() {
		return this.altitude;
	}

}
