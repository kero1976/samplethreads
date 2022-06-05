package api;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import domain.entities.Command;

public class ApiMain1 {

	
	private static Logger log = LoggerFactory.getLogger(ApiMain1.class);
	
	public static void main(String[] args) {
		MDC.put("pid", ManagementFactory.getRuntimeMXBean().getName().replaceAll("@.*", ""));
		log.debug("START");
	    ExecutorService es = Executors.newCachedThreadPool();
		//ExecutorService es = Executors.newSingleThreadExecutor();
	    
	    // Taskの作成
	    List<AppliExecute> tasks = new ArrayList<>();
	    for(int i = 0; i < 3; i ++) {
        	MDC.put("pid", ManagementFactory.getRuntimeMXBean().getName().replaceAll("@.*", ""));
        	Command command = new Command("../Appli/Debug/ConsoleApp2.exe",null);
	        tasks.add(new AppliExecute(command));
	    }
	    // Futureを受け取る
	    List<Future<Integer>> futures = new ArrayList<>();
	    for(AppliExecute task: tasks) {
	    	Future<Integer> future = es.submit(task);
	    	futures.add(future);
	    }

	    es.shutdown();
	    try {
	        es.awaitTermination(5, TimeUnit.SECONDS);
	        log.debug("es終了");
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	    for (Future<Integer> future : futures) {
	    	Integer result;
			try {
				result = future.get();
				log.debug("Result:" + result);
			} catch (InterruptedException | ExecutionException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
	    	
	    }

	    //es.invokeAll(null)
	    log.debug("スレッド処理END");
	}

}
