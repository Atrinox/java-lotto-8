package lotto.service;

import lotto.Lotto;

import java.util.ArrayList;
import java.util.List;

public final class LottoMachine {
    private LottoMachine() { }

    public static List<Lotto> issue(int count) {
        return issue(count, new RandomNumberPicker());
    }

    public static List<Lotto> issue(int count, NumberPicker picker) {
        List<Lotto> result = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            result.add(new Lotto(picker.pickSixUnique()));
        }
        return result;
    }
}
