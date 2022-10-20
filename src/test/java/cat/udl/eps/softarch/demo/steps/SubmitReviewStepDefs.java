package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.Review;
import cat.udl.eps.softarch.demo.domain.User;
import cat.udl.eps.softarch.demo.repository.ReviewRepository;
import cat.udl.eps.softarch.demo.repository.UserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.junit.Assert;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SubmitReviewStepDefs {

    private StepDefs stepDefs;
    private ReviewRepository reviewRepository;
    private UserRepository userRepository;
    String newUri;
    public static String id;

    public SubmitReviewStepDefs(StepDefs stepDefs, ReviewRepository reviewRepository, UserRepository userRepository){
        this.stepDefs = stepDefs;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }


    @When("The buyer submits a new review with username {string}, number of stars {int} and message {string} to a seller {string}")
    public void theBuyerSubmitsANewReviewWithUsernameNumberOfStarsAndMessageToASeller(String author, int nStars, String message, String about) throws Throwable{
        Review review = new Review();
        review.setStars(nStars);
        review.setMessage(message);

        List<User> a = userRepository.findByUsernameContaining(author);

        review.setAuthor(a.get(0));

        List<User> users = this.userRepository.findByUsernameContaining(about);

        if(users.size() != 0)
            review.setAbout(users.get(0));

        stepDefs.result = stepDefs.mockMvc.perform(
                        post("/reviews")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(stepDefs.mapper.writeValueAsString(review))
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        newUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
    }

    @And("It has been submitted a review by buyer with username {string}")
    public void itHasBeenSubmittedAReviewByBuyerWithUsername(String author) throws Throwable {
        id = stepDefs.result.andReturn().getResponse().getHeader("Location");
        assert id != null;
        stepDefs.result = stepDefs.mockMvc.perform(
                        get(id)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isOk());

        JSONObject response = new JSONObject(stepDefs.result.andReturn().getResponse().getContentAsString());
        String authorByHref = response.getJSONObject("_links").getJSONObject("author").getString("href");

        assertProvidedByEqualsToExpectedUser(authorByHref, author);

    }

    public void assertProvidedByEqualsToExpectedUser(String authorByHref, String author) throws Throwable{
        stepDefs.mockMvc.perform(
                        get(authorByHref)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.id", is(author)));
    }

    @And("A new review has not been created")
    public void aNewReviewHasNotBeenCreated() {
        Assert.assertEquals(0, reviewRepository.count());
    }

    @And("There is a review with id {int}, number of stars {int} and message {string} from user {string} to user {string}")
    public void thereIsAReviewWithIdNumberOfStarsAndMessageFromUserToUser(int id, int nStars, String msg, String author, String about) throws Throwable {
        if(!reviewRepository.existsById(id))
        {
            Review review = new Review();
            review.setId(id);
            review.setStars(nStars);
            review.setMessage(msg);

            List<User> authors = userRepository.findByUsernameContaining(author);
            if(authors.size() != 0)
                review.setAuthor(authors.get(0));

            List<User> abouts = this.userRepository.findByUsernameContaining(about);
            if(abouts.size() != 0)
                review.setAbout(abouts.get(0));

            reviewRepository.save(review);
        }
    }











}
