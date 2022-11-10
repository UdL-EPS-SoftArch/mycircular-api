package cat.udl.eps.softarch.demo.repository;

import cat.udl.eps.softarch.demo.domain.Announcement;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface AnnouncementRepository extends PagingAndSortingRepository<Announcement, Long> {

    List<Announcement> findById(@Param("id") long id);
}
