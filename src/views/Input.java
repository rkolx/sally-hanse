package views;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static java.util.stream.Collectors.toList;
import static views.InputVerification.*;
import static views.Output.println;

public class Input {
	private static final Scanner scanner = new Scanner(System.in);
	public static final int PRICE_OF_ONE_LOTTO = 1000;
	public static final String OUTPUT_ASK_PURCHASE_AMOUNT = "구입금액을 입력해 주세요.";
	public static final String OUTPUT_NUMBER_OF_PURCHASED = "개를 구매했습니다.";
	public static final String OUTPUT_ASK_WINNING_NUMBER = "당첨 번호를 입력해 주세요.";
	public static final String OUTPUT_ASK_BONUSBALL = "보너스 볼을 입력해주세요.";
	public static final String OUTPUT_ASK_OF_PURCHASING_MANUAL_LOTTO = "수동으로 구매할 로또 수를 입력해 주세요.";
	public static final String OUTPUT_ASK_OF_MANUAL_LOTTO_NUMBERS = "수동으로 구매할 번호를 입력해 주세요.";

	public Input() {
	}

	public static PurchasedLotto purchaseLotto() {
		int purchaseAmount = getPurchaseAmount();
		int numberOfTicket = getNumberOfTicket(purchaseAmount);
		int numberOfPurchasingManual = getManualPurchaseAmount(numberOfTicket); 

		if (numberOfPurchasingManual >= 0) {
			List<List<Integer>> manualNumbers = getManualNumbers(numberOfPurchasingManual);
			return new PurchasedLotto(purchaseAmount, numberOfTicket, Optional.of(manualNumbers));
		}
		return new PurchasedLotto(purchaseAmount, numberOfTicket, Optional.empty());  // TODO  Optional 사용방법 확인
	}

	public static int getPurchaseAmount() {
		try {
			println.accept(OUTPUT_ASK_PURCHASE_AMOUNT);
			String purchaseAmount = nextLine();
			isValidPurchaseAmount(purchaseAmount);
			return toInt(purchaseAmount);
		} catch (IllegalArgumentException exception) {
			println.accept(exception.getMessage());
			return getPurchaseAmount();
		}
	}

	public static int getNumberOfTicket(int purchaseAmount) {
		int numberOfTicket = purchaseAmount / PRICE_OF_ONE_LOTTO;
		println.accept(informPurchasing(numberOfTicket));
		return numberOfTicket;
	}

	public static int getManualPurchaseAmount(int numberOfTicket){
		try	{
			println.accept(OUTPUT_ASK_OF_PURCHASING_MANUAL_LOTTO);
			String manualPurchaseAmount = nextLine();
			isValidNumberOfPurchasingManuals(manualPurchaseAmount, numberOfTicket);
			return toInt(manualPurchaseAmount);
		} catch (IllegalArgumentException exception){
			println.accept(exception.getMessage());
			return getManualPurchaseAmount(numberOfTicket);
		}
	}

	public static List<List<Integer>> getManualNumbers(int numberOfManualTicket){
		try {
			println.accept(OUTPUT_ASK_OF_MANUAL_LOTTO_NUMBERS);
			List<String> manualNumbers = inputManualNumbers(numberOfManualTicket);
			isValidManualNumbers(manualNumbers);
			List<List<Integer>> changedManualNumbers = getManualLottoNumbers(manualNumbers);
			return changedManualNumbers;
		} catch (IllegalArgumentException exception){
			println.accept(exception.getMessage());
			return getManualNumbers(numberOfManualTicket);
		}
	}

	private static List<String> inputManualNumbers(int numberOfManualTicket) {
		List<String> inputValues = new ArrayList<>();
		for (int i = 0; i < numberOfManualTicket; i++) {
			String inputManualSixNumber = nextLine();
			inputValues.add(inputManualSixNumber);
		}
		return inputValues;
	}

	private static List<List<Integer>> getManualLottoNumbers(List<String> manualNumbers) {
		List<List<Integer>> manualLottoNumbers = new ArrayList<>();
		for (int i = 0; i < manualNumbers.size(); i++) {
			String textOfManualNumber = manualNumbers.get(i);
			List<Integer> manualSixNumbers = splitAndToInt(textOfManualNumber);
			manualLottoNumbers.add(manualSixNumbers);
		}
		return manualLottoNumbers;
	}

	public static int getBonusNumber(List<Integer> inputValueOfWinningNumbers){
		try {
			println.accept(OUTPUT_ASK_BONUSBALL);
			String bonusNumber = nextLine();
			isValidBonusNumber(bonusNumber, inputValueOfWinningNumbers);
			return toInt(bonusNumber);
		} catch (IllegalArgumentException exception){
			println.accept(exception.getMessage());
			return getBonusNumber(inputValueOfWinningNumbers);
		}
	}

	public static List<Integer> inputWinningNumbers(){
		try {
			println.accept(OUTPUT_ASK_WINNING_NUMBER);
			String textNumbers = nextLine();
			isValidWinningNumbers(textNumbers);
			List<Integer> winningNumbers = splitAndToInt(textNumbers);
			return winningNumbers;
		} catch (IllegalArgumentException exception){
			println.accept(exception.getMessage());
			return inputWinningNumbers();
		}
	}

	private static List<Integer> splitAndToInt(String textNumbers) {
		return Arrays.stream(textNumbers.split(SYMBOL_SEPARATOR))
			.map(Integer::parseInt)
			.collect(toList());
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
