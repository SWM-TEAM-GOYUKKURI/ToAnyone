package anyone.to.soma.user.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int level;

    private String name;

    private String tag;

    private Long userId;

    public Achievement(int level, String name, String tag, Long userId) {
        this(null, level, name, tag, userId);
    }

    private Achievement(Long id, int level, String name, String tag, Long userId) {
        this.id = id;
        this.level = level;
        this.name = name;
        this.tag = tag;
        this.userId = userId;
    }
}
