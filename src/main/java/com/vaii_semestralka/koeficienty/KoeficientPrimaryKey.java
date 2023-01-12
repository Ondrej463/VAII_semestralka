package com.vaii_semestralka.koeficienty;
import com.vaii_semestralka.tipping_all.TippingAllEntity;
import lombok.Getter;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class KoeficientPrimaryKey implements Serializable {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event", referencedColumnName = "name")
    @Getter
    private TippingAllEntity tippingAllEntity;

    @Getter
    private int od_;

    public KoeficientPrimaryKey(TippingAllEntity tippingAllEntity, int od) {
        this.tippingAllEntity = tippingAllEntity;
        this.od_= od;
    }

    public KoeficientPrimaryKey() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KoeficientPrimaryKey accountId = (KoeficientPrimaryKey) o;
        return this.od_ == accountId.od_ &&
                this.tippingAllEntity.getName().equals(accountId.tippingAllEntity.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(tippingAllEntity, od_);
    }
}
