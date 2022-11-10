package cat.udl.eps.softarch.demo.domain;

import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class ServiceOffer extends Offer {

    private Boolean availability;
    private int durationInHours;

}
