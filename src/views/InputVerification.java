package views;

import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputVerification {
    private static final int RANGE_OF_LOTTO_NUMBER = 6;
    private static final int MIN_RANGE_OF_LOTTO_NUMBER = 1;
    private static final int MAX_RANGE_OF_LOTTO_NUMBER = 45;

    private static final String SYMBOL_SEPARATOR = ",";
    private static final String REGEX_NOT_DIGIT = "[^0-9]";
    private static final String SEPARATOR_REGEX = "[\\,]+";
    private static final int MAX_LIMITED_AMOUNT = 1_000_000;
    private static final int MIN_LIMITED_AMOUNT = 1_000;
    private static final String ERROR_PURCHASE_AMOUNT_BLANK = "구입 금액을 입력해주세요.(공백은 안됩니다)";
    private static final String ERROR_BLANK = "공백은 안됩니다.";
    public static final String ERROR_APPENDED_THOUSAND_PER_SHEETS = "[1장:1000원]";
    private static final String ERROR_OF_NOT_NUMBER = "숫자로만 입력해주세요. ";
    private static final String ERROR_NOT_THOUSAND_UNIT = "장당 천원입니다.";
    private static final String ERROR_OUT_OF_RANGE = "최소 천원 이상 최대 십만원 이하로 구매 가능합니다. " + ERROR_APPENDED_THOUSAND_PER_SHEETS;

    private static final String ERROR_RANGE_OF_LOTTO = "1~45 범위내 숫자 6개를 입력하세요";
    private static final String ERROR_NOT_INCLUDED_COMMA = "쉼표를 구분자로 " + ERROR_RANGE_OF_LOTTO;

    private static final String ERROR_DUPLICATED_SIX_NUMBER = "서로 다른 6개의 숫자를 입력해 주세요";
    private static final String ERROR_SIX_NUMBER = "숫자 6개를 입력하세요";



    public static void isValidPurchaseAmount(String purchaseAmount){
        String errorMessageWhenNotNumber = ERROR_OF_NOT_NUMBER + ERROR_APPENDED_THOUSAND_PER_SHEETS;
        isBlank(purchaseAmount, ERROR_PURCHASE_AMOUNT_BLANK);
        isNumber(purchaseAmount, errorMessageWhenNotNumber);
        int integerPurchaseAmount = toInt(purchaseAmount);
        isThousandUnit(integerPurchaseAmount);
        isRange(integerPurchaseAmount);
    }

    public static void isValidWinningNumbers(String winningNumbers) {
        isBlank(winningNumbers, ERROR_BLANK);
        isValidStartOrEnd(winningNumbers);
        isRulesOfInput(winningNumbers);
        String[] numbers = getSplit(winningNumbers);
        isNumberOfSix(numbers);
        isDuplicated(numbers);
        isValidSize(numbers);
        isValidRangeOf(numbers);
    }

    private static void isValidStartOrEnd(String winningNumbers) {
        char[] winnings = winningNumbers.toCharArray();
        int size = winnings.length - 1;
        isValidTheLast(winningNumbers, size);
        fromFirstTo(winnings, size);
    }

    private static void isValidTheLast(String winningNumbers, int size) {
        if (!Character.isDigit(winningNumbers.charAt(size))) {
            throw new IllegalArgumentException(ERROR_NOT_INCLUDED_COMMA);
        }
    }

    private static void fromFirstTo(char[] winnings, int size) {
        boolean isFirstDigit = false;
        for (int i = 0; i < size; i++) {
            char winning = winnings[i];
            if (i == 0) {
                isFirstDigit = checkFirstDigit(isFirstDigit, winning);
            }
            if (!Character.isDigit(winning) && !isFirstDigit) {
                throw new IllegalArgumentException(ERROR_NOT_INCLUDED_COMMA);
            }
        }
    }

    private static boolean checkFirstDigit(boolean isFirstDigit, char winning) {
        if (Character.isDigit(winning)) {
            isFirstDigit = true;
        }
        return isFirstDigit;
    }

    private static String[] getSplit(String inputValues) {
        return inputValues.split(SYMBOL_SEPARATOR);
    }

    private static void isNumberOfSix(String[] sixNumbers) {
        Arrays.stream(sixNumbers)
            .forEach(number -> isNumber(number, ERROR_OF_NOT_NUMBER));
    }

    private static void isDuplicated(String[] numbers) {
        boolean isUnique = checkUnique(numbers);
        if (!isUnique) {
            throw new IllegalArgumentException(ERROR_DUPLICATED_SIX_NUMBER);
        }
    }

    private static boolean checkUnique(String[] names) {
        return Arrays.stream(names)
            .map(String::trim)
            .allMatch(new HashSet<>()::add);
    }

    private static void isValidSize(String[] numbers) {
        if (numbers.length != RANGE_OF_LOTTO_NUMBER) {
            throw new IllegalArgumentException(ERROR_SIX_NUMBER);
        }
    }

    private static void isValidRangeOf(String[] numbers) {
        Arrays.stream(numbers)
            .mapToInt(numberString -> toInt(numberString))
            .filter(number -> (number < MIN_RANGE_OF_LOTTO_NUMBER) || (number > MIN_RANGE_OF_LOTTO_NUMBER))
            .findAny()
            .ifPresent((outOfRangeNumber) -> {throw new IllegalArgumentException(ERROR_RANGE_OF_LOTTO);});
    }

    public static int toInt(String textNumber) {
        return Integer.parseInt(textNumber);
    }

    private static void isBlank(String inputValue, String errorMessage) {
        if (inputValue.isBlank()){
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private static void isNumber(String inputValue, String errorMessage){
        Matcher matcher = Pattern.compile(REGEX_NOT_DIGIT).matcher(inputValue);
        if (matcher.find()){
            throw new IllegalArgumentException();
        }
    }

    private static void isThousandUnit(int inputValue){
        int lessThanThousand = (inputValue % MIN_LIMITED_AMOUNT) / 100;
        if (lessThanThousand != 0){
            throw new IllegalArgumentException(ERROR_NOT_THOUSAND_UNIT);
        }
    }

    private static void isRange(int inputValue){
        if (MIN_LIMITED_AMOUNT > inputValue || MAX_LIMITED_AMOUNT < inputValue){
            throw new IllegalArgumentException(ERROR_OUT_OF_RANGE);
        }
    }

    private static void isRulesOfInput(String inputValues) {
        if (!isIncludedSeparator(inputValues)) {
            throw new IllegalArgumentException(ERROR_NOT_INCLUDED_COMMA);
        }
    }

    private static boolean isIncludedSeparator(String inputValues) {
        Pattern compile = Pattern.compile(SEPARATOR_REGEX);
        Matcher matcher = compile.matcher(inputValues);
        return matcher.find();
    }
}
