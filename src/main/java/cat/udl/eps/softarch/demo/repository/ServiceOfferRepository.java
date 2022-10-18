package cat.udl.eps.softarch.demo.repository;

import cat.udl.eps.softarch.demo.domain.ServiceOffer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ServiceOfferRepository extends PagingAndSortingRepository<ServiceOffer, Long>{

}
