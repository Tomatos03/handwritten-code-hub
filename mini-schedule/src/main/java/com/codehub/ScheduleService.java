package com.codehub;

import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.LockSupport;

/**
 *
 *
 * @author : Tomatos
 * @date : 2026/6/7
 */
public class ScheduleService {

    private final Executor threadPool = Executors.newFixedThreadPool(5);;
    private final Trigger trigger = new Trigger();
    private final Map<Runnable, Job> jobMap = new ConcurrentHashMap<>();

    public void execute(Runnable runnable, long delay) {
        Job job = new Job(String.format("Job-%s", UUID.randomUUID().toString().substring(0, 4)));
        job.setRunnable(runnable);
        job.setStartTime(System.currentTimeMillis() + delay);
        job.setDelay(delay);
        jobMap.put(runnable, job);
        trigger.add(job);
        System.out.println("add job to trigger, job name: " + job.getName());
        trigger.wakeUp();
    }

    public void stop(Runnable runnable) {
        jobMap.get(runnable)
              .setInterrupted(true);
        jobMap.remove(runnable);
    }

    private class Trigger {
        private final PriorityBlockingQueue<Job> queue = new PriorityBlockingQueue<>();
        private final Thread thread = new Thread(this::triggerTask, triggerName());

        {
            thread.start();
        }

        public Trigger() {
        }

        public void add(Job job) {
            queue.add(job);
        }

        void triggerTask() {
            while (true) {
                // 没有任何任务，阻塞当前线程，直到有新的任务添加到队列中
                // 新任务添加到任务队列，调用wakeUp()唤醒当前线程
                while (queue.isEmpty()) {
                    LockSupport.park();
                }
                Job job = queue.peek();
                if (shouldExecuteJob(job)) {
                    // 处理添加的新任务开始时间比当前开始时间更早的情况
                    // 这里的poll方法拿到的job可能与peek方法拿到的job不是同一个job
                    job = queue.poll();
                    if (job.isInterrupted()) {
                        System.out.printf("%s is interrupted, skip it\n", job.getName());
                        continue;
                    }
                    threadPool.execute(job.getRunnable());

                    // 执行完任务后，重新添加一个新的任务到队列中，新的任务的开始时间是当前时间加上原来的延迟时间。
                    Job newJob = new Job(job.getName());
                    newJob.setRunnable(job.getRunnable());
                    newJob.setStartTime(job.getDelay() + System.currentTimeMillis());
                    newJob.setDelay(job.getDelay());
                    newJob.setInterrupted(job.isInterrupted());
                    jobMap.computeIfPresent(job.getRunnable(), (runnable, oldJob) -> newJob);
                    trigger.add(newJob);
                } else {
                    LockSupport.parkUntil(job.getStartTime());
                }
            }
        }

        private boolean shouldExecuteJob(Job job) {
            return job.getStartTime() < System.currentTimeMillis();
        }

        void wakeUp() {
            LockSupport.unpark(thread);
        }

        private String triggerName() {
            return String.format("Trigger-%s", new Random().nextInt(1000));
        }
    }
}

