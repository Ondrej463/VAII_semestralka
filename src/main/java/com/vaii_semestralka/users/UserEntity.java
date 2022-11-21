package com.vaii_semestralka.users;

import com.vaii_semestralka.converter.DateTimeConverter;
import com.vaii_semestralka.tip.TipEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

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

    @OneToMany(mappedBy = "tipPrimaryKeys.userEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Getter @Setter private Set<TipEntity> tips;

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
}
