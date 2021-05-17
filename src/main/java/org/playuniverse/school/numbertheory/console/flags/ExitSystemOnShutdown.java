package org.playuniverse.school.numbertheory.console.flags;

import org.playuniverse.school.numbertheory.console.Console;
import org.playuniverse.school.numbertheory.console.ConsoleFlag;

class ExitSystemOnShutdown implements ConsoleFlag {
	
	@Override
	public void onShutdown(Console console) {
		System.exit(0);
	}

}
