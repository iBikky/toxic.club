package me.endr.toxic.util.hwid;

import me.endr.toxic.Toxic;

public class NoStackTraceThrowable extends RuntimeException {

    public NoStackTraceThrowable(final String msg) {
        super(msg);
        this.setStackTrace(new StackTraceElement[0]);
    }

    @Override
    public String toString() {
        return "" + Toxic.getVersion();
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
