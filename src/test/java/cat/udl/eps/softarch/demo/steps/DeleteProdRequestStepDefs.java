package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.ProdRequest;
import cat.udl.eps.softarch.demo.domain.Request;
import cat.udl.eps.softarch.demo.domain.User;
import cat.udl.eps.softarch.demo.repository.OfferRepository;
import cat.udl.eps.softarch.demo.repository.RequestRepository;
import cat.udl.eps.softarch.demo.repository.UserRepository;
import io.cucumber.java.en.And;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class DeleteProdRequestStepDefs {

    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private UserRepository userRepository;

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
}
