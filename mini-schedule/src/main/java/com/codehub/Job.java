package com.codehub;

/**
 *
 *
 * @author : Tomatos
 * @date : 2026/6/7
 */
public class Job implements Comparable<Job> {
    private long startTime;
    private Runnable runnable;
    private long delay;
    private volatile boolean isInterrupted = false;
    private String name;

    public Job(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDelay() {
        return delay;
    }

    public boolean isInterrupted() {
        return isInterrupted;
    }

    public void setInterrupted(boolean interrupted) {
        isInterrupted = interrupted;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public int compareTo(Job job) {
        return Long.compare(this.startTime, job.startTime);
    }
}
