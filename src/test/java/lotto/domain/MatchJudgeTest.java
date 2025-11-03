package lotto.domain;

import lotto.Lotto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MatchJudgeTest {

    private final WinningNumbers winning = new WinningNumbers(
            List.of(1, 2, 3, 4, 5, 6), 7
    );

    @DisplayName("6개 일치면 1등")
    @Test
    void sixMatch_isFirst() {
        Lotto ticket = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        assertThat(MatchJudge.judge(ticket, winning)).isEqualTo(Rank.FIRST);
    }

    @DisplayName("5개 + 보너스 일치면 2등")
    @Test
    void fivePlusBonus_isSecond() {
        Lotto ticket = new Lotto(List.of(1, 2, 3, 4, 5, 7)); // 5개 + 보너스(7)
        assertThat(MatchJudge.judge(ticket, winning)).isEqualTo(Rank.SECOND);
    }

    @DisplayName("5개만 일치면 3등")
    @Test
    void fiveOnly_isThird() {
        Lotto ticket = new Lotto(List.of(1, 2, 3, 4, 5, 45));
        assertThat(MatchJudge.judge(ticket, winning)).isEqualTo(Rank.THIRD);
    }

    @DisplayName("4개 일치면 4등")
    @Test
    void four_isFourth() {
        Lotto ticket = new Lotto(List.of(1, 2, 3, 4, 45, 44));
        assertThat(MatchJudge.judge(ticket, winning)).isEqualTo(Rank.FOURTH);
    }

    @DisplayName("3개 일치면 5등")
    @Test
    void three_isFifth() {
        Lotto ticket = new Lotto(List.of(1, 2, 3, 45, 44, 43));
        assertThat(MatchJudge.judge(ticket, winning)).isEqualTo(Rank.FIFTH);
    }

    @DisplayName("그 외는 낙첨(null)")
    @Test
    void otherwise_none() {
        Lotto ticket = new Lotto(List.of(1, 2, 45, 44, 43, 42)); // 2개 일치
        assertThat(MatchJudge.judge(ticket, winning)).isNull();
    }
}
