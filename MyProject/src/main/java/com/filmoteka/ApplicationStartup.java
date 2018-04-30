package com.filmoteka;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.filmoteka.util.taskExecutors.CustomTaskExecutor;
import com.filmoteka.util.taskExecutors.ExpiredProductsDeleter;
import com.filmoteka.util.taskExecutors.ExpiringProductsNotifier;

@Component
public class ApplicationStartup {
	private static final LocalTime TASKS_STARTING_TIME = LocalTime.now().withHour(16).withMinute(23).withSecond(20);
	private static final List<CustomTaskExecutor> TASKS = new ArrayList<CustomTaskExecutor>();
	
	@EventListener()
	void contextRefreshEvent(ContextRefreshedEvent event) {
		//Listener gets called only when the main ApplicationContext gets refreshed.
		//Source -> https://stackoverflow.com/questions/3994860/how-to-execute-jobs-just-after-spring-loads-application-context
		if(event.getApplicationContext().getParent() == null) {
			//Add all utility tasks
			TASKS.add(new CustomTaskExecutor(ExpiringProductsNotifier.getInstance())); //Expiring products notifier
			TASKS.add(new CustomTaskExecutor(ExpiredProductsDeleter.getInstance())); // Expired products deleter
			
			//Start all task at the same time
			for (CustomTaskExecutor taskExecutor : TASKS) {
				taskExecutor.startExecutionAt(TASKS_STARTING_TIME.getHour(), TASKS_STARTING_TIME.getMinute(), TASKS_STARTING_TIME.getSecond());
			}	
		}
	}
}
