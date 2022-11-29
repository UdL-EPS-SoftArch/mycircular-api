package cat.udl.eps.softarch.demo.config;

import cat.udl.eps.softarch.demo.domain.Review;
import cat.udl.eps.softarch.demo.domain.User;
import cat.udl.eps.softarch.demo.repository.ReviewRepository;
import cat.udl.eps.softarch.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;

@Configuration
@Profile("test")
public class BBDDInitialization {
    @Value("{default-password}")
    String defaultPassword;

    private UserRepository userRepository;
    private ReviewRepository reviewRepository;

    public BBDDInitialization(ReviewRepository reviewRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    @PostConstruct
    public void initializeDatabase() {

        //Users
        User user = this.userRepository.findById("demo").orElse(new User());
        if (user.getId() == null) {
            user.setEmail("demo@sample.com");
            user.setUsername("demo");
            user.setPassword(defaultPassword);
            user.encodePassword();
            userRepository.save(user);
        }

        User user2 = this.userRepository.findById("demo2").orElse(new User());
        if (user2.getId() == null) {
            user2.setEmail("demo2@sample.com");
            user2.setUsername("demo2");
            user2.setPassword(defaultPassword);
            user2.encodePassword();
            userRepository.save(user2);
        }

        //Reviews
        Review review = new Review();
        review.setAuthor(user);
        review.setAbout(user2);
        review.setStars(5);
        review.setMessage("Great!");
        reviewRepository.save(review);

        Review review2 = new Review();
        review2.setAuthor(user2);
        review2.setAbout(user);
        review2.setStars(4);
        review2.setMessage("Very good!");
        reviewRepository.save(review2);
    }
}
