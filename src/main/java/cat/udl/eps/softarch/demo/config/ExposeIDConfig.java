package cat.udl.eps.softarch.demo.config;

import cat.udl.eps.softarch.demo.domain.Review;
import cat.udl.eps.softarch.demo.domain.User;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Component
public class ExposeIDConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry regs){
        config.exposeIdsFor(Review.class);
    }
}
