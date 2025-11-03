package lotto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        Set<Integer> uniq = new HashSet<>(numbers);
        if (uniq.size() != numbers.size()) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 중복될 수 없습니다.");
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

    public List<Integer> getNumbers() {
        return List.copyOf(numbers);
    }
}
