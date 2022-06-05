package domain.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.entities.Command;
import domain.exceptions.CommandExecuteFailedException;

public final class CommandExecutor {

	private static Logger log = LoggerFactory.getLogger(CommandExecutor.class);
	
    public static int execute(Command command, long timeoutSec) {
    	log.debug("プロセス起動START");
        ProcessBuilder builder = new ProcessBuilder(command.getCommand());
        builder.redirectErrorStream(true);  // 標準エラー出力の内容を標準出力にマージする

        Process process;
        try {
            process = builder.start();
        } catch (IOException e) {
            throw new CommandExecuteFailedException("Command launch failed. [cmd: " + command + "]", e);
        }

        int exitCode;
        try {
            // 標準出力をすべて読み込む
            new Thread(() -> {
                try (InputStream is = process.getInputStream()) {
                    while (is.read() >= 0); 
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }).start();

            boolean end = process.waitFor(timeoutSec, TimeUnit.SECONDS);
            if (end) {
                exitCode = process.exitValue();
            } else {
                throw new CommandExecuteFailedException("Command timeout. [CommandPath: " + command + "]");
            }

        } catch (InterruptedException e) {
            throw new CommandExecuteFailedException("Command interrupted. [CommandPath: " + command + "]", e);
        } finally {
            if (process.isAlive()) {
                process.destroy(); // プロセスを強制終了
            }
        }
    	log.debug("プロセス起動END");
        return exitCode;
    }

    private CommandExecutor() {
    }
}