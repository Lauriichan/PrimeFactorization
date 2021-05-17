package org.playuniverse.school.numbertheory.console.flags;

import java.util.Objects;
import java.util.function.Consumer;

import org.playuniverse.school.numbertheory.console.Console;
import org.playuniverse.school.numbertheory.console.ConsoleFlag;

class ExecuteOnShutdown implements ConsoleFlag {

    private final Consumer<Console> execute;

    public ExecuteOnShutdown(Consumer<Console> execute) {
        this.execute = Objects.requireNonNull(execute);
    }

    @Override
    public void onShutdown(Console console) {
        execute.accept(console);
    }

}
