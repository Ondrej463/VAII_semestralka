package com.vaii_semestralka.tipping_all;

public enum StavUdalosti {
    NEZACAL("Nezačal"),
    PREBIEHA("Prebieha"),
    SKONCENY("Ukončený");
    private String stav;
    StavUdalosti(String stav) {
        this.stav = stav;
    }
    public String getStav() {
        return this.stav;
    }
}
