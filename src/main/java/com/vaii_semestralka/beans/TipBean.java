package com.vaii_semestralka.beans;

import com.vaii_semestralka.LoggedInUser;
import com.vaii_semestralka.converter.DateTimeConverter;
import com.vaii_semestralka.tip.TipEntity;
import com.vaii_semestralka.tip.TipPrimaryKeys;
import com.vaii_semestralka.tip.TipService;
import com.vaii_semestralka.tipping_all.TippingAllEntity;
import com.vaii_semestralka.tipping_all.TippingAllService;
import lombok.Getter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Configuration
public class TipBean {
    @Autowired private TipService tipService;
    @Autowired private TippingAllService tippingAllService;
    private TipPrimaryKeys tipPrimaryKeys;

    public TipEntity getTip(String name) {
        this.tipPrimaryKeys = new TipPrimaryKeys(tippingAllService.findById(name), LoggedInUser.getActualUser());
        TipEntity tipEntity = tipService.findById(this.tipPrimaryKeys);
        return Objects.requireNonNullElseGet(tipEntity, TipEntity::new);
    }

    public TippingAllEntity getTippingAllEntity(String name) {
        return tippingAllService.findById(name);
    }
    public void save(TipEntity tipEntity) {
        tipEntity.setTipPrimaryKeys(this.tipPrimaryKeys);
        tipEntity.setWhen(DateTimeConverter.formatDateTime(LocalDateTime.now()));
        LoggedInUser.getActualUser().addTip(tipEntity);
        this.tipService.save(tipEntity);
    }

    public void delete() {
        TipEntity tipEntity = tipService.findById(this.tipPrimaryKeys);
        if (tipEntity != null) {
            this.tipService.delete(tipEntity);
            LoggedInUser.getActualUser().delTip(tipEntity);
        }
    }
}
