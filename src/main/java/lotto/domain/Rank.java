package lotto.domain;

public enum Rank {
    FIRST(6, false, 2_000_000_000L),
    SECOND(5, true, 30_000_000L),
    THIRD(5, false, 1_500_000L),
    FOURTH(4, false, 50_000L),
    FIFTH(3, false, 5_000L);

    private final int match;
    private final boolean needBonus;
    private final long prize;

    Rank(int match, boolean needBonus, long prize) {
        this.match = match;
        this.needBonus = needBonus;
        this.prize = prize;
    }

    public long prize() {
        return prize;
    }

    public static Rank of(int matchCount, boolean bonusMatch) {
        if (matchCount == 6) return FIRST;
        if (matchCount == 5 && bonusMatch) return SECOND;
        if (matchCount == 5) return THIRD;
        if (matchCount == 4) return FOURTH;
        if (matchCount == 3) return FIFTH;
        return null; // 낙첨
    }
}
