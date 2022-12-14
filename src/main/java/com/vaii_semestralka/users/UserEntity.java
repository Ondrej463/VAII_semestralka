package com.vaii_semestralka.users;

import com.vaii_semestralka.LoggedInUser;
import com.vaii_semestralka.converter.DateTimeConverter;
import com.vaii_semestralka.tip.TipEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @Getter @Setter private String email;
    @Getter @Setter private String first_name;
    @Getter @Setter private String last_name;
    private Date born_date;
    @Getter @Setter private String passwd;
    @Getter @Setter private String adress;
    @Getter @Setter private int credit;

    @OneToMany(mappedBy = "tipPrimaryKeys.userEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Getter private List<TipEntity> tips;

    public UserEntity() {
        this.tips = new ArrayList<>();
    }

    public String getBorn_date() {
        return this.born_date == null ? null : DateTimeConverter.formatDate(this.born_date);
    }
    public void setBorn_date(String date) {
        this.born_date = DateTimeConverter.parseDate(date);
    }
    public Role getUserRole() {
        if (email.equals("ondrejkokov@gmail.com")) {
            return Role.ADMIN;
        } else {
            return Role.USER;
        }
    }
    public String getName() {
        return this.first_name + " " + this.getLast_name();
    }

    public void addTip(TipEntity tipEntity) {
        tips.add(tipEntity);
    }
    public void delTip(TipEntity tipEntity) {
        tips.removeIf(tipEntity1 -> tipEntity1.getTipPrimaryKeys().getTippingAllEntity().getName()
                .equals(tipEntity.getTipPrimaryKeys().getTippingAllEntity().getName()));
    }
    public boolean hasTipInEvent(String nameOfEvent) {
        Optional<TipEntity> tip =  tips.stream()
                .filter(tipEntity -> tipEntity.getTipPrimaryKeys().getTippingAllEntity().getName().equals(nameOfEvent)).findFirst();
        return tip.isPresent();
    }
}
