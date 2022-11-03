package cat.udl.eps.softarch.demo.domain;

import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class CounterOffer extends Offer {

    private BigDecimal counterOfferPrice;

}
