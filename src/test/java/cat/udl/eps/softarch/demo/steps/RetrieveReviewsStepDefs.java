package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.repository.ReviewRepository;
import cat.udl.eps.softarch.demo.repository.UserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class RetrieveReviewsStepDefs {

    private StepDefs stepDefs;
    private ReviewRepository reviewRepository;
    private UserRepository userRepository;
    String newUri;
    public static String id;

    public RetrieveReviewsStepDefs(StepDefs stepDefs, ReviewRepository reviewRepository, UserRepository userRepository){
        this.stepDefs = stepDefs;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }
    @When("I list all the reviews")
    public void iListAllTheReviews() throws Throwable{
        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/reviews")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }


    @When("I list the review with id {int}")
    public void iListTheReviewWithId(int id) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/reviews/" + id)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }



    @When("I list all reviews of user {string}")
    public void iListAllReviewsOfUser(String about) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/reviews/{about}", about)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }
}
