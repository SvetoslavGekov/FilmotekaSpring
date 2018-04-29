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
public class Startup {
	private static final LocalTime TASKS_STARTING_TIME = LocalTime.now().withHour(19).withMinute(24).withSecond(00);
	private static final List<CustomTaskExecutor> TASKS = new ArrayList<>();
	
	@EventListener(ContextRefreshedEvent.class)
	void contextRefreshEvent() {
		//Create all utility tasks
		TASKS.add(new CustomTaskExecutor(ExpiringProductsNotifier.getInstance())); //Expiring products notifier
		TASKS.add(new CustomTaskExecutor(ExpiredProductsDeleter.getInstance()));// Expired products deleter
		
		//Start all task at the same time
		for (CustomTaskExecutor taskExecutor : TASKS) {
			taskExecutor.startExecutionAt(TASKS_STARTING_TIME.getHour(), TASKS_STARTING_TIME.getMinute(), TASKS_STARTING_TIME.getSecond());
		}
	}
}
