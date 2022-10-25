package cat.udl.eps.softarch.demo.handler;

import cat.udl.eps.softarch.demo.domain.Offer;
import cat.udl.eps.softarch.demo.domain.User;
import cat.udl.eps.softarch.demo.exception.ForbiddenException;
import cat.udl.eps.softarch.demo.repository.OfferRepository;
import cat.udl.eps.softarch.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.security.core.context.SecurityContextHolder;

public class OfferEventHandler {
    final Logger logger = LoggerFactory.getLogger(User.class);

    final OfferRepository OfferRepository;

    public OfferEventHandler(OfferRepository offerRepository) {
        this.OfferRepository = offerRepository;
    }

    @HandleBeforeDelete
    public void handleOfferPreDelete(Offer offer) {
        if (offer.getOfferer() != (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
            throw new ForbiddenException();
    }

}
