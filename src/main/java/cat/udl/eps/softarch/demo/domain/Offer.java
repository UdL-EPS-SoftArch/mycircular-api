package cat.udl.eps.softarch.demo.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.ZonedDateTime;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Offer extends Announcement {

    private ZonedDateTime dateTime;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private User offerer;

}
