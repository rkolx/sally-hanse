package views;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputVerification {
    private static final String REGEX_NOT_DIGIT = "[^0-9]";
    private static final int MAX_LIMITED_AMOUNT = 1_000_000;
    private static final int MIN_LIMITED_AMOUNT = 1_000;
    private static final String ERROR_BLANK = "구입 금액을 입력해주세요.(공백은 안됩니다)";
    private static final String ERROR_OF_NOT_NUMBER = "숫자로만 입력해주세요. [1장:1000원]";
    private static final String ERROR_NOT_THOUSAND_UNIT = "장당 천원입니다.";
    private static final String ERROR_OUT_OF_RANGE = "최소 천원 이상 최대 십만원 이하로 구매 가능합니다. [1장:1000원]";

    public static void isValidPurchaseAmount(String purchaseAmount){
        isBlank(purchaseAmount);
        isNumber(purchaseAmount);
        int integerPurchaseAmount = Integer.parseInt(purchaseAmount);
        isThousandUnit(integerPurchaseAmount);
        isRange(integerPurchaseAmount);
    }

    private static void isBlank(String inputValue) {
        if (inputValue.isBlank()){
            throw new IllegalArgumentException(ERROR_BLANK);
        }
    }

    private static void isNumber(String inputValue){
        Matcher matcher = Pattern.compile(REGEX_NOT_DIGIT).matcher(inputValue);
        if (matcher.find()){
            throw new IllegalArgumentException(ERROR_OF_NOT_NUMBER);
        }
    }

    private static void isThousandUnit(int inputValue){
        if ((inputValue % MIN_LIMITED_AMOUNT)/100 != 0){
            throw new IllegalArgumentException(ERROR_NOT_THOUSAND_UNIT);
        }
    }

    private static void isRange(int inputValue){
        if (inputValue < MIN_LIMITED_AMOUNT || MAX_LIMITED_AMOUNT < inputValue){
            throw new IllegalArgumentException(ERROR_OUT_OF_RANGE);
        }
    }

}
