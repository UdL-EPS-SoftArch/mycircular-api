package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.Review;
import cat.udl.eps.softarch.demo.domain.User;
import cat.udl.eps.softarch.demo.repository.ReviewRepository;
import cat.udl.eps.softarch.demo.repository.UserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import net.bytebuddy.implementation.bytecode.Throw;
import org.json.JSONObject;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class ModifyReviewStepDefs {

    private StepDefs stepDefs;
    private ReviewRepository reviewRepository;
    private UserRepository userRepository;
    String newUri;
    public static String id;

    public ModifyReviewStepDefs(StepDefs stepDefs, ReviewRepository reviewRepository, UserRepository userRepository){
        this.stepDefs = stepDefs;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }
    @When("The buyer modifies the last created review with changing number of stars to {int}")
    public void theBuyerModifiesTheLastCreatedReviewWithChangingNumberOfStarsTo(int nStars) throws Throwable{

        JSONObject modifyStars = new JSONObject();
        modifyStars.put("stars", nStars);

        stepDefs.result = stepDefs.mockMvc.perform(patch(newUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(modifyStars.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("I submit new review with username {string}, number of stars {int} and message {string} to a seller {string}")
    public void iSubmitNewReviewWithUsernameNumberOfStarsAndMessageToASeller(String author, int nStars, String message, String about) throws Throwable {
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

    @When("The buyer modifies the last created review with changing message to {string}")
    public void theBuyerModifiesTheLastCreatedReviewWithChangingMessageTo(String message) throws Throwable {

        JSONObject modifyMessage = new JSONObject();
        modifyMessage.put("message", message);

        stepDefs.result = stepDefs.mockMvc.perform(patch(newUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(modifyMessage.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @When("The buyer modifies the last created review with changing message to {string} and number of stars to {int}")
    public void theBuyerModifiesTheLastCreatedReviewWithChangingMessageToAndNumberOfStarsTo(String message, int nStars) throws Throwable {

        JSONObject modifyReview = new JSONObject();
        modifyReview.put("message", message);
        modifyReview.put("stars", nStars);

        stepDefs.result = stepDefs.mockMvc.perform(patch(newUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(modifyReview.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }


    @When("I modify a review with id {int}")
    public void iModifyAReviewWithId(int reviewId) throws Throwable {
        Review review = new Review();
        review.setId(reviewId);

        stepDefs.result = stepDefs.mockMvc.perform(patch("/review/{id}", id).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }
}
