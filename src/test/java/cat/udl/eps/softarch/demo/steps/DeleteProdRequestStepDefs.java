package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.ProdRequest;
import cat.udl.eps.softarch.demo.domain.Request;
import cat.udl.eps.softarch.demo.domain.User;
import cat.udl.eps.softarch.demo.repository.OfferRepository;
import cat.udl.eps.softarch.demo.repository.RequestRepository;
import cat.udl.eps.softarch.demo.repository.UserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class DeleteProdRequestStepDefs {

    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StepDefs stepDefs;

    @And("There is a product request already created")
    public void thereIsAProductRequestAlreadyCreated() {
        ProdRequest prodRequest = new ProdRequest();
        prodRequest.setName("croqueta2");
        prodRequest.setPrice(new BigDecimal(100));
        prodRequest.setDescription("le hago la competencia a la mama");
        User requester = new User();
        requester.setUsername("manolo");
        requester.setPassword("password");
        requester.setEmail("manolo" + "@gmail.com");
        prodRequest.setRequester(requester);

        prodRequest.setRequester(requester);
        requestRepository.save(prodRequest);
        Assert.assertEquals(1, requestRepository.count());
    }

    @When("I delete a product request with id {string}")
    public void iDeleteAProductRequestWithId(String id) throws Exception {
        stepDefs.result = stepDefs.mockMvc.perform(
                        delete("/prodRequests/{id}", id)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }
}
