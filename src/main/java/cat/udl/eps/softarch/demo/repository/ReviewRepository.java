package cat.udl.eps.softarch.demo.repository;

import cat.udl.eps.softarch.demo.domain.Review;
import cat.udl.eps.softarch.demo.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;



import java.util.List;

@RepositoryRestResource
public interface ReviewRepository extends PagingAndSortingRepository<Review, Integer> {
    List<Review> findById(@Param("id") int id);
    List<Review> findByStars(@Param("stars") Integer stars);
    List<Review> findByMessage(@Param("message") String message);
    List<Review> findByAuthor(@Param("author") User author);
    List<Review> findByAbout(@Param("about") User about);

}
