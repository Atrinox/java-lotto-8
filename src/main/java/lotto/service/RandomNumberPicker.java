package lotto.service;

import camp.nextstep.edu.missionutils.Randoms;

import java.util.List;

public final class RandomNumberPicker implements NumberPicker {

    @Override
    public List<Integer> pickSixUnique() {
        return Randoms.pickUniqueNumbersInRange(1, 45, 6);
    }
}
