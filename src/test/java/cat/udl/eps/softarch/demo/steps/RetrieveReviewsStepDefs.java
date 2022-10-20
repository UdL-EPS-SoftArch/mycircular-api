package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.repository.ReviewRepository;
import cat.udl.eps.softarch.demo.repository.UserRepository;

import io.cucumber.java.en.When;

import org.springframework.http.MediaType;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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

    @When("I list the review with number of stars {int}")
    public void iListTheReviewWithNumberOfStars(int nStars) throws Throwable{
        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/reviews/search/findByStars?stars={stars}", nStars)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @When("I list the review with message {string}")
    public void iListTheReviewWithMessage(String msg) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/reviews/search/findByMessage?message={message}", msg)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @When("I list all reviews for user {string}")
        public void iListAllReviewsForUser(String user) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/reviews/search/findByAbout?about={about}", "/users/" + user)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @When("I list all reviews that user {string} has already made")
    public void iListAllReviewsThatUserHasAlreadyMade(String user) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/reviews/search/findByAuthor?author={author}", "/users/" + user)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }
}
