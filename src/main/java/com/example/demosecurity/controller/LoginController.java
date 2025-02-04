package org.example.c08security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @GetMapping("/login")
    public ModelAndView showLoginForm(@RequestParam(value = "error", required = false) String error,
                                      @RequestParam(value = "logout", required = false) String logout) {
        ModelAndView mav = new ModelAndView("login");
        if (error != null) {
            mav.addObject("errorMessage", "Sai tài khoản hoặc mật khẩu!");
        }
        if (logout != null) {
            mav.addObject("successMessage", "Bạn đã đăng xuất thành công!");
        }
        return mav;
    }
}

