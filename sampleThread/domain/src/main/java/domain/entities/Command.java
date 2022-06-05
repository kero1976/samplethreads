package domain.entities;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Command {
	private static Logger log = LoggerFactory.getLogger(Command.class);
	
	public Command(String cmd, List<String> params) {
		super();
		this.cmd = cmd;
		this.params = params;
	}
	private String cmd;
	private List<String> params;

	
	public List<String> getCommand(){
		
		log.debug("cmd:" + cmd);
		Path p = Paths.get(cmd);
		if(Files.exists(p)) {
			log.debug("Comd Exists.");
		}else {
			log.debug("Comd Not Exists.");
		}
	    List<String> cmdAndParams = new LinkedList<>();
	    cmdAndParams.add(cmd);
	    if(params != null) {
		    cmdAndParams.addAll(params);
	    }
	    return cmdAndParams;
	}

}
