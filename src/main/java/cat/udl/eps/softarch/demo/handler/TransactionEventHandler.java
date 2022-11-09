package cat.udl.eps.softarch.demo.handler;

import cat.udl.eps.softarch.demo.domain.Transaction;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Component
@RepositoryEventHandler
public class TransactionEventHandler {

    @HandleBeforeCreate
    public void handleTransactionPreCreate(Transaction transaction) {
        if (transaction.getStatus() == null) {
            transaction.setStatus(Transaction.StatusTypes.INITIALIZED);
        }

        ZonedDateTime now = ZonedDateTime.now();
        transaction.setCreationDate(now);
    }


}
