package cat.udl.eps.softarch.demo.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Transaction extends UriEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private ZonedDateTime creationDate;
    private BigDecimal price;

    public enum StatusTypes {INITIALIZED, IN_PROGRESS, CLOSED, CANCELED}
    private StatusTypes status;

    @Override
    public Long getId() {
        return id;
    }
    /*
    @ManyToOne
    private User buyer;

    @ManyToOne
    private User seller;

    @OneToOne
    private Announcement announcementAbout;
     */
}
