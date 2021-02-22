package com.scheduler.springboot.repo;

import com.scheduler.springboot.domain.ScheduleTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleTaskRepository extends CrudRepository<ScheduleTask, Long> {

    List<ScheduleTask> findAll();
    Optional<ScheduleTask> findById(Long id);
    Optional<ScheduleTask> findByName(String name);

}
