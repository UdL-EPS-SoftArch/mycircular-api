package cat.udl.eps.softarch.demo.repository;

import cat.udl.eps.softarch.demo.domain.ProdRequest;
import cat.udl.eps.softarch.demo.domain.ServRequest;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ServRequestRepository extends PagingAndSortingRepository<ServRequest, Long> {
}
