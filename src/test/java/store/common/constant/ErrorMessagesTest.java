package store.common.constant;

import static store.common.constant.ErrorMessages.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ErrorMessagesTest {
    @Test
    @DisplayName("에러메세지는 반드시 접두사([ERROR])를 포함한다")
    void errorMessagesShouldStartWithPrefix() {
        String prefix = ERROR_PREFIX.getMessage();
        String message1 = String.valueOf(INVALID_INPUT_FORMAT);
        String message2 = String.valueOf(PRODUCT_NOT_FOUND);
        String message3 = String.valueOf(RESOURCE_NOT_FOUND);

        assertThat(message1).startsWith(prefix);
        assertThat(message2).startsWith(prefix);
        assertThat(message3).startsWith(prefix);
    }
}
