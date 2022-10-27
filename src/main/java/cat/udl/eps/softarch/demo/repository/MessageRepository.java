package cat.udl.eps.softarch.demo.repository;

import cat.udl.eps.softarch.demo.domain.Message;
import cat.udl.eps.softarch.demo.domain.Review;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
    public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {

    List<Message> findById(@Param("id") int id);
}