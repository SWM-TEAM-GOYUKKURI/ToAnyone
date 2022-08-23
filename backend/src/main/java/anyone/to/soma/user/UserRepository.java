package anyone.to.soma.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsUserByUniqueId(String uniqueId);

    Optional<User> findUserByUniqueId(String uniqueId);
}
