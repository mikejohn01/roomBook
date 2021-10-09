package ru.mikejohn.roomBook.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.mikejohn.roomBook.model.Meeting;

@Controller
public class MainController {

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

}
