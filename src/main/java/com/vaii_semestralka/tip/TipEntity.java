package com.vaii_semestralka.tip;

import com.vaii_semestralka.converter.DateTimeConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "vyplnene_tipy")
public class TipEntity implements Serializable {
    @EmbeddedId
    @Getter @Setter private TipPrimaryKeys tipPrimaryKeys;
    @Column(name="input_time")
    private LocalDateTime when;
    @Getter @Setter private int first_number;
    @Getter @Setter private int second_number;
    @Getter @Setter private int third_number;
    @Getter @Setter private int fourth_number;
    @Getter @Setter private int fifth_number;

    public TipEntity() {

    }
    public String getWhen() {
        return this.when == null ? null : DateTimeConverter.formatDateTime(this.when);
    }
    public void setWhen(String date) {
        this.when = DateTimeConverter.parseDateTime(date);
    }
}
