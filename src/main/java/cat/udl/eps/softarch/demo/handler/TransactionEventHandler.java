package cat.udl.eps.softarch.demo.handler;

import cat.udl.eps.softarch.demo.domain.Transaction;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
@RepositoryEventHandler
public class TransactionEventHandler {

    @HandleBeforeCreate
    public void handleTransactionPreCreate(Transaction transaction) {
        if (transaction.getStatus() == null) {
            transaction.setStatus(Transaction.StatusTypes.INITIALIZED);
        }

        if (transaction.getStatus() != Transaction.StatusTypes.INITIALIZED
                && transaction.getStatus() != Transaction.StatusTypes.IN_PROGRESS
                && transaction.getStatus() != Transaction.StatusTypes.CLOSED) {
            throw new IllegalArgumentException("Status must be one of the following: INITIALIZED, IN_PROGRESS, CLOSED");
        }

        ZonedDateTime now = ZonedDateTime.now();
        transaction.setCreationDate(now);
    }
}
