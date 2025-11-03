package lotto.domain;

public final class PurchaseAmount {
    private static final int UNIT = 1000;

    private final int amount;

    private PurchaseAmount(int amount) {
        validatePositive(amount);
        validateUnit(amount);
        this.amount = amount;
    }

    public static PurchaseAmount of(String raw) {
        if (raw == null) throw iae("구입 금액은 숫자여야 합니다.");
        String s = raw.trim();
        try {
            int v = Integer.parseInt(s);
            return new PurchaseAmount(v);
        } catch (NumberFormatException e) {
            throw iae("구입 금액은 숫자여야 합니다.");
        }
    }

    public int value() {
        return amount;
    }

    public int tickets() {
        return amount / UNIT;
    }

    private static void validatePositive(int v) {
        if (v <= 0) throw iae("구입 금액은 1,000원 이상의 양의 정수여야 합니다.");
    }

    private static void validateUnit(int v) {
        if (v % UNIT != 0) throw iae("구입 금액은 1,000원 단위여야 합니다.");
    }

    private static IllegalArgumentException iae(String msg) {
        return new IllegalArgumentException("[ERROR] " + msg);
    }
}
