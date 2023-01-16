package com.vaii_semestralka.controlers;

import com.vaii_semestralka.LoggedInUser;
import com.vaii_semestralka.beans.LoginBean;
import com.vaii_semestralka.users.UserEntity;
import com.vaii_semestralka.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Controller
public class LoginControler {
    @Autowired private LoginBean loginBean;

    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    @PostMapping("/")
    public String postIndex(@RequestParam(value="email", required = false) String email,
                            @RequestParam(value="password", required = false) String password,
                            Model model) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (this.loginBean.validate(email, password)) {
            loginBean.login(email);
            return "redirect:/informacie";
        } else {
            this.loginBean.addErrorMessagesToModel(model);
            return "index";
        }
    }
}
