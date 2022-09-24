package anyone.to.soma.decoration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DecorationService {

    private final DecorationRepository decorationRepository;

    @Transactional
    public Long saveDecoration(Decoration decoration) {
        return decorationRepository.save(decoration).getId();
    }
    public List<Decoration> findDecorationsOfUser(Long userId){
        return decorationRepository.findByUserId(userId);
    }
}
