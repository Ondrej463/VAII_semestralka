package com.vaii_semestralka.koeficienty;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class KoeficientList {
    @Getter @Setter
    private List<Koeficient> koeficients;

    public void addNewKoeficient() {
        this.koeficients.add(new Koeficient());
    }
    public void addKoeficient(Koeficient koeficient) {this.koeficients.add(koeficient);}
    public void removeKoeficient() {
        this.koeficients.remove(this.koeficients.size() - 1);
    }
    public KoeficientList() {
        this.koeficients = new ArrayList<>();
    }

    public void removeKoeficients() {
        this.koeficients = new ArrayList<>();
    }

    public String zvalidujKoeficienty() {
        if (this.koeficients.isEmpty()) {
            return "Koeficienty nesmú byť nevyplnené!";
        }
        if (this.koeficients.get(0).getOd_() != 1) {
            return "Poradie musí začínať od 1!";
        }

        for (int i = 0; i < this.koeficients.size() - 1; i++) {
            if (this.koeficients.get(i).getDo_() >= this.koeficients.get(i + 1).getOd_()) {
                return "Intervaly sa prekrývajú!";
            }
            if (this.koeficients.get(i).getDo_() + 1 != this.koeficients.get(i + 1).getOd_()) {
                return "Poradie nenadväzuje bezprostredne na seba!";
            }
            if (this.koeficients.get(i).getOd_() == 0 && this.koeficients.get(i).getDo_() == 0) {
                return "Nevyplnený koeficient!";
            }
        }
        if (this.koeficients.get(this.koeficients.size() - 1).getDo_() != 0) {
            return "Posledný koeficient musí mať hodnotu prázdnu = nekonečno!";
        }
        return "";
    }
}
