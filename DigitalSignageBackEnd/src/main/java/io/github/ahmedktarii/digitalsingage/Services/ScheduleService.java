package io.github.ahmedktarii.digitalsingage.Services;

import io.github.ahmedktarii.digitalsingage.Entities.Schedule;
import io.github.ahmedktarii.digitalsingage.Repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    public void saveSchedule(Schedule schedule) {
        scheduleRepository.save(schedule);
    }
}
