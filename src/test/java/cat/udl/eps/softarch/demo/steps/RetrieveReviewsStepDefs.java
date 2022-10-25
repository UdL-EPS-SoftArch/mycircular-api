package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.Review;
import cat.udl.eps.softarch.demo.domain.User;
import cat.udl.eps.softarch.demo.repository.ReviewRepository;
import cat.udl.eps.softarch.demo.repository.UserRepository;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

import org.springframework.http.MediaType;


import java.util.List;

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
