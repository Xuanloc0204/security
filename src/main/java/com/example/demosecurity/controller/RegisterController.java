package com.example.demosecurity.controller;

import com.example.demosecurity.model.AppRole;
import com.example.demosecurity.model.AppUser;
import com.example.demosecurity.model.ROLENAME;
import com.example.demosecurity.service.role.IAppRoleService;
import com.example.demosecurity.service.user.IAppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private IAppUserService appUserService;

    @Autowired
    private IAppRoleService appRoleService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @ModelAttribute
    public Set<AppRole> getAppUser() {
        AppRole appRole = appRoleService.findByName(ROLENAME.ROLE_USER.toString());
        Set<AppRole> appRoleSet = new HashSet<>();
        appRoleSet.add(appRole);
        return appRoleSet;
    }


    @GetMapping("")
    public String register(Model model){
        model.addAttribute("user", new AppUser());
        return "register";
    }

//    passwordEncoder
@PostMapping
public String register(AppUser user, Model model){
    // Kiểm tra xem tên người dùng đã tồn tại chưa
    if (appUserService.existsByUsername(user.getUsername())) {
        // Nếu đã tồn tại, thêm thông báo lỗi vào model và trả về lại trang đăng ký
        model.addAttribute("error", "Username already exists!");
        return "register";
    }

    // Tạo role mặc định khi đăng ký là user
    user.setRoll(getAppUser());

    // Mã hóa mật khẩu
    String newp = passwordEncoder.encode(user.getPassword());
    user.setPassword(newp);

    // Lưu người dùng vào cơ sở dữ liệu
    appUserService.save(user);
    model.addAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
    return "redirect:/login?register=success";
}


//    @PostMapping("")
//    public String register(AppUser user) {
//        AppRole appRole = appRoleService.findById(2L).get();
//        Set<AppRole> appRoleSet = new HashSet<>();
//        appRoleSet.add(appRole);
//        user.setRoll(appRoleSet);
//        appUserService.save(user);
//
//        return "redirect:/login";
//    }
}
