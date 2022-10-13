package anyone.to.soma.user.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Age age;

    @Enumerated(EnumType.STRING)
    private Job job;

    @OneToOne(mappedBy = "profile")
    private User user;

    // TODO: 심리검사 선택지 추가


    public Profile(String nickname, Gender gender, Age age, Job job, User user) {
        this(null, nickname, gender, age, job, user);
    }

    private Profile(Long id, String nickname, Gender gender, Age age, Job job, User user) {
        this.id = id;
        this.nickname = nickname;
        this.gender = gender;
        this.age = age;
        this.job = job;
        this.user = user;
    }
}
