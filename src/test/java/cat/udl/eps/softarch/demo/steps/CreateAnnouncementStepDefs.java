package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.Announcement;
import cat.udl.eps.softarch.demo.repository.AnnouncementRepository;
import io.cucumber.java.en.And;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class CreateAnnouncementStepDefs {

    @Autowired
    AnnouncementRepository announcementRepository;

    String newResourceUri;

    @Autowired
    private StepDefs stepDefs;



    @And("There is an announcement with id {int}, name {string}, description {string} and price {double}")
    public void thereIsAnAnnouncementWithNameDescriptionPrice(Integer id, String name, String description, Double price) {
        Announcement announcement = new Announcement();
        announcement.setId(Long.valueOf(id));
        announcement.setName(name);
        announcement.setDescription(description);
        announcement.setPrice(BigDecimal.valueOf(price));
        announcementRepository.save(announcement);
    }
}
