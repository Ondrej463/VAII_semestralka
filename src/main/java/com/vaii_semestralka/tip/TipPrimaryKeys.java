package com.vaii_semestralka.tip;

import com.vaii_semestralka.tipping_all.TippingAllEntity;
import com.vaii_semestralka.users.UserEntity;
import lombok.Getter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TipPrimaryKeys implements Serializable {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event", referencedColumnName = "name")
    @Getter
    private TippingAllEntity tippingAllEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "who", referencedColumnName = "email")
    @Getter
    private UserEntity userEntity;

    public TipPrimaryKeys(TippingAllEntity tippingAllEntity, UserEntity userEntity) {
        this.tippingAllEntity = tippingAllEntity;
        this.userEntity = userEntity;
    }

    public TipPrimaryKeys() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipPrimaryKeys accountId = (TipPrimaryKeys) o;
        return this.userEntity.getEmail().equals(accountId.userEntity.getEmail()) &&
                this.tippingAllEntity.getName().equals(accountId.tippingAllEntity.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(tippingAllEntity, userEntity);
    }
}

