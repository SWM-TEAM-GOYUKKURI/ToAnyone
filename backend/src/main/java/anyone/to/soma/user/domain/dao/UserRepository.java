package anyone.to.soma.user.domain.dao;

import anyone.to.soma.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsUserByUniqueId(String uniqueId);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByUniqueId(String uniqueId);

    @Query(value = "SELECT u FROM User as u WHERE u.id<>:userId AND u.receiveCount =" +
            "(SELECT MIN(u2.receiveCount) FROM User u2 WHERE u2.id<>:userId)")
    List<User> findUsersByMinReceiveCount(@Param("userId") Long userId);

    @Modifying
    @Query("UPDATE User u SET u.receiveCount = u.receiveCount + 1 WHERE u.id=:userId")
    void increaseReceiveCount(@Param("userId") Long userId);

    @Modifying
    @Query("UPDATE User u SET u.userAchievement.sendLetterCount = u.userAchievement.sendLetterCount + 1 WHERE u.id=:userId")
    void increaseSendCount(@Param("userId") Long userId);

    @Modifying
    @Query("UPDATE User u SET u.userAchievement.loginCount = u.userAchievement.loginCount + 1 WHERE u.id=:userId")
    void increaseLoginCount(@Param("userId") Long userId);

    @Modifying
    @Query("UPDATE User u SET u.point.point = u.point.point + :point WHERE u.id=:userId")
    void increaseUserPoint(@Param("userId") Long userId, @Param("point") Long point);

}