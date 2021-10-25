package ru.mikejohn.roomBook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.DateUtils;
import ru.mikejohn.roomBook.model.Meeting;
import ru.mikejohn.roomBook.repository.MeetingRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.temporal.ChronoUnit.MINUTES;

@Service
public class MeetingService {
    @Autowired
    private MeetingRepository meetingRepository;

    private static LocalDate monday = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

    public static final String[] hours = new String[] { "00:00", "01:00", "02:00", "03:00", "04:00",
            "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00",
            "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "24:00"
    };

    public void add(Meeting meeting) {
        meetingRepository.save(meeting);
    }

    public Map<LocalDate, List<Meeting>> getMeetingByWeek() {
        Map<LocalDate, List<Meeting>> meetings = new HashMap<>();

        for (int i = 0; i < 6; i++) {
            LocalDateTime date = LocalDateTime.of(monday.plusDays(i), LocalTime.of(0, 0));

            List<Meeting> meetingsInDay = meetingRepository.findByStartTimeBetween(date, date.plusDays(1));
            meetings.put(date.toLocalDate(), meetingsInDay);
        }
        return meetings;
    }

    public Meeting getMeetingById(long id) {return meetingRepository.findById(id);};

    public static List<LocalDate> getDaysOfWeek() {
        List<LocalDate> week = new ArrayList<>();
        for(int i=0;i<6;i++){
            week.add(monday.plusDays(i));
        }
        return week;
    }

    public void setNextWeek(){
        monday = monday.plusDays(7);
    }

    public void setPrevWeek(){
        monday = monday.minusDays(7);
    }

    public boolean hasError(LocalDateTime startTime, LocalDateTime endTime){
        long periiod = ChronoUnit.MINUTES.between(startTime, endTime);
        if ( periiod < 30){
                return true;}
        else if (0!=meetingRepository.findByStartDateBetweenOrFinishDateBetween(startTime, endTime)) {
            return true;
        } else {
            return false;
        }
    }
    public void deleteMeetingById(long id) {
        meetingRepository.deleteById(id);
    }

    public void update(Meeting meeting) { meetingRepository.save(meeting); }
}
