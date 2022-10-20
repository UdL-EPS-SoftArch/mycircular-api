package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.Announcement;
import cat.udl.eps.softarch.demo.domain.Message;
import cat.udl.eps.softarch.demo.domain.User;
import cat.udl.eps.softarch.demo.repository.AnnouncementRepository;
import cat.udl.eps.softarch.demo.repository.MessageRepository;
import cat.udl.eps.softarch.demo.repository.UserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import java.time.ZonedDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


public class MessageStepDefs {

    @Autowired
    private StepDefs stepDefs;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;


    @And("I don't have any messages")
    public void iDonTHaveAnyMessages() {
        long messages = messageRepository.count();
        assert messages == 0;
    }

    @When("I send a message with date {string}, text {string} and author {string}")
    public void iSendAMessageWithDateTextAndAuthor(String date, String text, String author) throws Exception{
        ZonedDateTime dated = ZonedDateTime.parse(date);
        Message message = new Message();
        List<User> authors = userRepository.findByUsernameContaining(author);

        //message.setId(ident);
        message.setWhen(dated);
        message.setText(text);
        message.setAuthor(authors.get(0));

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stepDefs.mapper.writeValueAsString(message))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())).andDo(print());
    }
}
