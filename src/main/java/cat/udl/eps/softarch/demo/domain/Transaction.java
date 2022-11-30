package cat.udl.eps.softarch.demo.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Transaction extends UriEntity<Long> {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    private ZonedDateTime creationDate;

    private BigDecimal price;

    public enum StatusTypes {INITIALIZED, IN_PROGRESS, CLOSED, CANCELED}
    private StatusTypes status;

    @Override
    public Long getId() {
        return id;
    }

    @ManyToOne
    @NotNull
    @JsonIdentityReference(alwaysAsId = true)
    private User buyer;

    @ManyToOne
    @NotNull
    @JsonIdentityReference(alwaysAsId = true)

    private User seller;

    @OneToOne
    @NotNull
    @JsonIdentityReference(alwaysAsId = true)
    private Announcement announcementAbout;
}
