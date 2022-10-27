package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.Review;
import cat.udl.eps.softarch.demo.domain.User;
import cat.udl.eps.softarch.demo.repository.ReviewRepository;
import cat.udl.eps.softarch.demo.repository.UserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class DeleteReviewStepDefs {

    private StepDefs stepDefs;
    private ReviewRepository reviewRepository;
    private UserRepository userRepository;
    String newUri;
    public static String id;

    public DeleteReviewStepDefs(StepDefs stepDefs, ReviewRepository reviewRepository, UserRepository userRepository){
        this.stepDefs = stepDefs;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }
    @When("I delete the last created rating")
    public void iDeleteTheLastCreatedRating() throws Throwable{
        stepDefs.result = stepDefs.mockMvc.perform(
                        delete(newUri)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("The rating was deleted")
    public void theRatingWasDeleted() {
        long totalRatings = reviewRepository.count();
        assert totalRatings == 0;
    }

    @And("I submit a new review with username {string}, number of stars {int} and message {string} to a seller {string}")
    public void iSubmitANewReviewWithUsernameNumberOfStarsAndMessageToASeller(String author, int nStars, String message, String about) throws Throwable{
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

    @When("I delete a review with id {int}")
    public void iDeleteAReviewWithId(int id) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(delete("/review/{id}", id).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("The rating with id {int} was not deleted")
    public void theRatingWithIdWasNotDeleted(int id) {
        boolean existsReview = reviewRepository.existsById(id);
        assert !existsReview;
    }

}
