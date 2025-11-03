package lotto;

import java.util.List;

public class Lotto {
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    private void validate(List<Integer> numbers) {
        if (numbers.size() != 6) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 6개여야 합니다.");
        }
    }

    public boolean contains(int n) {
        return numbers.contains(n);
    }

    public int countMatchWith(List<Integer> reference) {
        int cnt = 0;
        for (int n : reference) {
            if (numbers.contains(n)) cnt++;
        }
        return cnt;
    }
}
