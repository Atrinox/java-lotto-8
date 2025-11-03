package lotto.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class WinningNumbers {
    private static final int SIZE = 6;
    private static final int MIN = 1;
    private static final int MAX = 45;

    private final Set<Integer> numbers;
    private final int bonus;

    public WinningNumbers(List<Integer> numbers, int bonus) {
        validateSize(numbers);
        validateRange(numbers);
        validateDuplicate(numbers);
        validateBonusRange(bonus);
        validateBonusNotDuplicated(numbers, bonus);
        this.numbers = new HashSet<>(numbers);
        this.bonus = bonus;
    }

    private void validateSize(List<Integer> nums) {
        if (nums.size() == SIZE) return;
        throw iae("당첨 번호는 6개여야 합니다.");
    }

    private void validateRange(List<Integer> nums) {
        boolean invalid = nums.stream().anyMatch(n -> n < MIN || n > MAX);
        if (!invalid) return;
        throw iae("로또 번호는 1부터 45 사이의 숫자여야 합니다.");
    }

    private void validateDuplicate(List<Integer> nums) {
        if (nums.stream().distinct().count() == nums.size()) return;
        throw iae("당첨 번호는 중복될 수 없습니다.");
    }

    private void validateBonusRange(int b) {
        if (MIN <= b && b <= MAX) return;
        throw iae("로또 번호는 1부터 45 사이의 숫자여야 합니다.");
    }

    private void validateBonusNotDuplicated(List<Integer> nums, int b) {
        if (!nums.contains(b)) return;
        throw iae("보너스 번호는 당첨 번호와 중복될 수 없습니다.");
    }

    private IllegalArgumentException iae(String msg) {
        return new IllegalArgumentException("[ERROR] " + msg);
    }

    public boolean contains(int n) {
        return numbers.contains(n);
    }

    public int bonus() {
        return bonus;
    }
}
