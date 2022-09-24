package anyone.to.soma.letter;

import anyone.to.soma.decoration.DecorationType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class LetterDecoration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DecorationType decorationType;

    public LetterDecoration(DecorationType decorationType) {
        this(null, decorationType);
    }

    private LetterDecoration(Long id, DecorationType decorationType) {
        this.id = id;
        this.decorationType = decorationType;
    }
}
