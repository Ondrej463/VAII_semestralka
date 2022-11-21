package com.vaii_semestralka.beans;

import com.vaii_semestralka.LoggedInUser;
import com.vaii_semestralka.tipping_all.TippingAllEntity;
import com.vaii_semestralka.tipping_all.TippingAllService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;

@Configuration
public class SpravcaPodujatiBean {
    @Autowired private TippingAllService tippingAllService;

    @Getter private Operacia operacia;
    @Getter private TippingAllEntity tipping;

    public void save(TippingAllEntity tippingAllEntity) {
        if (this.operacia == Operacia.EDIT) {
            tippingAllEntity.setName(this.tipping.getName());
        }
        this.tippingAllService.save(tippingAllEntity);
    }
    public String getButtonText() {
        return this.operacia == Operacia.EDIT ? "Upraviť" : "Vytvoriť";
    }
    public void init(Model model) {
        if (model.getAttribute("name") != null) {
            this.operacia = Operacia.EDIT;
            this.tipping = tippingAllService.findById(model.getAttribute("name").toString());
        } else {
            this.operacia = Operacia.NEW;
            this.tipping = new TippingAllEntity();
        }
    }

    public Object getStyle() {
        return this.operacia == Operacia.EDIT ? "disabled=disabled" : "";
    }

    public Object jeNovy() {
        return this.operacia == Operacia.NEW;
    }
}

enum Operacia {
    NEW,
    EDIT;
}