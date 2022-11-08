package anyone.to.soma.user.domain.dao;

import anyone.to.soma.user.domain.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {
}
