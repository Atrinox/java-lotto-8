package lotto.domain;

import lotto.Lotto;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class Result {
    private final Map<Rank, Integer> counts = new EnumMap<>(Rank.class);

    private Result() {
        for (Rank r : Rank.values()) {
            counts.put(r, 0);
        }
    }

    public static Result of(List<Lotto> tickets, WinningNumbers winningNumbers) {
        Result result = new Result();
        for (Lotto lotto : tickets) {
            Rank rank = MatchJudge.judge(lotto, winningNumbers);
            if (rank == null) continue;
            result.counts.put(rank, result.counts.get(rank) + 1);
        }
        return result;
    }

    public int countOf(Rank rank) {
        return counts.getOrDefault(rank, 0);
    }

    public long totalPrize() {
        long sum = 0L;
        for (Map.Entry<Rank, Integer> e : counts.entrySet()) {
            sum += e.getKey().prize() * e.getValue();
        }
        return sum;
    }

    /** 수익률(%)을 소수점 한 자리까지 출력 규칙에 맞게 반올림하여 반환 */
    public double yieldPercent(long purchaseAmount) {
        if (purchaseAmount <= 0) return 0.0;
        double ratio = (double) totalPrize() / purchaseAmount * 100.0;
        return Math.round(ratio * 10.0) / 10.0; // 둘째 자리에서 반올림 → 한 자리
    }
}
