package ru.cft.focusstart.suhodolov;

import java.util.function.Supplier;

public class Task implements Supplier<Long> {

    private long result;
    private final int startVal;
    private final int endVal;

    public Task(final int startVal, final int endVal) {
        this.startVal = startVal;
        this.endVal = endVal;
    }

    private long calcFunction(final int num) {
        long functionResult = 0;

        for (int i = 1; i <= num; i += 5000) {
            int modVal = num % i;
            functionResult += modVal;
        }

        return functionResult;
    }

    @Override
    public Long get() {
        for (int i = startVal; i <= endVal; i++) {
            result += calcFunction(i);
        }
        return result;
    }
}
