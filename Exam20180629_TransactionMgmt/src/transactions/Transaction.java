package transactions;

public class Transaction {
	private String transactionId;
	private Request Request;
	private Offer Offer;
	private Carrier Carrier;
	
	private int score=0;
	
	
	public Transaction(String transactionId,Request request, Offer offer,
			Carrier carrier) {
		
		this.transactionId = transactionId;
		Request = request;
		Offer = offer;
		Carrier = carrier;
	}
	
	
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public Request getRequest() {
		return Request;
	}
	public void setRequest(Request request) {
		Request = request;
	}
	public Offer getOffer() {
		return Offer;
	}
	public void setOffer(Offer offer) {
		Offer = offer;
	}
	public Carrier getCarrier() {
		return Carrier;
	}
	public void setCarrier(Carrier carrier) {
		Carrier = carrier;
	}


	public int getScore() {
		return score;
	}


	public void setScore(int score) {
		this.score = score;
	}
	
	public Region getDeliveryRegion() {
		return this.Request.getRegion();
	}
	
	public String getProductId() {
		return this.Request.getProductId();
	}
}
