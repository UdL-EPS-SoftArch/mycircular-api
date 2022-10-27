package cat.udl.eps.softarch.demo.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.ZonedDateTime;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Offer extends Announcement {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;

    private ZonedDateTime dateTime;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIdentityReference(alwaysAsId = true)
    private User offerer;

}
