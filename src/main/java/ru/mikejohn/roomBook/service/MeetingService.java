package ru.mikejohn.roomBook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mikejohn.roomBook.model.Meeting;
import ru.mikejohn.roomBook.repository.MeetingRepository;

@Service
public class MeetingService {
    @Autowired
    private MeetingRepository meetingRepository;

    public void add(Meeting meeting){
        meetingRepository.save(meeting);
    }
}
