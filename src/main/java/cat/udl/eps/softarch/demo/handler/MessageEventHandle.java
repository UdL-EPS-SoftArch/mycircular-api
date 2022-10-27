package cat.udl.eps.softarch.demo.handler;

import cat.udl.eps.softarch.demo.domain.Announcement;
import cat.udl.eps.softarch.demo.domain.Message;
import cat.udl.eps.softarch.demo.domain.User;
import cat.udl.eps.softarch.demo.repository.AnnouncementRepository;
import cat.udl.eps.softarch.demo.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.Access;

@Component
@RepositoryEventHandler
public class MessageEventHandle {
    final Logger logger = LoggerFactory.getLogger(Message.class);

    final MessageRepository messageRepository;
    final AnnouncementRepository announcementRepository;
    @Autowired
    public MessageEventHandle(MessageRepository messageRepository, AnnouncementRepository announcementRepository) {
        this.messageRepository = messageRepository;
        this.announcementRepository = announcementRepository;
    }

    @HandleBeforeCreate
    public void handleMessagePreCreate(Message message) {
        logger.info("Before creating: {}", message.toString());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        message.setUser(user);

    }

    @HandleAfterDelete
    public void handleMessagePostDelete(Message message) {
        logger.info("After deleting: {}", message.toString());
    }

    @HandleAfterLinkSave
    public void handleMessagePostLinkSave(Message message, Object o) {
        logger.info("After linking: {} to {}", message.toString(), o.toString());
    }

}
