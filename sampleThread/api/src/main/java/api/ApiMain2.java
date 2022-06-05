package api;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import domain.entities.Command;

public class ApiMain2 {


	
	private static Logger log = LoggerFactory.getLogger(ApiMain2.class);
	
	public static void main(String[] args) {
		MDC.put("pid", ManagementFactory.getRuntimeMXBean().getName().replaceAll("@.*", ""));
		log.debug("START");
	    ExecutorService es = Executors.newCachedThreadPool();
		//ExecutorService es = Executors.newSingleThreadExecutor();
	    
	    // Taskの作成
	    
	    List<Future<Integer>> futures = new ArrayList<>();
	    
	    for(int i = 0; i < 3; i ++) {
        	MDC.put("pid", ManagementFactory.getRuntimeMXBean().getName().replaceAll("@.*", ""));
//        	List<String> params = new ArrayList<String>();
//        	params.add("" + (7 + i));
//        	params.add(""  + i);
        	Command command = new Command("../Appli/Debug/ConsoleApp2.exe",null);
        	futures.add(es.submit(new AppliExecute(command)));


	    }
	    try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
	    futures.get(1).cancel(true);

	    es.shutdown();
	    try {
	        es.awaitTermination(20, TimeUnit.SECONDS);
	        log.debug("es終了");
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	    try {
		    for (Future<Integer> future : futures) {
		    	Integer result;
				try {
					result = future.get();
					log.debug("Result:" + result);
				} catch (InterruptedException | ExecutionException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}catch(CancellationException e2) {
					log.debug("キャンセルされました:");
				}
		    	
		    }
	    }catch(Exception e){
	    	log.debug("ERROR:" + e);
	    }finally {
		    log.debug("スレッド処理END");
		}


	    //es.invokeAll(null)

	}

}
