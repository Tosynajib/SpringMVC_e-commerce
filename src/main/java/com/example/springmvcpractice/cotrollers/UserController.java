package com.example.springmvcpractice.cotrollers;

import com.example.springmvcpractice.dtos.PasswordDTO;
import com.example.springmvcpractice.dtos.UsersDTO;
import com.example.springmvcpractice.models.Users;
import com.example.springmvcpractice.serviceImpl.UsersServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
    private UsersServiceImpl usersService;

    @Autowired
    public UserController(UsersServiceImpl userService) {this.usersService = userService;}

    @GetMapping("/register")
    public String indexPage(Model model){
        model.addAttribute("user", new UsersDTO());
        return "sign-up";
    }

    @GetMapping("/login")
    public ModelAndView loginPage(){
        return new ModelAndView("login")
                .addObject("user", new UsersDTO());
    }

    @PostMapping("/sign-up")
    public String signUp(@ModelAttribute UsersDTO usersDTO){
        Users user = usersService.saveUser.apply(new Users(usersDTO));
        log.info("User details ---> {}", user);
        return "successful-register";
    }


    @PostMapping("/login")
    public String loginUser(@ModelAttribute UsersDTO usersDTO, HttpServletRequest request, Model model){
        Users user = usersService.findUserByUsername.apply(usersDTO.getUsername());
        log.info("User details ---> {}", user);
        if (usersService.verifyUserPassword
                .apply(PasswordDTO.builder()
                        .password(usersDTO.getPassword())
                        .hashPassword(user.getPassword())
                        .build())){
            HttpSession session = request.getSession();
            session.setAttribute("userID", user.getId());
            return "redirect:/products/all";
        }
        return "redirect:/user/login";
    }

    @PostMapping("/admin-sign-up")
   public String signAdminUp(@ModelAttribute UsersDTO usersDTO){
        Users users = usersService.saveUser.apply(new Users(usersDTO));
       System.out.println("user details-----> {}"+ users);
       return "admin-successful-register";
   }

   @GetMapping("/admin-sign-up")
   public String signUpAdminPage(Model model){
        model.addAttribute("user", new UsersDTO());
        return "admin-signup";
   }

   @GetMapping
   public ModelAndView adminLogInPage(){
        return new ModelAndView("admin-login")
                .addObject("user", new UsersDTO());
   }

   @PostMapping
   public String loginAdminUser(@ModelAttribute UsersDTO usersDTO, HttpServletRequest request, Model model) {
       Users users = usersService.findUserByAdminUsername.apply(usersDTO.getUsername());
       log.info("admin's info --> {}" + users);
       if (usersService.verifyUserPassword.apply(PasswordDTO.builder()
               .password(usersDTO.getPassword())
               .hashPassword(users.getPassword())
               .build()) && Objects.equals(users.getRole(), "admin")) {
           HttpSession session = request.getSession(true);
           session.setAttribute("userID", users.getId());
           return "redirect:/products/admin-view";
       }
       return "redirect:/user/admin-login";
   }


    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "index";
    }

}

