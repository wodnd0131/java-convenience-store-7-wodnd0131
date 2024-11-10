package store.view.interfaces;

import store.view.OutputMessage;

public interface OutputView {
    void println(String string);

    void println(OutputMessage message, Object... args);
}
