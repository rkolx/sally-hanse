package views;

public class PurchasedLotto {
	private final int purchaseAmount;
	private final int numberOfTicket;
	private final int manualPurchaseAmount;

	public PurchasedLotto(int purchaseAmount, int numberOfTicket, int manualPurchaseAmount) {
		this.purchaseAmount = purchaseAmount;
		this.numberOfTicket = numberOfTicket;
		this.manualPurchaseAmount = manualPurchaseAmount;
	}

	public int getPurchaseAmount() {
		return purchaseAmount;
	}

	public int getNumberOfTicket() {
		return numberOfTicket;
	}
}
