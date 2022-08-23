package anyone.to.soma.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsUserByUniqueId(String uniqueId);

    Optional<User> findUserByUniqueId(String uniqueId);

    @Query(value = "SELECT u FROM User as u WHERE u.receiveCount = (select min(u2.receiveCount) from User u2)")
    List<User> findUsersByMinReceieveCount();
}
