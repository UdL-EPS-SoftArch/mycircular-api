package cat.udl.eps.softarch.demo.domain;


import javax.persistence.*;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
