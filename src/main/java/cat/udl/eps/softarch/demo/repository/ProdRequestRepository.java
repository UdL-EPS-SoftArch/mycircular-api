package cat.udl.eps.softarch.demo.repository;


import cat.udl.eps.softarch.demo.domain.ProdRequest;
import cat.udl.eps.softarch.demo.domain.Request;
import cat.udl.eps.softarch.demo.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigDecimal;
import java.util.List;

@RepositoryRestResource
public interface ProdRequestRepository extends PagingAndSortingRepository<ProdRequest, Long> {
    List<Request> findByRequester(@Param("requester") User requester);
    List<Request> findByNameAndPriceAndDescriptionAndRequester(@Param("atts") String name, BigDecimal price, String description, User requester);
    void deleteByRequester(@Param("requester") User requester);
}
