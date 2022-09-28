package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.Transaction;
import cat.udl.eps.softarch.demo.domain.User;
import cat.udl.eps.softarch.demo.repository.TransactionRepository;
import io.cucumber.java.en.When;
import io.cucumber.java.sl.In;
import org.junit.Assert;
import org.junit.Before;
import io.cucumber.java.en.Given;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.math.BigDecimal;

public class CreateTransDefs {

    public static Long identifyer;

    @Before
    public void setup(){
        CreateTransDefs.identifyer = null;
    }

    @Autowired
    private TransactionRepository transactionRepository;

    @Given("^There is no transaction created with identifyer (\\d+)")
    public void thereIsNoTransactionCreatedWithIdentifyer(Integer identifyer){
        Long id = Long.valueOf(identifyer);
        Assert.assertFalse("Identifyer \"" + identifyer + "\"shouldn't exist",transactionRepository.existsById(id));
    }

    @When("^I Create a new Transaction with Identifyer (\\d+), price (\\d+), Status \"([^\"]*)\"")
    public void iCreateANewTransactionWithIdentifyerPriceAndStatus(Integer identifyer, Integer price, String status){
        Transaction transaction = new Transaction();

        Long id = Long.valueOf(identifyer);
        BigDecimal priceDecimal = BigDecimal.valueOf(price);

        transaction.setIdentifyer(id);
        transaction.setPrice(priceDecimal);
        transaction.assigntime();

        //Status assings implements remaning
    }
}
