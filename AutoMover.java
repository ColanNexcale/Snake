import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ScheduledFuture;
 import static java.util.concurrent.TimeUnit.*;


class AutoMover extends FixedRateService{
	
	int delay = 1000;
	int initialDelay = delay;
	float scaleFactor = 0.9f;
	final int LOWER_SPEED_LIMIT = 100;
	static Controller controller;
	
	AutoMover (int speed, String name){
		super(name);
		interval = speed;
		delay = speed;
		controller = controller.getController();
	}
	
	public Runnable createTask(){
		return new Runnable(){
			@Override
			public void run(){
				controller.moveSnake();
			}	
		};
	}
	
	void updateSpeed(){
		int newSpeed = (int) (interval*scaleFactor	);
		if (newSpeed < LOWER_SPEED_LIMIT) newSpeed = LOWER_SPEED_LIMIT;
			sf.cancel(true);
		if (sf.isCancelled())
			sf = ses.scheduleAtFixedRate(task,delay,newSpeed,TimeUnit.MILLISECONDS);
		delay = newSpeed;
		interval = newSpeed;
		System.out.println(newSpeed);
	}
}
