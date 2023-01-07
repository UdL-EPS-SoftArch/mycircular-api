package cat.udl.eps.softarch.demo.config;

import cat.udl.eps.softarch.demo.domain.Admin;
import cat.udl.eps.softarch.demo.domain.Review;
import cat.udl.eps.softarch.demo.domain.User;
import cat.udl.eps.softarch.demo.repository.AdminRepository;
import cat.udl.eps.softarch.demo.repository.ReviewRepository;
import cat.udl.eps.softarch.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

@Configuration
public class AuthenticationConfig extends GlobalAuthenticationConfigurerAdapter {

  @Value("${default-password}")
  String defaultPassword;

  final BasicUserDetailsService basicUserDetailsService;
  final UserRepository userRepository;
  final AdminRepository adminRepository;
  final ReviewRepository reviewRepository;

  public AuthenticationConfig(BasicUserDetailsService basicUserDetailsService, UserRepository userRepository, AdminRepository adminRepository,
  ReviewRepository reviewRepository) {
    this.basicUserDetailsService = basicUserDetailsService;
    this.userRepository = userRepository;
    this.adminRepository = adminRepository;
    this.reviewRepository = reviewRepository;
  }

  @Override
  public void init(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .userDetailsService(basicUserDetailsService)
        .passwordEncoder(User.passwordEncoder);

    // Sample users
    if (!userRepository.existsById("demo")) {
      User player = new User();
      player.setEmail("demo@sample.app");
      player.setUsername("demo");
      player.setPassword(defaultPassword);
      player.encodePassword();
      userRepository.save(player);
    }

    if (!userRepository.existsById("demo2")) {
      User player2 = new User();
      player2.setEmail("demo2@sample.app");
      player2.setUsername("demo2");
      player2.setPassword(defaultPassword);
      player2.encodePassword();
      userRepository.save(player2);
    }

    //Admin
    Admin admin = new Admin();
    admin.setUsername("admin");
    admin.setEmail("admin@admin.com");
    admin.setPassword(defaultPassword);
    admin.encodePassword();
    adminRepository.save(admin);
  }

}
