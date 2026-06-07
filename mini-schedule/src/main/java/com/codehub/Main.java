package com.codehub;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 *
 *
 * @author : Tomatos
 * @date : 2026/6/7
 */
public class Main {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public static void main(String[] args) throws InterruptedException {
        ScheduleService scheduleService = new ScheduleService();
        Runnable task1 = () -> System.out.printf("[%s]: first task\n", LocalDateTime.now().format(FORMATTER));
        scheduleService.execute(
                task1,
                500
        );
        Runnable task2 = () -> System.out.printf("[%s] : second task\n",  LocalDateTime.now().format(FORMATTER));
        scheduleService.execute(
                task2,
                300
        );
        TimeUnit.SECONDS.sleep(3);
        scheduleService.stop(task1);
    }
}