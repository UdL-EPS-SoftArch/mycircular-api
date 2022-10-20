package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.Announcement;
import io.cucumber.java.en.And;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class CreateAnnouncementStepDefs {

    String newResourceUri;
    @Autowired
    private StepDefs stepDefs;



    @And("There is an announcement with name {string}, description {string}, price {string}")
    public void thereIsAnAnnouncementWithNameDescriptionPrice(String name, String description, String strPrice) throws Exception {
        Announcement announcement = new Announcement();
        announcement.setName(name);
        announcement.setDescription(description);
        BigDecimal price = new BigDecimal(strPrice);
        announcement.setPrice(price);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/announcements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stepDefs.mapper.writeValueAsString(announcement))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
    }
}
