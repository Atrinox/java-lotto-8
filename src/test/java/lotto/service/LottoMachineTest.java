package lotto.service;

import lotto.Lotto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LottoMachineTest {

    @DisplayName("요청한 장수만큼 6개 번호의 로또를 발행한다(각 번호는 중복 없이 1~45)")
    @Test
    void issue_many_lottos_with_injected_picker() {
        // given: 두 장을 미리 정의된 숫자로 발행
        FakePicker picker = new FakePicker(
                List.of(8, 21, 23, 41, 42, 43),
                List.of(3, 5, 11, 16, 32, 38)
        );

        // when
        List<Lotto> lottos = LottoMachine.issue(2, picker);

        // then
        assertThat(lottos).hasSize(2);
        assertThat(lottos.get(0).countMatchWith(List.of(8, 21, 23, 41, 42, 43))).isEqualTo(6);
        assertThat(lottos.get(1).countMatchWith(List.of(3, 5, 11, 16, 32, 38))).isEqualTo(6);
    }

    static class FakePicker implements NumberPicker {
        private final Deque<List<Integer>> queue = new ArrayDeque<>();

        @SafeVarargs
        FakePicker(List<Integer>... picks) {
            for (List<Integer> p : picks) queue.addLast(p);
        }

        @Override
        public List<Integer> pickSixUnique() {
            return queue.removeFirst();
        }
    }
}
