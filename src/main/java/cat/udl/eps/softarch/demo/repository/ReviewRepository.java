package cat.udl.eps.softarch.demo.repository;

import cat.udl.eps.softarch.demo.domain.Review;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ReviewRepository extends PagingAndSortingRepository<Review, Integer> {}
