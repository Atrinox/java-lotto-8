package lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PurchaseAmountTest {

    @DisplayName("정상 금액(1,000원 단위의 양의 정수)은 생성된다")
    @Test
    void create_success_when_positive_and_multiple_of_1000() {
        assertThatCode(() -> PurchaseAmount.of("8000"))
                .doesNotThrowAnyException();
        assertThatCode(() -> PurchaseAmount.of("1000"))
                .doesNotThrowAnyException();
        assertThatCode(() -> PurchaseAmount.of("14000"))
                .doesNotThrowAnyException();
    }

    @DisplayName("숫자가 아니면 예외가 발생한다")
    @Test
    void fail_when_not_number() {
        assertThatThrownBy(() -> PurchaseAmount.of("1000j"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
        assertThatThrownBy(() -> PurchaseAmount.of(" "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("0 또는 음수면 예외가 발생한다")
    @Test
    void fail_when_zero_or_negative() {
        assertThatThrownBy(() -> PurchaseAmount.of("0"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
        assertThatThrownBy(() -> PurchaseAmount.of("-1000"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("1,000원 단위가 아니면 예외가 발생한다")
    @Test
    void fail_when_not_multiple_of_1000() {
        assertThatThrownBy(() -> PurchaseAmount.of("1500"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
        assertThatThrownBy(() -> PurchaseAmount.of("999"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }
}
