package cat.udl.eps.softarch.demo.repository;

import cat.udl.eps.softarch.demo.domain.Admin;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdminRepository extends PagingAndSortingRepository<Admin, String> {

    List<Admin> findByUsernameContaining(@Param("text") String text);
}
