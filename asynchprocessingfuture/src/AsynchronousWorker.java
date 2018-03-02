

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/* Asynchronous future call ,when future.get() invoked main thread is blocked (hence not considered as parralel task) and forced to wait until the taskcompletes*/
public class AsynchronousWorker {
    public AsynchronousWorker() {
    }

    
    public static void main(String[] args) {
        System.out.println("Start Work"  + new java.util.Date());
        ExecutorService es = Executors.newFixedThreadPool(3);
        final Future<Object> future = es.submit(new Callable<Object>() {
                    public Object call() throws Exception {
                        new SlowWorker().doWork();
                        return null;
                    }
                });
       
        System.out.println("... try to do something while the work is being done....");
        System.out.println("... and more ....");
        try {
            future.get();
        } catch (InterruptedException e) {
        } catch (ExecutionException e) {
        }
        
       /* while(future.isDone()){
        System.out.println("End work" + new java.util.Date());
        System.exit(0);
        }*/
    }
    
}
