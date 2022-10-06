package cat.udl.eps.softarch.demo.handler;


import cat.udl.eps.softarch.demo.domain.Message;
import cat.udl.eps.softarch.demo.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class MessageEventHandle {
    final Logger logger = LoggerFactory.getLogger(Message.class);

    final MessageRepository messageRepository;

    public MessageEventHandle(MessageRepository messageRepository){this.messageRepository = messageRepository;}

    @HandleBeforeCreate
    public void handleMessagePreCreate(Message message){logger.info("Before creating: {}", message.toString());}


    @HandleAfterDelete
    public void handleMessagePostDelete(Message message) {
        logger.info("After deleting: {}", message.toString());
    }

    @HandleAfterLinkSave
    public void handleMessagePostLinkSave(Message message, Object o) {
        logger.info("After linking: {} to {}", message.toString(), o.toString());
    }
}
