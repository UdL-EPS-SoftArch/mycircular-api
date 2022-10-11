package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.Review;
import cat.udl.eps.softarch.demo.repository.ReviewRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReviewStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private ReviewRepository reviewRepository;

    @Given("There is no review submitted by a buyer with username {string} to a seller with username {string}")
    public void thereIsNoReviewSubmittedByABuyerWithUsernameToASellerWithUsername(String buyer, String seller) {
        /*List<Review> reviews = reviewRepository.findByAuthorContaining(buyer);

        boolean alreadyHasSentAReview = false;

        for (Review review : reviews) {
            if (review.getAbout().getUsername() == seller)
            {
                alreadyHasSentAReview = true;
                break;
            }
        }

        Assert.assertFalse(alreadyHasSentAReview);*/
    }

    @And("Exists a transaction between the buyer with username {string} and the seller with username {string}")
    public void existsATransactionBetweenTheBuyerWithUsernameAndTheSellerWithUsername(String arg0, String arg1) {
    }

    @When("The buyer submits a new review with username {string}, number of stars {int} and message {string}")
    public void theBuyerSubmitsANewReviewWithUsernameNumberOfStarsAndMessage(String buyer, int nStars, String message) throws Throwable {
        Review review = new Review();

        review.setStars(nStars);
        review.setMessage(message);

        stepDefs.result = stepDefs.mockMvc.perform(
            post("/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new JSONObject(stepDefs.mapper.writeValueAsString(review)).toString())
                .accept(MediaType.APPLICATION_JSON)
                .with(AuthenticationStepDefs.authenticate()))
            .andDo(print());
    }

    @And("It has been submitted a review for the seller with username {string}, number of stars {int} and message {string}")
    public void itHasBeenSubmittedAReviewForTheSellerWithUsernameNumberOfStarsAndMessage(String arg0, int arg1, String arg2) {
    }

    @And("It has been submitted a review by buyer with username {string}")
    public void itHasBeenSubmittedAReviewByBuyerWithUsername(String arg0) {
    }

    @Given("The buyer is not logged in")
    public void theBuyerIsNotLoggedIn() {
    }

    @And("The review has not been created")
    public void theReviewHasNotBeenCreated() {
    }

    @Given("There is already a review submitted by a buyer with username {string} to a seller with username {string}")
    public void thereIsAlreadyAReviewSubmittedByABuyerWithUsernameToASellerWithUsername(String arg0, String arg1) {
    }

    @Given("There is no any transaction between the buyer with username {string} and the seller with username {string}")
    public void thereIsNoAnyTransactionBetweenTheBuyerWithUsernameAndTheSellerWithUsername(String arg0, String arg1) {
    }

    @When("The buyer submits a new review with username {string} and message {string}")
    public void theBuyerSubmitsANewReviewWithUsernameAndMessage(String username, String message) throws Throwable{
        Review review = new Review();

        review.setStars(null);
        review.setMessage(message);

        stepDefs.result = stepDefs.mockMvc.perform(
            post("/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new JSONObject(stepDefs.mapper.writeValueAsString(review)).toString())
                .accept(MediaType.APPLICATION_JSON)
                .with(AuthenticationStepDefs.authenticate()))
            .andDo(print());
    }

    @And("The buyer with username {string} and password {string} is logged in")
    public void theBuyerWithUsernameAndPasswordIsLoggedIn(String username, String password) throws Throwable {
        AuthenticationStepDefs.currentUsername = username;
        AuthenticationStepDefs.currentPassword = password;

        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/identity", username)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
