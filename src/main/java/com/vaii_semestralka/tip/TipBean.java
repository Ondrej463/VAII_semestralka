package com.vaii_semestralka.tip;

import com.vaii_semestralka.LoggedInUser;
import com.vaii_semestralka.converter.DateTimeConverter;
import com.vaii_semestralka.tipping_all.TippingAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.Objects;

@Configuration
public class TipBean {
    @Autowired private TipService tipService;
    @Autowired private TippingAllService tippingAllService;
    private Model model;
    private TipPrimaryKeys tipPrimaryKeys;

    TipEntity getTip() {
        TipEntity tipEntity = tipService.findById(this.tipPrimaryKeys);
        return Objects.requireNonNullElseGet(tipEntity, TipEntity::new);
    }

    public void save(TipEntity tipEntity) {
        tipEntity.setTipPrimaryKeys(this.tipPrimaryKeys);
        tipEntity.setWhen(DateTimeConverter.formatDateTime(LocalDateTime.now()));
        this.tipService.save(tipEntity);
    }

    public void setup(Model model) {
        this.model = model;
        this.tipPrimaryKeys = new TipPrimaryKeys(this.tippingAllService.findById(this.model.getAttribute("paName").toString()),
                LoggedInUser.getActualUser());
    }
}
