package cat.udl.eps.softarch.demo.config;

import cat.udl.eps.softarch.demo.domain.Review;
import cat.udl.eps.softarch.demo.domain.User;
import cat.udl.eps.softarch.demo.repository.ReviewRepository;
import cat.udl.eps.softarch.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class BBDDInitialization {
    @Value("{default-password}")
    String defaultPassword;

    final UserRepository userRepository;
    final ReviewRepository reviewRepository;

    public BBDDInitialization(ReviewRepository reviewRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    @PostConstruct
    public void initializeDatabase() {
        User user1 = new User();
        user1.setUsername("user0");
        user1.setEmail("user0@sample.com");
        user1.setPassword("12345678");
        user1.encodePassword();
        userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("user0");
        user2.setEmail("user0@sample.com");
        user2.setPassword("12345678");
        user2.encodePassword();
        userRepository.save(user2);

        Review review1 = new Review();
        review1.setAuthor(user1);
        review1.setAbout(user2);
        review1.setStars(5);
        review1.setMessage("Great!");
        reviewRepository.save(review1);
    }
}
