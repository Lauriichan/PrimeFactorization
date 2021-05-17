package org.playuniverse.school.numbertheory.console.flags;

import java.util.function.Consumer;

import org.playuniverse.school.numbertheory.console.Console;
import org.playuniverse.school.numbertheory.console.ConsoleFlag;

public final class ConsoleFlags {

    public static ConsoleFlag shutdownNoResponse(int seconds) {
        return new ShutdownWithoutResponseFlag(seconds);
    }

    public static ConsoleFlag exitSystemOnShutdown() {
        return new ExitSystemOnShutdown();
    }

    public static ConsoleFlag executeOnShutdown(Consumer<Console> execute) {
        return new ExecuteOnShutdown(execute);
    }

    public static ConsoleFlag executeOnShutdown(Runnable execute) {
        return executeOnShutdown(console -> execute.run());
    }

}
