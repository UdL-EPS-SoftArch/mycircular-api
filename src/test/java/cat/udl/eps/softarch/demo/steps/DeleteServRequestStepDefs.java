package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.ProdRequest;
import cat.udl.eps.softarch.demo.domain.ServRequest;
import cat.udl.eps.softarch.demo.domain.User;
import cat.udl.eps.softarch.demo.repository.RequestRepository;
import cat.udl.eps.softarch.demo.repository.UserRepository;
import io.cucumber.java.en.And;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class DeleteServRequestStepDefs {

    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private UserRepository userRepository;

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
}
