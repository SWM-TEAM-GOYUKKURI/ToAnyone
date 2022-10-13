package anyone.to.soma.decoration;


import anyone.to.soma.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Decoration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DecorationType decorationType;

    private int count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public Decoration(DecorationType decorationType, int count, User user) {
        this(null, decorationType, count, user);
    }

    private Decoration(Long id, DecorationType decorationType, int count, User user) {
        this.id = id;
        this.decorationType = decorationType;
        this.count = count;
        this.user = user;
    }
}
