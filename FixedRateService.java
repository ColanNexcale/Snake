import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ScheduledFuture;


abstract class FixedRateService implements Runnable{
	
	Thread t;
	ScheduledFuture<?> sf;
	ScheduledExecutorService ses;
	Runnable task;
	String threadName;
	int interval;
	int delay;
	boolean running = true;

	
	FixedRateService (String name){
		threadName = name;
	}
	
	abstract Runnable createTask();
	
	public void run (){
		ses = Executors.newScheduledThreadPool(1);
		task = createTask();
		sf = ses.scheduleAtFixedRate(task, delay, interval, TimeUnit.MILLISECONDS);
	}
	
	void stop(){	
		if (ses != null && sf != null){
			ses.shutdown();
			sf.cancel(true);	
		}
	}
	
	public void start(){
		if (t == null){
			t = new Thread(this, threadName);
			t.start();
		}
	}
}
