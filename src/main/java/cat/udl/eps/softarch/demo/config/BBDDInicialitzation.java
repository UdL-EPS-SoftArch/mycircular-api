package cat.udl.eps.softarch.demo.config;

import cat.udl.eps.softarch.demo.domain.Announcement;
import cat.udl.eps.softarch.demo.domain.Offer;
import cat.udl.eps.softarch.demo.domain.ProductOffer;
import cat.udl.eps.softarch.demo.repository.AnnouncementRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Configuration
@Profile("test")
public class BBDDInicialitzation {
    final AnnouncementRepository announcementRepository;
    public BBDDInicialitzation(AnnouncementRepository announcementRepository){
        this.announcementRepository=announcementRepository;
    }
    @PostConstruct
    public void inicializeDatabase(){
        ProductOffer ann = new ProductOffer();
        ann.setName("prod1");
        ann.setDescription("blablablablablablabla");
        ann.setPrice(BigDecimal.TEN);
        ann = announcementRepository.save(ann);

    }
}
