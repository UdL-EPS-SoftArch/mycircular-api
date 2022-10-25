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

    public SubmitReviewStepDefs(StepDefs stepDefs, ReviewRepository reviewRepository, UserRepository userRepository){
        this.stepDefs = stepDefs;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    @When("The buyer submits a new review with username {string}, number of stars {int} and message {string} to a seller with username {string}")
    public void theBuyerSubmitsANewReviewWithUsernameNumberOfStarsAndMessageToASellerWithUsername(String buyer, int nStars, String message, String seller) throws Throwable{
        Review review = new Review();
        review.setStars(nStars);
        review.setMessage(message);

        User author = userRepository.findByUsernameContaining(buyer).get(0);
        review.setAuthor(author);

        User about = userRepository.findByUsernameContaining(seller).get(0);
        review.setAbout(about);

        stepDefs.result = stepDefs.mockMvc.perform(
            post("/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(stepDefs.mapper.writeValueAsString(review))
                .accept(MediaType.APPLICATION_JSON)
                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("It has been submitted a review by buyer with username {string}")
    public void itHasBeenSubmittedAReviewByBuyerWithUsername(String author) throws Throwable {
        String id = stepDefs.result.andReturn().getResponse().getHeader("Location");

        assert id != null;

        stepDefs.result = stepDefs.mockMvc.perform(
                get(id)
                .accept(MediaType.APPLICATION_JSON)
                .with(AuthenticationStepDefs.authenticate())
            )
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
            .with(AuthenticationStepDefs.authenticate())
        )
        .andDo(print())
        .andExpect(jsonPath("$.id", is(author)));
    }

    @And("A new review has not been created")
    public void aNewReviewHasNotBeenCreated() {
        Assert.assertEquals(0, reviewRepository.count());
    }

    @And("There is already a review submitted by a buyer with username {string} to a seller with username {string}")
    public void thereIsAlreadyAReviewSubmittedByABuyerWithUsernameToASellerWithUsername(String buyerUsername, String sellerUsername) {
        List<User> buyer = userRepository.findByUsernameContaining(buyerUsername);
        List<User> seller = userRepository.findByUsernameContaining(sellerUsername);

        Review review = new Review();
        review.setStars(5);
        review.setMessage("Fantastic!");
        review.setAuthor(buyer.get(0));
        review.setAbout(seller.get(0));

        reviewRepository.save(review);
    }

    @And("A duplicated review has not been created")
    public void aDuplicatedReviewHasNotBeenCreated() { Assert.assertEquals(1, reviewRepository.count()); }
}