package cat.udl.eps.softarch.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Collection;

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

    public void setIdentifyer(Long newIdentifyer){
        identifyer = newIdentifyer;
    }

    public void setPrice(BigDecimal newPrice){
        price = newPrice;
    }

    public void assigntime(){
        dateTime = ZonedDateTime.now();
    }
}
