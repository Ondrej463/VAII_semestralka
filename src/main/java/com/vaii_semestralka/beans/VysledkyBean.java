package com.vaii_semestralka.beans;
import com.vaii_semestralka.LoggedInUser;
import com.vaii_semestralka.koeficienty.KoeficientEntity;
import com.vaii_semestralka.tip.TipEntity;
import com.vaii_semestralka.tip.TipPrimaryKey;
import com.vaii_semestralka.tip.TipService;
import com.vaii_semestralka.tipping_all.TippingAllEntity;
import com.vaii_semestralka.tipping_all.TippingAllService;
import com.vaii_semestralka.users.UserEntity;
import com.vaii_semestralka.users.UserService;
import com.vaii_semestralka.vysledky.VysledkyEntity;
import com.vaii_semestralka.vysledky.VysledkyService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class VysledkyBean {
    @Autowired private VysledkyService vysledkyService;
    @Autowired private TippingAllService tippingAllService;

    @Autowired private UserService userService;
    @Autowired private TipService tipService;
    @Getter private TippingAllEntity tippingAllEntity;
    @Getter private boolean jePlatny;
    @Getter private List<VysledkyEntity> vysledky;

    @Getter private TipEntity tipPrihlasenehoPouzivatela;

    private void vyhodnotPodujatie(TippingAllEntity tippingAllEntity) {
        List<VysledkyEntity> vysledky = new ArrayList<>();
        for (TipEntity tipEntity : tippingAllEntity.getTips()) {

            vysledky.add(new VysledkyEntity(new TipPrimaryKey(tippingAllEntity, tipEntity.getTipPrimaryKeys().getUserEntity()),
                    VysledkyBean.getDiff(tippingAllEntity, tipEntity), tipEntity.getVklad()));
        }
        List<VysledkyEntity> utriedeneVysledky = vysledky.stream().sorted(Comparator.comparingInt(VysledkyEntity::getRozdiel)).collect(Collectors.toList());
        List<KoeficientEntity> utriedeneKoeficienty = tippingAllEntity.getKoeficientsInOrder();

        for (int i = 0; i < utriedeneVysledky.size(); i++) {
            utriedeneVysledky.get(i).setPoradie(i + 1);
            for (int j = 0; j < utriedeneKoeficienty.size(); j++) {
                if (j == utriedeneKoeficienty.size() - 1) {
                    utriedeneVysledky.get(i).setKoeficient(utriedeneKoeficienty.get(j).getKoef_());
                } else {
                    if ((i + 1) >= utriedeneKoeficienty.get(j).getKoeficientPrimaryKey().getOd_()
                        && (i + 1) <= utriedeneKoeficienty.get(j).getDo_()) {
                        utriedeneVysledky.get(i).setKoeficient(utriedeneKoeficienty.get(j).getKoef_());
                        break;
                    }
                }
            }
            utriedeneVysledky.get(i).setZisk(Math.round(utriedeneVysledky.get(i).getZisk()
                    * utriedeneVysledky.get(i).getKoeficient() * 100) / 100.0);
        }
        this.vysledkyService.saveAll(utriedeneVysledky);
    }

    private static int getDiff(TippingAllEntity tippingAllEntity, TipEntity tipEntity) {
        List<Integer> absRozdiel = List.of(Math.abs(tippingAllEntity.getFirst_number() - tipEntity.getFirst_number()),
                Math.abs(tippingAllEntity.getSecond_number() - tipEntity.getSecond_number()),
                Math.abs(tippingAllEntity.getThird_number() - tipEntity.getThird_number()),
                Math.abs(tippingAllEntity.getFourth_number() - tipEntity.getFourth_number()),
                Math.abs(tippingAllEntity.getFifth_number() - tipEntity.getFifth_number()));
        int rozdiel = 0;
        for (int i = 0; i < tippingAllEntity.getDruh().getPocet_cislic(); i++) {
            rozdiel += absRozdiel.get(i);
        }
        return rozdiel;
    }

    public void init(String nazov) {
        this.tippingAllEntity = this.tippingAllService.findById(nazov);
        this.tipPrihlasenehoPouzivatela = this.tipService.findById(new TipPrimaryKey(this.tippingAllEntity, LoggedInUser.getActualUser()));
        if (this.tippingAllEntity.maDostatokTipov()) {
            this.jePlatny = true;
            if (!this.vysledkyService.maVysledky(this.tippingAllEntity)) {
                this.vyhodnotPodujatie(this.tippingAllEntity);
            }
            this.vysledky = this.vysledkyService.getVysledkyPodujatiaUsporiadanePodlaPoradia(this.tippingAllEntity.getName());
        } else {
            this.jePlatny = false;
            this.vysledky = new ArrayList<>();
        }

        if (this.tipPrihlasenehoPouzivatela != null && this.tipPrihlasenehoPouzivatela.getVysledkyEntity() == null) {
            this.tipPrihlasenehoPouzivatela.setVysledkyEntity(this.vysledkyService.findById(this.tipPrihlasenehoPouzivatela.getTipPrimaryKeys()));
        }
    }
    public String getTipy(VysledkyEntity vysledkyEntity) {
        TipEntity tipEntity = this.getTipEntity(vysledkyEntity);
        String vypis = "";
        int[] cisla = {tipEntity.getFirst_number(), tipEntity.getSecond_number(), tipEntity.getThird_number(), tipEntity.getFourth_number(), tipEntity.getFifth_number()};
        for (int i = 0; i < cisla.length; i++) {
            if (i < this.tippingAllEntity.getDruh().getPocet_cislic()) {
                vypis = vypis.concat(cisla[i] + " ");
            }
        }
        return vypis.substring(0, vypis.length() - 1);
    }

    public String getVklad(VysledkyEntity vysledkyEntity) {
        return this.getTipEntity(vysledkyEntity).getVkladScreenFormat();
    }
    public TipEntity getTipEntity(VysledkyEntity vysledkyEntity) {
        if (vysledkyEntity.getTipEntity() == null) {
            return this.tipService.findById(new TipPrimaryKey(vysledkyEntity.getTipPrimaryKey().getTippingAllEntity(),
                    vysledkyEntity.getTipPrimaryKey().getUserEntity()));
        }
        return vysledkyEntity.getTipEntity();
    }

    public String getFarbaPozadia() {
        if (this.tipPrihlasenehoPouzivatela.getVysledkyEntity().getZisk() > 0) {
            return "green";
        } else {
            return "yellow";
        }
    }

    public void pridajPeniaze(double peniaze) {
        LoggedInUser.getActualUser().setCredit(LoggedInUser.getActualUser().getCredit() + peniaze);
        UserEntity user = this.userService.getByEmail(LoggedInUser.getActualUser().getEmail());
        user.setCredit(LoggedInUser.getActualUser().getCredit());
        this.userService.save(user);
        TipEntity tip = this.tipService.findById(this.tipPrihlasenehoPouzivatela.getTipPrimaryKeys());
        tip.setVybratePeniaze(true);
        this.tipService.save(tip);
    }
}

