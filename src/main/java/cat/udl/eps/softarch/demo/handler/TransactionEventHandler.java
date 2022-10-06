package cat.udl.eps.softarch.demo.handler;

import cat.udl.eps.softarch.demo.domain.Transaction;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;

import java.time.ZonedDateTime;
import java.util.Date;

public class TransactionEventHandler {

    @HandleBeforeCreate
    public void handleTransactionPreCreate(Transaction transaction) {
        transaction.setStatus(Transaction.StatusTypes.INITIALIZED);
        ZonedDateTime now = ZonedDateTime.now();
        transaction.setCreationDate(now);
    }
}
