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
    private long dealy;

    public long getDealy() {
        return dealy;
    }

    public void setDealy(long dealy) {
        this.dealy = dealy;
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
