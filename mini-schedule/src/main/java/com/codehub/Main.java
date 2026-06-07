package com.codehub;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 *
 * @author : Tomatos
 * @date : 2026/6/7
 */
public class Main {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public static void main(String[] args) {
        ScheduleService scheduleService = new ScheduleService();
        scheduleService.execute(
                () -> System.out.printf("[%s]: first task\n", LocalDateTime.now().format(FORMATTER)),
                500)
        ;
        scheduleService.execute(
                () -> System.out.printf("[%s] : second task\n",  LocalDateTime.now().format(FORMATTER)),
                300
        );
    }
}