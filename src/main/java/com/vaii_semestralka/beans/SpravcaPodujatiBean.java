package com.vaii_semestralka.beans;
import com.vaii_semestralka.tipping_all.TippingAllEntity;
import com.vaii_semestralka.tipping_all.TippingAllService;
import com.vaii_semestralka.koeficienty.*;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;
import java.util.List;


@Configuration
public class SpravcaPodujatiBean {
    @Autowired private TippingAllService tippingAllService;
    @Autowired private KoeficientService koeficientService;
    @Getter private String koefErrorMessage;
    @Getter private Operacia operacia;
    @Getter private TippingAllEntity tipping;

    @Getter private KoeficientList koeficientList;
    public void save(TippingAllEntity tippingAllEntity) {
        if (this.operacia == Operacia.EDIT) {
            tippingAllEntity.setName(this.tipping.getName());
            this.koeficientService.deleteAllByName(tippingAllEntity.getName());
        }
        this.tippingAllService.save(tippingAllEntity);
        List<Koeficient> koeficients = this.koeficientList.getKoeficients();
        for (int i = 0; i < koeficients.size(); i++) {
            KoeficientPrimaryKey koeficientPrimaryKey = new KoeficientPrimaryKey(tippingAllEntity, koeficients.get(i).getOd_());
            KoeficientEntity koeficientEntity = new KoeficientEntity(koeficientPrimaryKey,
                    koeficients.get(i).getDo_(), koeficients.get(i).getKoef());
            this.koeficientService.save(koeficientEntity);
        }
    }
    public String getButtonText() {
        return this.operacia == Operacia.EDIT ? "Upraviť" : "Vytvoriť";
    }
    public void init(Model model) {
        if (model.containsAttribute("name")) {
            if (model.getAttribute("name") == null) {
                this.operacia = Operacia.NEW;
                this.tipping = new TippingAllEntity();
                this.koeficientList = new KoeficientList();
                this.koeficientList.addNewKoeficient();
                model.addAttribute("name", "novy");
            } else {
                this.koeficientList = new KoeficientList();
                this.operacia = Operacia.EDIT;
                this.tipping = tippingAllService.findById(model.getAttribute("name").toString());
                List<KoeficientEntity> koefs = this.koeficientService.findAllKoefsInOrder(this.tipping.getName());
                for (KoeficientEntity koeficientEntity : koefs) {
                    this.koeficientList.addKoeficient(new Koeficient(koeficientEntity.getKoeficientPrimaryKey().getOd_(),
                            koeficientEntity.getDo_(), koeficientEntity.getKoef_()));
                }
            }
        }
    }
    public void addNewKoef() {
        this.koeficientList.addNewKoeficient();
    }
    public void resetKoeficients() {
        this.koeficientList.removeKoeficients();
    }
    public Object getStyle() {
        return this.operacia == Operacia.EDIT ? "disabled=disabled" : "";
    }

    public Object jeNovy() {
        return this.operacia == Operacia.NEW;
    }
    public boolean suKoeficienty() {
        return this.koeficientList != null && !this.koeficientList.getKoeficients().isEmpty();
    }

    public void validateKoefList() {
        this.koefErrorMessage = this.koeficientList.zvalidujKoeficienty();
    }
}

enum Operacia {
    NEW,
    EDIT;
}