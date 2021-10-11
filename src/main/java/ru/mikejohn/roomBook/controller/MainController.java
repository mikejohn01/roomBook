package ru.mikejohn.roomBook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mikejohn.roomBook.model.Meeting;
import ru.mikejohn.roomBook.model.User;
import ru.mikejohn.roomBook.service.MeetingService;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller
public class MainController {
    @Autowired
    private MeetingService meetingService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/roombook")
    public String roombookPage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return "roombook";
    }

    @GetMapping("/new_meeting")
    public String newMeeting(Model model) {
        model.addAttribute("meeting", new Meeting());
        return "new_meeting";
    }
    @PostMapping("/new_meeting")
    public String addMeeting(
            @RequestParam("date") @DateTimeFormat (pattern = "dd.MM.yyyy") LocalDate date,
            @RequestParam("title") String title,
            @RequestParam("startTime") @DateTimeFormat (pattern = "HH.mm") LocalTime sT,
            @RequestParam("endTime") @DateTimeFormat (pattern = "HH.mm") LocalTime eT,
            @RequestParam("description") String description,
            @AuthenticationPrincipal User owner,
            Model model) {

        LocalDateTime startTime = LocalDateTime.of(date, sT);
        LocalDateTime endTime = LocalDateTime.of(date, eT);

        Meeting meeting = Meeting.builder()
                .date(date)
                .title(title)
                .startTime(startTime)
                .endTime(endTime)
                .description(description)
                .owner(owner)
                .build();

        meetingService.add(meeting);
        return "redirect:/roombook";
    }

}
