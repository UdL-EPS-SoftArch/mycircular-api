package cat.udl.eps.softarch.demo.repository;

import cat.udl.eps.softarch.demo.domain.Request;
import cat.udl.eps.softarch.demo.domain.Review;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@RepositoryRestResource
public interface ReviewRepository extends PagingAndSortingRepository<Review, Integer> {
    List<Review> findById(@Param("id") int id);
}
