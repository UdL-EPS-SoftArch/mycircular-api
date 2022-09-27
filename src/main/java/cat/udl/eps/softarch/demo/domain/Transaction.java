package cat.udl.eps.softarch.demo.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
public class Transaction extends UriEntity<Long> {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long identifyer;

    private ZonedDateTime dateTime;

    private BigDecimal price;

    public enum StatusTypes{ACTIVE,INPROGRESS,CLOSED}

    private StatusTypes status;
    /**
    @ManyToOne
    private User buyer;

    @ManyToOne
    private User seller;

    @OneToOne
    private Announcement announcementAbout;*/

    @Override
    public Long getId(){ return identifyer; }
}
