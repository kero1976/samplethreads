package api;

import java.util.concurrent.Callable;

import domain.entities.Command;
import domain.utils.CommandExecutor;

public class AppliExecute implements Callable<Integer> {
	  private Command cmd;

	  public AppliExecute(Command cmd) {
	    this.cmd = cmd;
	  }

	  @Override
	  public Integer call() throws Exception {
    	return CommandExecutor.execute(cmd, 40);
	  }
	}