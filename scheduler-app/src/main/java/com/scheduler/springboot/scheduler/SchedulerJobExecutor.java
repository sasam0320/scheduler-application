package com.scheduler.springboot.scheduler;

import com.scheduler.springboot.domain.ScheduleJob;
import com.scheduler.springboot.domain.ScheduleTask;
import com.scheduler.springboot.utils.ScheduleExpression;
import com.scheduler.springboot.utils.ScheduleExpressionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Component("schedulerJobExecutor")
public class SchedulerJobExecutor implements JobRunner {

    @Autowired
    private ScheduledExecutorService executor;

    public SchedulerJobExecutor() {

    }

    @Override
    public void run(List<ScheduleJob> jobList) throws Exception {

        final int NUMBER_OF_THREADS = jobList.size();

        executor = Executors.newScheduledThreadPool(NUMBER_OF_THREADS);

        System.out.println("The time is : " + new Date());

        jobList.stream().forEach(job -> {

            String cron;
            ScheduleTask task = job.getTask();

            try {

                cron = task.getRecurrence();
                ScheduleExpression scheduleExp = ScheduleExpressionUtil.parseCron(cron);
                String second = scheduleExp.getSecond();
                String minute = scheduleExp.getMinute();
                String hour = scheduleExp.getHour();

                if (second.equals("*") && !minute.equals("*") && !hour.equals("*")) {

                    LocalDateTime time = LocalDateTime.now();

                    LocalDateTime next = time.withHour(Integer.valueOf(hour)).withMinute(Integer.valueOf(minute)).withSecond(0);

                    if (time.compareTo(next) > 0) next = next.plusDays(1);

                    Duration duration = Duration.between(time, next);
                    long initial = duration.getSeconds();

                    executor.scheduleAtFixedRate(job, initial, TimeUnit.DAYS.toSeconds(1), TimeUnit.SECONDS);

                } else if (!second.equals("*") && !minute.equals("*")) {

                    long initial = Integer.valueOf(minute) * 60 + Integer.valueOf(second);

                    System.out.println(initial + " seconds");

                    executor.schedule(job, initial, TimeUnit.SECONDS);

                } else if (minute.startsWith("/")) {

                    minute = minute.substring(1);
                    long period = Long.valueOf(minute);
                    executor.scheduleAtFixedRate(job, 0, period, TimeUnit.MINUTES);

                }


            } catch (Exception e) {

                e.getMessage();
            }

        });

    }
}
