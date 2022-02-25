package views;

import java.util.List;
import java.util.Optional;

public class PurchasedLotto {
	private final int purchaseAmount;
	private final int numberOfTicket;
	private final Optional<List<List<Integer>>> manualNumbers;

	public PurchasedLotto(int purchaseAmount, int numberOfTicket, Optional<List<List<Integer>>> manualNumbers) {
		this.purchaseAmount = purchaseAmount;
		this.numberOfTicket = numberOfTicket;
		this.manualNumbers = manualNumbers;
	}

	public int getPurchaseAmount() {
		return purchaseAmount;
	}

	public int getNumberOfTicket() {
		return numberOfTicket;
	}
}
