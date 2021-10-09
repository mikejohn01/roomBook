package ru.mikejohn.roomBook.model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="meetings")
@Data
@NoArgsConstructor
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User owner;

    @NotNull
    private String title;

    private String description;
    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime finishDate;

    public Meeting(
            User owner,
            @NotNull String title,
            String description,
            @NotNull LocalDateTime startDate,
            @NotNull LocalDateTime finishDate
    ) {
        this.owner = owner;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }
}
