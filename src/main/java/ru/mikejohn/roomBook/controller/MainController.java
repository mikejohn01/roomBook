package ru.mikejohn.roomBook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mikejohn.roomBook.model.Meeting;
import ru.mikejohn.roomBook.model.User;
import ru.mikejohn.roomBook.service.MeetingService;
import ru.mikejohn.roomBook.service.UserService;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private MeetingService meetingService;
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/roombook")
    public String roombookPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("user", userService.findUserByUsername(auth.getName()));

        Map<LocalDate, List<Meeting>> meetings = meetingService.getMeetingByWeek();
        Iterable<LocalDate> daysOfWeek = meetingService.getDaysOfWeek();
        model.addAttribute("daysOfWeek", daysOfWeek);
        model.addAttribute("hours", meetingService.hours);
        model.addAttribute("meetings", meetings);
        return "roombook";
    }

    @PostMapping("roombook/nextWeek")
    public String getNextWeek(){
        meetingService.setNextWeek();
        return "redirect:/roombook";
    }
    @PostMapping("roombook/prevWeek")
    public String getPrevWeek(){
        meetingService.setPrevWeek();
        return "redirect:/roombook";
    }

    @GetMapping("/details/{id}")
    public String getDetails(@PathVariable long id, Model model){
        Meeting meeting = meetingService.getMeetingById(id);
        model.addAttribute(meeting);
        return "details";
    }
    @PostMapping ("/details/{id}")
    public String changeMeeting(@PathVariable long id,
            @RequestParam("date") @DateTimeFormat (pattern = "dd.MM.yyyy") LocalDate date,
            @RequestParam("title") String title,
            @RequestParam("startTime") @DateTimeFormat (pattern = "HH.mm") LocalTime sT,
            @RequestParam("endTime") @DateTimeFormat (pattern = "HH.mm") LocalTime eT,
            @RequestParam("description") String description,
            Model model){
        LocalDateTime startTime = LocalDateTime.of(date, sT);
        LocalDateTime endTime = LocalDateTime.of(date, eT);

        Meeting meeting = meetingService.getMeetingById(id);
        meeting.setDate(date);
        meeting.setTitle(title);
        meeting.setStartTime(startTime);
        meeting.setEndTime(endTime);
        meeting.setDescription(description);
        meetingService.update(meeting);
        return "redirect:/roombook";
    }

    @GetMapping("/details/delete/{id}")
    public String deleteMeeting(@PathVariable long id, Model model){
        meetingService.deleteMeetingById(id);
        return "redirect:/roombook";
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
        if( meetingService.hasError(startTime, endTime)){
            return "/new_meeting";
        }
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
