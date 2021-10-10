package ru.mikejohn.roomBook.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

import java.time.LocalDate;

@Controller
public class MainController {

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
    public String addMeeting(@RequestParam("date") String date, @RequestParam("title") String title,
                             @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime,
                             @RequestParam("description") String description,
                             @AuthenticationPrincipal User owner,
                             Model model) {
//        Meeting meeting = new Meeting();
//        meeting.setOwner(owner);
//        meeting.setDate(date);
//        meeting.setTitle(title);
//        meeting.setStartTime(startTime);
//        meeting.setEndTime(endTime);
//        meeting.setDescription(description);

        LocalDate startDate = LocalDate.parse(date);
        Meeting meeting = Meeting.builder().date(startDate).build();

        meetingService.add(meeting);
        return "redirect:/roombook";
    }

}
