package cat.udl.eps.softarch.demo.repository;

import cat.udl.eps.softarch.demo.domain.Request;
import cat.udl.eps.softarch.demo.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigDecimal;
import java.util.List;

@RepositoryRestResource
public interface RequestRepository extends PagingAndSortingRepository<Request, Long> {

    List<Request> findById(@Param("id") long id);

    List<Request> findByName(@Param("name") String Name);
    List<Request> findByPrice(@Param("price") BigDecimal price);
    List<Request> findByDescription(@Param("description") String Description);
    List<Request> findByRequester(@Param("requester") User requester);
    List<Request> findByNameAndPriceAndDescriptionAndRequester(@Param("attributes") String name, BigDecimal price, String description, User requester);

    List<Request> findByNameAndPrice(@Param("name") String Name, BigDecimal price);
    List<Request> findByNameAndPriceAndDescription(@Param("name") String name, BigDecimal price, String description);
}
