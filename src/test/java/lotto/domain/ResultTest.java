package lotto.domain;

import lotto.Lotto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ResultTest {

    @DisplayName("여러 로또를 집계해 등수별 개수, 총 상금, 수익률을 계산한다")
    @Test
    void aggregate_counts_totalPrize_yield() {
        // given
        WinningNumbers winning = new WinningNumbers(List.of(1, 2, 3, 4, 5, 6), 7);

        List<Lotto> tickets = List.of(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),   // 1등
                new Lotto(List.of(1, 2, 3, 4, 5, 7)),   // 2등 (5+보너스)
                new Lotto(List.of(1, 2, 3, 4, 5, 45)),  // 3등 (5)
                new Lotto(List.of(1, 2, 3, 4, 44, 45)), // 4등 (4)
                new Lotto(List.of(1, 2, 3, 43, 44, 45)),// 5등 (3)
                new Lotto(List.of(1, 2, 42, 43, 44, 45))// 낙첨 (2)
        );

        // when
        Result result = Result.of(tickets, winning);

        // then: 등수별 카운트
        assertThat(result.countOf(Rank.FIRST)).isEqualTo(1);
        assertThat(result.countOf(Rank.SECOND)).isEqualTo(1);
        assertThat(result.countOf(Rank.THIRD)).isEqualTo(1);
        assertThat(result.countOf(Rank.FOURTH)).isEqualTo(1);
        assertThat(result.countOf(Rank.FIFTH)).isEqualTo(1);

        // 총 상금
        long expectedTotal =
                2_000_000_000L + 30_000_000L + 1_500_000L + 50_000L + 5_000L;
        assertThat(result.totalPrize()).isEqualTo(expectedTotal);

        // 수익률: 티켓 6장 구매 = 6,000원
        double yieldPercent = result.yieldPercent(6_000);
        // 소수점 한 자리, 둘째 자리에서 반올림
        assertThat(yieldPercent).isEqualTo(33_859_250.0);
    }
}
