package cat.udl.eps.softarch.demo.domain;


import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.ZonedDateTime;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
//@Table(name = "Request")

public class Request extends Announcement{

    private ZonedDateTime dateTime;

//    @NotEmpty
    @ManyToOne()
    @JsonIdentityReference(alwaysAsId = true)
    private User requester;
}
