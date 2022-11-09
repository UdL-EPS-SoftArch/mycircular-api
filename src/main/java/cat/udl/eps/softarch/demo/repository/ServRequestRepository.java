package cat.udl.eps.softarch.demo.repository;

import cat.udl.eps.softarch.demo.domain.Request;
import cat.udl.eps.softarch.demo.domain.ServRequest;
import cat.udl.eps.softarch.demo.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ServRequestRepository extends PagingAndSortingRepository<ServRequest, Long> {

    List<Request> findByRequester(@Param("requester") User requester);
    void deleteByRequester(@Param("requester") User requester);
    List<Request> findByNameAndPriceAndDescriptionAndRequester(@Param("atts") String name, BigDecimal price, String description, User requester);
}
