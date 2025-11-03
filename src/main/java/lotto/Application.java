package lotto;

import camp.nextstep.edu.missionutils.Console;
import lotto.domain.PurchaseAmount;
import lotto.domain.Rank;
import lotto.domain.Result;
import lotto.domain.WinningNumbers;
import lotto.service.LottoMachine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Application {
    private static final String ERROR_PREFIX = "[ERROR] ";

    public static void main(String[] args) {
        try {
            String rawAmount = Console.readLine();
            PurchaseAmount purchase = PurchaseAmount.of(rawAmount);
            int count = purchase.tickets();

            List<Lotto> tickets = LottoMachine.issue(count);

            printPurchase(count);
            printTickets(tickets);

            List<Integer> winning = parseWinningNumbers(Console.readLine());
            int bonus = parseIntStrict(Console.readLine(), ERROR_PREFIX + "보너스 번호는 숫자여야 합니다.");

            WinningNumbers winningNumbers = new WinningNumbers(winning, bonus);
            Result result = Result.of(tickets, winningNumbers);

            printStatistics(result, purchase.value());
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            System.out.println(msg != null ? msg : ERROR_PREFIX);
            return;
        } finally {
            Console.close();
        }
    }

    private static void printPurchase(int count) {
        System.out.println(count + "개를 구매했습니다.");
    }

    private static void printTickets(List<Lotto> tickets) {
        for (Lotto lotto : tickets) {
            List<Integer> sorted = new ArrayList<>(lotto.getNumbers());
            Collections.sort(sorted);
            System.out.println(sorted);
        }
    }

    private static void printStatistics(Result result, int purchaseAmount) {
        System.out.println();
        System.out.println("당첨 통계");
        System.out.println("---");
        System.out.println("3개 일치 (5,000원) - " + result.countOf(Rank.FIFTH) + "개");
        System.out.println("4개 일치 (50,000원) - " + result.countOf(Rank.FOURTH) + "개");
        System.out.println("5개 일치 (1,500,000원) - " + result.countOf(Rank.THIRD) + "개");
        System.out.println("5개 일치, 보너스 볼 일치 (30,000,000원) - " + result.countOf(Rank.SECOND) + "개");
        System.out.println("6개 일치 (2,000,000,000원) - " + result.countOf(Rank.FIRST) + "개");
        System.out.printf("총 수익률은 %.1f%%입니다.%n", result.yieldPercent(purchaseAmount));
    }

    private static List<Integer> parseWinningNumbers(String raw) {
        String[] tokens = raw.split(",");
        if (tokens.length != 6) {
            throw new IllegalArgumentException(ERROR_PREFIX + "당첨 번호는 쉼표(,)로 구분된 6개여야 합니다.");
        }
        List<Integer> nums = new ArrayList<>(6);
        for (String t : tokens) {
            nums.add(parseIntStrict(t.trim(), ERROR_PREFIX + "당첨 번호는 숫자여야 합니다."));
        }
        return nums;
    }

    private static int parseIntStrict(String s, String errorWithPrefix) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            throw new IllegalArgumentException(errorWithPrefix);
        }
    }
}
