package com.savinpp.springboot.crude.controller;

import com.savinpp.springboot.crude.entity.User;
import com.savinpp.springboot.crude.entity.Role;
import com.savinpp.springboot.crude.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class MyController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String showAll(Model model) {
        model.addAttribute("index", userService.index());
        System.out.println(userService.index());
        return "index";
    }

    @GetMapping("/add")
    public String getUser(Model model) {
        model.addAttribute("listRole", userService.listRoles());
        return "add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("addUser") User user,
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

    @GetMapping("/delete/{id}")
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
}