package cat.udl.eps.softarch.demo.repository;

import cat.udl.eps.softarch.demo.domain.Transaction;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PostFilter;

import java.util.List;

@RepositoryRestResource
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {

    /* Interface provides automatically, as defined in https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/PagingAndSortingRepository.html
     * count, delete, deleteAll, deleteById, existsById, findAll, findAllById, findById, save, saveAll
     *
     * Additional methods following the syntax defined in
     * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
     */


    @PostFilter("filterObject.buyer.username == authentication.principal.username")
    List<Transaction> findByBuyer_Username(@Param("username") String username);
}
