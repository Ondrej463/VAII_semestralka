package com.vaii_semestralka.beans;

import com.vaii_semestralka.LoggedInUser;
import com.vaii_semestralka.converter.DateTimeConverter;
import com.vaii_semestralka.tip.TipEntity;
import com.vaii_semestralka.tip.TipPrimaryKey;
import com.vaii_semestralka.tip.TipService;
import com.vaii_semestralka.tipping_all.TippingAllEntity;
import com.vaii_semestralka.tipping_all.TippingAllService;
import com.vaii_semestralka.users.UserEntity;
import com.vaii_semestralka.users.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
public class TipBean {
    @Autowired private TipService tipService;
    @Autowired private TippingAllService tippingAllService;

    @Autowired private UserService userService;
    @Getter @Setter TippingAllEntity tippingAllEntity;
    private TipPrimaryKey tipPrimaryKeys;
    @Getter @Setter private TipEntity tipEntity;
    public void init(String meno) {
        this.tipEntity = new TipEntity();
        this.tippingAllEntity = this.tippingAllService.findById(meno);
        this.tipPrimaryKeys = new TipPrimaryKey(tippingAllService.findById(meno), LoggedInUser.getActualUser());
    }

    public void save(TipEntity tipEntity) {
        tipEntity.setTipPrimaryKeys(this.tipPrimaryKeys);
        tipEntity.setWhen(DateTimeConverter.formatDateTime(LocalDateTime.now()));
        LoggedInUser.getActualUser().addTip(tipEntity);
        LoggedInUser.getActualUser().setCredit(LoggedInUser.getActualUser().getCredit() - tipEntity.getVklad());

        UserEntity user = this.userService.getByEmail(LoggedInUser.getActualUser().getEmail());
        user.setCredit(LoggedInUser.getActualUser().getCredit());
        this.userService.save(user);
        this.tipService.save(tipEntity);
    }

    public void delete() {
        TipEntity tipEntity = tipService.findById(this.tipPrimaryKeys);
        if (tipEntity != null) {
            this.tipService.delete(tipEntity);
            LoggedInUser.getActualUser().delTip(tipEntity);
        }
    }

    public String validate(TipEntity tipEntity) {
        if (tipEntity.getVklad() > LoggedInUser.getActualUser().getCredit()) {
            return "Nemáte dostatok finančných prostriedkov na účte!";
        }
        return "";
    }
}
