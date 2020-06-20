package transactions;

public class Offer {
	private String OfferId;
	private String PlaceName;
	private Region Region;
	private String productId;
	
	public Offer(String offerId, String placeName, Region region, String productId) {
		OfferId = offerId;
		PlaceName = placeName;
		Region = region;
		this.productId = productId;
	}
	
	public String getOfferId() {
		return OfferId;
	}
	public void setOfferId(String offerId) {
		OfferId = offerId;
	}
	public String getPlaceName() {
		return PlaceName;
	}
	public void setPlaceName(String placeName) {
		PlaceName = placeName;
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
