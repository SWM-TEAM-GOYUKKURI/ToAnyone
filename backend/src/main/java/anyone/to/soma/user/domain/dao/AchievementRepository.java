package anyone.to.soma.user.domain.dao;

import anyone.to.soma.user.domain.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {

    List<Achievement> findAllByUserId(Long userId);
}
