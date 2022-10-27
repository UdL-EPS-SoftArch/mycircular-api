package cat.udl.eps.softarch.demo.domain;

import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductOffer extends Offer {

    private String manufacturer;
    private String brand;
    private String productCode;

}
