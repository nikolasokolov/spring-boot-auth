package com.starter.authentication.controller;

import com.starter.authentication.exception.EmailTakenException;
import com.starter.authentication.exception.UsernameTakenException;
import com.starter.authentication.model.User;
import com.starter.authentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String openHomePage() {
        return "index";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String submitCreateNewUserForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String submitCreateNewUserForm(@Valid User user, BindingResult bindingResult, Model model, @RequestParam(value = "confirmPassword") String confirmPassword) {
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("wrongPassword", "Passwords doesn't match.");
            return "register";
        }
        if (bindingResult.hasErrors()) {
            return "register";
        }
        try {
            userService.save(user);
        } catch (UsernameTakenException e) {
            model.addAttribute("usernameTaken", "Username " + user.getUsername() + " is already taken");
            return "register";
        } catch (EmailTakenException e) {
            model.addAttribute("emailTaken", "Email " + user.getEmail() + " is already taken");
            return "register";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "/";
    }

    @RequestMapping(value = "/login/error", method = RequestMethod.GET)
    public String loginError(Model model) {
        model.addAttribute("loginError", "Incorrect credentials! Try Again");
        return "login";
    }
}
