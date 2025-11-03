package lotto.domain;

import lotto.Lotto;

public final class MatchJudge {
    private MatchJudge() { }

    public static Rank judge(Lotto ticket, WinningNumbers winning) {
        int match = matchCount(ticket, winning);
        boolean bonus = ticket.getNumbers().contains(winning.bonus());
        return Rank.of(match, bonus);
    }

    private static int matchCount(Lotto ticket, WinningNumbers winning) {
        return (int) ticket.getNumbers().stream()
                .filter(winning::contains)
                .count();
    }
}
