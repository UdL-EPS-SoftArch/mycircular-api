package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.Announcement;
import cat.udl.eps.softarch.demo.repository.AnnouncementRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

public class AnnouncementStepDefs {
    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private AnnouncementRepository announcementRepository;

    private Announcement announcement;

    @Then("The announcement should be created")
    public void theAnnouncementShouldBeCreated() {
        announcement = new Announcement();

        Assert.assertNotNull(announcement);
    }

    @And("said announcement has a name {string}, description {string} and a price {string}.")
    public void saidAnnouncementHasANameDescriptionAndAPrice(String name, String description, String price)
            throws Throwable {
        announcement.setName(name);
        announcement.setDescription(description);
        announcement.setPrice(new BigDecimal(price));

        Assert.assertEquals(name, announcement.getName());
        Assert.assertEquals(description, announcement.getDescription());
        Assert.assertEquals(price, String.valueOf(announcement.getPrice()));

        stepDefs.result = stepDefs.mockMvc.perform(
                        post("/announcements")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(stepDefs.mapper.writeValueAsString(announcement))
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("After all the steps I can retrieve this announcement which should have the name {string}.")
    public void afterAllTheStepsICanRetrieveThisAnnouncementWhichShouldHaveTheName(String name) throws Throwable{
        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/announcements/{id}", "1")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(status().isOk());
    }

    @Then("I want to modify this announcement's name to {string}.")
    public void iWantToModifyThisAnnouncementSNameTo(String name) throws Throwable{
        stepDefs.result = stepDefs.mockMvc.perform(
                        patch("/announcements/{id}", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content((new JSONObject().put("name", name)).toString())
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate())
                ).andDo(print())
                .andExpect(jsonPath("$.name", is(name)));
    }

    @Then("I want to delete the announcement with id {string}.")
    public void iWantToDeleteTheAnnouncementWithId(String id) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                        delete("/announcements/{id}", id)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("I want to check that the announcement doesn't exist anymore.")
    public void iWantToCheckThatTheAnnouncementDoesnTExistAnymore() throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/announcements/{id}", "1")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Then("I shouldn't be able to create any announcement.")
    public void iShouldnTBeAbleToCreateAnyAnnouncement() throws Throwable {
        Announcement newAnnouncement = new Announcement();

        stepDefs.result = stepDefs.mockMvc.perform(
                        post("/announcements")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(stepDefs.mapper.writeValueAsString(newAnnouncement))
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Then("I shouldn't be able to modify any announcement.")
    public void iShouldnTBeAbleToModifyAnyAnnouncement() throws Throwable {
        String name = "test";

        stepDefs.result = stepDefs.mockMvc.perform(
                        patch("/announcements/{id}", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content((new JSONObject().put("name", name)).toString())
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate())
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Then("I shouldn't be able to delete any announcement.")
    public void iShouldnTBeAbleToDeleteAnyAnnouncement() throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                        delete("/announcements/{id}", "1")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
