package com.vaii_semestralka.vysledky;

import com.vaii_semestralka.koeficienty.KoeficientPrimaryKey;
import com.vaii_semestralka.tipping_all.TippingAllEntity;
import com.vaii_semestralka.users.UserEntity;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class VysledkyPrimaryKey implements Serializable {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="nazov", referencedColumnName = "name")
    @Getter private TippingAllEntity tippingAllEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="who", referencedColumnName = "email")
    @Getter private UserEntity userEntity;

    public VysledkyPrimaryKey(TippingAllEntity tippingAllEntity, UserEntity userEntity) {
        this.tippingAllEntity = tippingAllEntity;
        this.userEntity = userEntity;
    }

    public VysledkyPrimaryKey() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VysledkyPrimaryKey accountId = (VysledkyPrimaryKey) o;
        return this.tippingAllEntity.getName().equals(accountId.tippingAllEntity.getName()) &&
                this.userEntity.getEmail().equals(accountId.userEntity.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(tippingAllEntity, userEntity);
    }
}
