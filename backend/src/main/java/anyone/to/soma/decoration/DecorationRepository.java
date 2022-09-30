package anyone.to.soma.decoration;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DecorationRepository extends JpaRepository<Decoration, Long> {

    List<Decoration> findByUserId(Long userId);

}
