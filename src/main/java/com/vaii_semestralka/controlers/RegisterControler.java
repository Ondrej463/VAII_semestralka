package com.vaii_semestralka.controlers;

import com.vaii_semestralka.beans.RegisterBean;
import com.vaii_semestralka.users.Hash;
import com.vaii_semestralka.users.UserEntity;
import com.vaii_semestralka.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Controller
public class RegisterControler {
    @Autowired private RegisterBean registerBean;

    @GetMapping("/registracia")
    public String getRegistracia(Model model) {
        model.addAttribute("user", new UserEntity());
        return "register";
    }

    @PostMapping(value = "/register")
    public String postRegistracia(@ModelAttribute("user") UserEntity user,
                                  @RequestParam(name = "repeatPassword", required = false) String repeatPassword,
                                  Model model) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (!this.registerBean.validatePasswords(user.getPasswd(), repeatPassword)
            || !registerBean.validate(user)) {
            this.registerBean.addErrorMessagesToModel(model);
            this.registerBean.refreshPasswords(model);
            return "register";
        } else {
            user.setSalt(Hash.newSalt());
            user.setPasswd(Hash.hashPassword(user.getPasswd(), user.getSalt()));
            user.setCredit(50);
            this.registerBean.save(user);
            return "redirect:/";
        }
    }
}
