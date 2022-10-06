package cat.udl.eps.softarch.demo.repository;

import cat.udl.eps.softarch.demo.domain.Offer;
import cat.udl.eps.softarch.demo.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface OfferRepository extends PagingAndSortingRepository<Offer, Long> {
    //public boolean existsAnnouncement(@Param("id") Announcement announcement);
    //public List<Offer> findAnnouncementByDateTime(@Param("id") Announcement announcement);

}
