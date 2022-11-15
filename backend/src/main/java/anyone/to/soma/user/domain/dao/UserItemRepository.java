package anyone.to.soma.user.domain.dao;

import anyone.to.soma.user.domain.UserItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserItemRepository extends JpaRepository<UserItem, Long> {

    List<UserItem> findUserItemsByUserId(Long userId);
}
