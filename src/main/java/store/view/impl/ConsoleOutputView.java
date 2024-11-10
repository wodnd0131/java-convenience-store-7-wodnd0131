package store.view.impl;

import store.view.OutputMessage;
import store.view.interfaces.OutputView;

public class ConsoleOutputView implements OutputView {
    private static final String lineFeed = "\n";

    @Override
    public void println(String string) {
        System.out.println(lineFeed + string);
    }

    @Override
    public void println(OutputMessage message, Object... args) {
        System.out.printf(message.getMessage(), args);
    }
}