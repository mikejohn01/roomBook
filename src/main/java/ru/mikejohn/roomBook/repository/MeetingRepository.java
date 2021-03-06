package ru.mikejohn.roomBook.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.mikejohn.roomBook.model.Meeting;

import java.time.LocalDateTime;
import java.util.List;

public interface MeetingRepository extends CrudRepository<Meeting, Long> {
    Meeting findById(long id);

    @Query("select mt from Meeting mt where (mt.startTime>=?1 and mt.endTime<?2)")
    List<Meeting> findByStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    @Query("select count(mt) from Meeting mt where (mt.startTime<=?1 and mt.endTime>?1)or(mt.startTime<?2 and mt.endTime>=?2)")
    Integer findByStartDateBetweenOrFinishDateBetween(LocalDateTime startTime, LocalDateTime endTime);

}
