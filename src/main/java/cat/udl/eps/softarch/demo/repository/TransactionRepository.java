package cat.udl.eps.softarch.demo.repository;

import cat.udl.eps.softarch.demo.domain.Transaction;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PostFilter;

import java.util.List;

@RepositoryRestResource
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {

    @PostFilter("filterObject.buyer.username == authentication.principal.username")
    List<Transaction> findByBuyer_Username(@Param("username") String username);

    @PostFilter("filterObject.seller.username == authentication.principal.username")
    List<Transaction> findBySeller_Username(@Param("username") String username);

    @PostFilter("filterObject.buyer.username == authentication.principal.username or filterObject.seller.username == authentication.principal.username")
    List<Transaction> findByBuyer_UsernameOrSeller_Username(@Param("buyerUsername") String username, @Param("sellerUsername") String username2);
}
