package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.ProdRequest;
import cat.udl.eps.softarch.demo.domain.ServRequest;
import cat.udl.eps.softarch.demo.domain.User;
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

public class DeleteServRequestStepDefs {

    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StepDefs stepDefs;

    @And("There is a service request already created")
    public void thereIsAServiceRequestAlreadyCreated() {
        ServRequest servRequest = new ServRequest();
        servRequest.setName("croqueta2");
        servRequest.setPrice(new BigDecimal(100));
        servRequest.setDescription("le hago la competencia a la mama");
        User requester = new User();
        requester.setUsername("manolo");
        requester.setPassword("password");
        requester.setEmail("manolo" + "@gmail.com");
        servRequest.setRequester(requester);

        servRequest.setRequester(requester);
        requestRepository.save(servRequest);
        Assert.assertEquals(1, requestRepository.count());
    }

    @When("I delete a service request with id {string}")
    public void iDeleteAServiceRequestWithId(String id) throws Exception {
        stepDefs.result = stepDefs.mockMvc.perform(
                        delete("/servRequests/{id}", id)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }
}
