package lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RankTest {

    @DisplayName("6개 일치면 1등")
    @Test
    void sixMatch_isFirst() {
        assertThat(Rank.of(6, false)).isEqualTo(Rank.FIRST);
        assertThat(Rank.of(6, true)).isEqualTo(Rank.FIRST);
    }

    @DisplayName("5개 일치+보너스 일치면 2등")
    @Test
    void fiveMatch_withBonus_isSecond() {
        assertThat(Rank.of(5, true)).isEqualTo(Rank.SECOND);
    }

    @DisplayName("5개 일치만 되면 3등")
    @Test
    void fiveMatch_withoutBonus_isThird() {
        assertThat(Rank.of(5, false)).isEqualTo(Rank.THIRD);
    }

    @DisplayName("4개 일치면 4등")
    @Test
    void fourMatch_isFourth() {
        assertThat(Rank.of(4, false)).isEqualTo(Rank.FOURTH);
        assertThat(Rank.of(4, true)).isEqualTo(Rank.FOURTH);
    }

    @DisplayName("3개 일치면 5등")
    @Test
    void threeMatch_isFifth() {
        assertThat(Rank.of(3, false)).isEqualTo(Rank.FIFTH);
        assertThat(Rank.of(3, true)).isEqualTo(Rank.FIFTH);
    }

    @DisplayName("그 외는 낙첨(null)")
    @Test
    void otherwise_isNone() {
        assertThat(Rank.of(2, true)).isNull();
        assertThat(Rank.of(0, false)).isNull();
    }
}
