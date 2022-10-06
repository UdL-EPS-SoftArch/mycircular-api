package cat.udl.eps.softarch.demo.handler;

import cat.udl.eps.softarch.demo.domain.Offer;
import cat.udl.eps.softarch.demo.domain.User;
import cat.udl.eps.softarch.demo.exception.ForbiddenException;
import cat.udl.eps.softarch.demo.repository.OfferRepository;
import cat.udl.eps.softarch.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterLinkSave;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeLinkSave;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class OfferEventHandler {

    final Logger logger = LoggerFactory.getLogger(Offer.class);

    final OfferRepository offerRepository;

    public OfferEventHandler(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @HandleBeforeCreate
    public void handleOfferPreCreate(Offer newOffer) {
        assert newOffer.getId() != null;
        if(offerRepository.existsById(newOffer.getId())){
            throw new ForbiddenException();
        }
    }
}
