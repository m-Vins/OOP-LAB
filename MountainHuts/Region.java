package mountainhuts;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;




/**
 * Class {@code Region} represents the main facade
 * class for the mountains hut system.
 * 
 * It allows defining and retrieving information about
 * municipalities and mountain huts.
 *
 */
public class Region {
	private String name;
	private List<range> Ranges=new ArrayList<range>();
	private Map<String,Municipality> Municipalities=new HashMap<String,Municipality>();
	private Map<String,MountainHut> MountainHuts= new HashMap<String,MountainHut>();
	
	private class range{
		private	int minValue;
		private int maxValue;
		
		
		public range(String val) {
			String[] subVal= val.split("-");
			this.minValue=Integer.parseInt(subVal[0]);
			this.maxValue=Integer.parseInt(subVal[1]);
		}
		
		public boolean check(int altitude) {
			if(altitude<=this.maxValue&&altitude>=this.minValue)
				return true;
			return false;
		}
		
		
		@Override
		public String toString() {
			return this.minValue + "-" + this.maxValue;
		}
	}
	/**
	 * Create a region with the given name.
	 * 
	 * @param name
	 *            the name of the region
	 */
	public Region(String name) {
		this.name=name;
	}

	/**
	 * Return the name of the region.
	 * 
	 * @return the name of the region
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Create the ranges given their textual representation in the format
	 * "[minValue]-[maxValue]".
	 * 
	 * @param ranges
	 *            an array of textual ranges
	 */
	public void setAltitudeRanges(String... ranges) {
			Ranges=Arrays.stream(ranges).map(a->new range(a)).collect(Collectors.toList());
	}

	/**
	 * Return the textual representation in the format "[minValue]-[maxValue]" of
	 * the range including the given altitude or return the default range "0-INF".
	 * 
	 * @param altitude
	 *            the geographical altitude
	 * @return a string representing the range
	 */
	public String getAltitudeRange(Integer altitude) {
		try{
			return Ranges.stream().filter(a->a.check(altitude))
					.findFirst().orElseThrow(NoSuchElementException::new)
					.toString();
		}catch(NoSuchElementException error) {
			return "0-INF";
		}
	}

	/**
	 * Create a new municipality if it is not already available or find it.
	 * Duplicates must be detected by comparing the municipality names.
	 * 
	 * @param nam;
	 *            the municipality name
	 * @param province
	 *            the municipality province
	 * @param altitude
	 *            the municipality altitude
	 * @return the municipality
	 */
	public Municipality createOrGetMunicipality(String name, String province, Integer altitude) {
		Municipalities.putIfAbsent(name, new Municipality(name,province,altitude));
		return Municipalities.get(name);
	}

	/**
	 * Return all the municipalities available.
	 * 
	 * @return a collection of municipalities
	 */
	public Collection<Municipality> getMunicipalities() {
		return Municipalities.values();
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 *
	 * @param name
	 *            the mountain hut name
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return the mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, String category, Integer bedsNumber,
			Municipality municipality) {
		
		MountainHuts.putIfAbsent(name, new MountainHut(name,Optional.empty(),
				category,bedsNumber,municipality));
		return MountainHuts.get(name);
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 * 
	 * @param name
	 *            the mountain hut name
	 * @param altitude
	 *            the mountain hut altitude
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return a mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, Integer altitude, String category, Integer bedsNumber,
			Municipality municipality) {
		MountainHuts.putIfAbsent(name, new MountainHut(name,Optional.ofNullable(altitude),
				category,bedsNumber,municipality));
		return MountainHuts.get(name);
	}

	/**
	 * Return all the mountain huts available.
	 * 
	 * @return a collection of mountain huts
	 */
	public Collection<MountainHut> getMountainHuts() {
		return MountainHuts.values();
	}

	/**
	 * Factory methods that creates a new region by loadomg its data from a file.
	 * 
	 * The file must be a CSV file and it must contain the following fields:
	 * <ul>
	 * <li>{@code "Province"},
	 * <li>{@code "Municipality"},
	 * <li>{@code "MunicipalityAltitude"},
	 * <li>{@code "Name"},
	 * <li>{@code "Altitude"},
	 * <li>{@code "Category"},
	 * <li>{@code "BedsNumber"}
	 * </ul>
	 * 
	 * The fields are separated by a semicolon (';'). The field {@code "Altitude"}
	 * may be empty.
	 * 
	 * @param name
	 *            the name of the region
	 * @param file
	 *            the path of the file
	 */
	public static Region fromFile(String name, String file) {
		Region ret= new Region(name);
		List<String> data=Region.readData(file);
		

		data.stream().skip(1).forEach(a->{
			String[] subData=a.split(";");
			Municipality m=ret.createOrGetMunicipality(subData[1], subData[0], Integer.parseInt(subData[2]));
			if(subData[4].equals("")) {
				ret.createOrGetMountainHut(subData[3], subData[5], Integer.parseInt(subData[6]), m);
			}else {
				ret.createOrGetMountainHut(subData[3], Integer.parseInt(subData[4]),
						subData[5], Integer.parseInt(subData[6]), m);
			}
			
		});
		
		return ret;
	}

	/**
	 * Internal class that can be used to read the lines of
	 * a text file into a list of strings.
	 * 
	 * When reading a CSV file remember that the first line
	 * contains the headers, while the real data is contained
	 * in the following lines.
	 * 
	 * @param file the file name
	 * @return a list containing the lines of the file
	 */
	@SuppressWarnings("unused")
	private static List<String> readData(String file) {
		try (BufferedReader in = new BufferedReader(new FileReader(file))) {
			return in.lines().collect(toList());
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Count the number of municipalities with at least a mountain hut per each
	 * province.
	 * 
	 * @return a map with the province as key and the number of municipalities as
	 *         value
	 */
	public Map<String, Long> countMunicipalitiesPerProvince() {

		return Municipalities.values().stream().
				collect(Collectors.groupingBy(a->a.getProvince(),Collectors.counting()));
	}

	

	/**
	 * Count the number of mountain huts per each municipality within each province.
	 * 
	 * @return a map with the province as key and, as value, a map with the
	 *         municipality as key and the number of mountain huts as value
	 */
	public Map<String, Map<String, Long>> countMountainHutsPerMunicipalityPerProvince() {

		
		return MountainHuts.values().stream().
				collect(Collectors.groupingBy(a->(String)a.
						getMunicipality().getProvince(),Collectors.
						groupingBy(b->(String)b.getMunicipality().getName(),Collectors.counting())));
	}

	/**
	 * Count the number of mountain huts per altitude range. If the altitude of the
	 * mountain hut is not available, use the altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the number of mountain huts
	 *         as value
	 */
	public Map<String, Long> countMountainHutsPerAltitudeRange() {

		
		return MountainHuts.values().stream().
				collect(Collectors.
				groupingBy(a->getAltitudeRange(a.getAltitude().orElse(a.getMunicipality().getAltitude())),
						Collectors.counting()));
	}

	/**
	 * Compute the total number of beds available in the mountain huts per each
	 * province.
	 * 
	 * @return a map with the province as key and the total number of beds as value
	 */
	public Map<String, Integer> totalBedsNumberPerProvince() {
		return MountainHuts.values().stream().
				collect(Collectors.groupingBy( a -> a.getMunicipality().getProvince(),
						Collectors.summingInt(a->a.getBedsNumber())));
	}

	/**
	 * Compute the maximum number of beds available in a single mountain hut per
	 * altitude range. If the altitude of the mountain hut is not available, use the
	 * altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the maximum number of beds
	 *         as value
	 */
	public Map<String, Optional<Integer>> maximumBedsNumberPerAltitudeRange() {
		return MountainHuts.values().stream().
				collect(Collectors.
						groupingBy(a->getAltitudeRange(a.getAltitude().
								orElse(a.getMunicipality().getAltitude())),
						Collectors.mapping(b->b.getBedsNumber(),
								Collectors.maxBy(Integer::compareTo))));
				
	}

	/**
	 * Compute the municipality names per number of mountain huts in a municipality.
	 * The lists of municipality names must be in alphabetical order.
	 * 
	 * @return a map with the number of mountain huts in a municipality as key and a
	 *         list of municipality names as value
	 */
	public Map<Long, List<String>> municipalityNamesPerCountOfMountainHuts() {
		
				/*Map<String,Long> temp=MountainHuts.values().stream().collect(Collectors.
						groupingBy(a->a.getMunicipality().getName(),Collectors.counting()));
				//da sistemare
				return MountainHuts.values().stream().map(a->a.getMunicipality().getName()).
						collect(Collectors.groupingBy(a->temp.get(a),
								()->new ArrayList<>(),Collectors.toList() ));*/
		
		
		return MountainHuts.values().stream().map(x->x.getMunicipality().getName()).collect(Collectors.groupingBy(x->x,
				TreeMap::new,Collectors.counting())).entrySet().stream().collect(
						Collectors.groupingBy(Map.Entry::getValue,
						Collectors.mapping(Map.Entry::getKey,Collectors.toList())));
	}
}

