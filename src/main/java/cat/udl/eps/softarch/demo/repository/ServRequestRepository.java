package cat.udl.eps.softarch.demo.repository;

import cat.udl.eps.softarch.demo.domain.ProdRequest;
import cat.udl.eps.softarch.demo.domain.Request;
import cat.udl.eps.softarch.demo.domain.ServRequest;
import cat.udl.eps.softarch.demo.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServRequestRepository extends PagingAndSortingRepository<ServRequest, Long> {

    List<Request> findByRequester(@Param("requester") User requester);
}
