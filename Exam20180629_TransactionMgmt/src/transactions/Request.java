package transactions;

public class Request {
	private String requestId;
	private String PlaceName;
	private Region Region;
	private String productId;
	
	
	public Request(String requestId, String placeName, Region region, String productId) {
		this.requestId = requestId;
		PlaceName = placeName;
		Region = region;
		this.productId = productId;
	}
	
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getPlaceId() {
		return PlaceName;
	}
	public void setPlaceId(String placeId) {
		PlaceName = placeId;
	}
	public Region getRegion() {
		return Region;
	}
	public void setRegion(Region region) {
		Region = region;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
}
