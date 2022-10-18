package cat.udl.eps.softarch.demo.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class ProductOfferStepDefs {

    
    @Then("The product offer should be created.")
    public void theProductOfferShouldBeCreated() {
        
    }
    
    @And("a manufacturer {string} a band {string} and a product code {string}")
    public void aManufacturerABandAndAProductCode(String manufacturer, String brand, String productCode) {
        
    }

    @And("After all the steps I can retrieve this product offer which should have the product code {string}.")
    public void afterAllTheStepsICanRetrieveThisProductOfferWhichShouldHaveTheProductCode(String arg0) {
        
    }

    @Then("I want to modify this product's brand to {string}.")
    public void iWantToModifyThisProductSBrandTo(String arg0) {
        
    }

    @And("After all the steps I can retrieve this product offer which should have the brand {string}.")
    public void afterAllTheStepsICanRetrieveThisProductOfferWhichShouldHaveTheBrand(String arg0) {
        
    }


    @Then("I want to delete the product offer with id {string}.")
    public void iWantToDeleteTheProductOfferWithId(String arg0) {
        
    }

    @And("I want to check that the product offer doesn't exist anymore.")
    public void iWantToCheckThatTheProductOfferDoesnTExistAnymore() {
    }
}
