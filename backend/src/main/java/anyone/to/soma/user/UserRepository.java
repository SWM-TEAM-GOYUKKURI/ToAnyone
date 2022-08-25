package anyone.to.soma.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsUserByUniqueId(String uniqueId);

    Optional<User> findUserByUniqueId(String uniqueId);

    @Query(value = "SELECT u FROM User as u WHERE u.receiveCount =" +
            "(SELECT MIN(u2.receiveCount) FROM User u2 WHERE u2.id<>:userId)")
    List<User> findUsersByMinReceiveCount(@Param("userId") Long userId);
}
