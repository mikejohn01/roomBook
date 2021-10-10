package ru.mikejohn.roomBook.model;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name="meetings")
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate date;

    private String title;

    @DateTimeFormat(pattern = "HH.mm")
    @Column(name="start_time")
    private LocalDateTime startTime;

    @DateTimeFormat(pattern = "HH.mm")
    @Column(name="end_time")
    private LocalDateTime endTime;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User owner;
}
