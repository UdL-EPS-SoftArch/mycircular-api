package cat.udl.eps.softarch.demo.repository;


import cat.udl.eps.softarch.demo.domain.CounterOffer;
import cat.udl.eps.softarch.demo.domain.ServiceOffer;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CounterOfferRepository extends PagingAndSortingRepository<CounterOffer, Long> {

}
