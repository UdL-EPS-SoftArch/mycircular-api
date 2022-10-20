package cat.udl.eps.softarch.demo.repository;

import cat.udl.eps.softarch.demo.domain.Request;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface RequestRepository extends PagingAndSortingRepository<Request, Long> {

    List<Request> findById(@Param("id") long id);

}
