package com.savinpp.springboot.crude.controller;

import com.savinpp.springboot.crude.entity.User;
import com.savinpp.springboot.crude.entity.Role;
import com.savinpp.springboot.crude.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class MyController {

    @Autowired
    private UserService userService;




    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView AllUsers(ModelAndView modelAndView, @AuthenticationPrincipal User user, Principal principal) {
        modelAndView.setViewName("test.html");
        modelAndView.addObject("user", new User());
        modelAndView.addObject("listRole", userService.listRoles());
        modelAndView.addObject("index", userService.index());
        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("oneUser", userService.showUserByUsername(principal.getName()));
        return modelAndView;
    }

/*    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String showAll(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("index", userService.index());
        model.addAttribute("user", new User());
        model.addAttribute("currentUser", user);
        model.addAttribute("listRole", userService.listRoles());
        return "test";
    }*/



    @GetMapping("/createUser")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "createUser";
    }

    @PostMapping("/createUser")
    public String add(@ModelAttribute("user") User user,
                      @RequestParam(value = "newRole", required = false) String[] role) {
        user.setRoles(getAddRole(role));
        userService.save(user);
        return "redirect:/admin";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.show(id));
        model.addAttribute("listRole", userService.listRoles());
        return "edit";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam(value = "newRole", required = false) String[] role) {
        user.setRoles(getAddRole(role));
        userService.update(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        User user=userService.show(id);
        userService.delete(user);
        return "redirect:/admin";
    }

    private Set<Role> getAddRole (String[] role) {
        Set<Role> roleSet=new HashSet<>();
        for (int i=0; i<role.length; i++) {
            roleSet.add(userService.getRoleByName(role[i]));
        }
        return roleSet;
    }

    @GetMapping("/user")
    public String clickMe(Model model, Principal principal) {
        model.addAttribute("oneUser", userService.showUserByUsername(principal.getName()));
        return "user";
    }

}