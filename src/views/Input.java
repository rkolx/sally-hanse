package views;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.util.stream.Collectors.toList;
import static views.InputVerification.*;
import static views.Output.println;
import static views.Output.prints;

public class Input {
	private static final Scanner scanner = new Scanner(System.in);
	public static final int PRICE_OF_ONE_LOTTO = 1000;
	public static final String OUTPUT_ASK_PURCHASE_AMOUNT = "구입금액을 입력해 주세요.";
	public static final String OUTPUT_NUMBER_OF_PURCHASED = "개를 구매했습니다.";
	public static final String OUTPUT_ASK_WINNING_NUMBER = "당첨 번호를 입력해 주세요.";
	public static final String OUTPUT_ASK_BONUSBALL = "보너스 볼을 입력해주세요.";
	public static final String OUTPUT_ASK_OF_PURCHASING_MANUAL_LOTTO = "수동으로 구매할 로또 수를 입력해 주세요.";
	public static final String OUTPUT_ASK_MANUAL_NUMBERS = "수동으로 구매할 번호를 입력해 주세요.";

	public Input() {
	}

	public static int getPurchaseAmount() {
		println.accept(OUTPUT_ASK_PURCHASE_AMOUNT);
		String purchaseAmount = nextLine();
		isValidPurchaseAmount(purchaseAmount);
		return toInt(purchaseAmount);
	}

	public static int getManualPurchaseAmount(int numberOfTicket){
		println.accept(OUTPUT_ASK_OF_PURCHASING_MANUAL_LOTTO);
		String manualPurchaseAmount = nextLine();
		isValidManualPurchaseAmount(manualPurchaseAmount, numberOfTicket);
		return toInt(manualPurchaseAmount);
	}

	public static List<List<Integer>> inputManualNumbers(int numberOfTicket){
		println.accept(OUTPUT_ASK_MANUAL_NUMBERS);
		return getManualLottoNumbers(numberOfTicket);
	}

	private static List<List<Integer>> getManualLottoNumbers(int numberOfTicket) {
		List<List<Integer>> manualLottoNumbers = new ArrayList<>();
		for (int i = 0; i < numberOfTicket; i++) {
			String pickManualSixNumber = nextLine();
			manualLottoNumbers.add(toInteger(pickManualSixNumber));
		}
		return manualLottoNumbers;
	}

	public static int getBonusNumber(List<Integer> inputValueOfWinningNumbers){
		println.accept(OUTPUT_ASK_BONUSBALL);
		String bonusNumber = nextLine();
		isValidBonusNumber(bonusNumber, inputValueOfWinningNumbers);
		return toInt(bonusNumber);
	}

	public static List<Integer> inputWinningNumbers(){
		println.accept(OUTPUT_ASK_WINNING_NUMBER);
		String textNumbers = nextLine();
		isValidWinningNumbers(textNumbers);
		List<Integer> winningNumbers = toInteger(textNumbers);
		return winningNumbers;
	}

	private static List<Integer> toInteger(String textNumbers) {
		return Arrays.stream(textNumbers.split(","))
				.map(Integer::parseInt)
				.collect(toList());
	}

	public static PurchasedLotto purchaseLotto() {
		int purchaseAmount = getPurchaseAmount();
		int numberOfTicket = getTicketAccount(purchaseAmount);
		int manualPurchaseAmount = getManualPurchaseAmount(numberOfTicket); // TODO
		return new PurchasedLotto(purchaseAmount, numberOfTicket, manualPurchaseAmount);
	}

	public static int getTicketAccount(int purchaseAmount) {
		int ticketAccount = purchaseAmount / PRICE_OF_ONE_LOTTO;
		println.accept(informPurchasing(ticketAccount));
		return ticketAccount;
	}

	private static String informPurchasing(int ticketAccount) {
		return ticketAccount + OUTPUT_NUMBER_OF_PURCHASED;
	}

	public static String nextLine() {
		return scanner.nextLine();
	}

	public static int nextInt() {
		return Integer.parseInt(nextLine());
	}

	public static void scanClose() {
		scanner.close();
	}
}
