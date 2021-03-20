package ru.geekbrains.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.security.entities.User;
import ru.geekbrains.security.services.UserService;

import java.security.Principal;

@RestController()
@RequestMapping("/score")
public class Controller {

    private final UserService userService;

    @Autowired
    public Controller(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String daoTestPage(Principal principal) {
        //User user = getUser(principal);
        return "authenticated: "; // + user.getUsername();
    }

    @GetMapping("/inc")
    public boolean incScore(Principal principal) {
        User user = getUser(principal);
        user.setScore(user.getScore() + 1);
        return true;
    }

    @GetMapping("/dec")
    public boolean decScore(Principal principal) {
        User user = getUser(principal);
        user.setScore(user.getScore() - 1);
        return true;
    }

    @GetMapping("/get/current")
    public int getCurrentScore(Principal principal) {
        return getUser(principal).getScore();
    }

    @GetMapping("/get/{id}")
    public int getScoreById(@PathVariable long id) {
        return getUser(id).getScore();
    }

    private User getUser(Principal principal) {
        return userService.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("unable to find user by username: " + principal.getName()));
    }

    private User getUser(long id) {
        return userService.findBiId(id).orElseThrow(() -> new RuntimeException("unable to find user by id: " + id));
    }


}
