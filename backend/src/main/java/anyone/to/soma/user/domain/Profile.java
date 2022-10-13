package anyone.to.soma.user.domain;

import anyone.to.soma.user.domain.type.Age;
import anyone.to.soma.user.domain.type.Gender;
import anyone.to.soma.user.domain.type.Job;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @JsonIgnore
    private User user;

    @ElementCollection
    @CollectionTable(
            name = "user_psychological_test",
            joinColumns = @JoinColumn(name = "profile_id")
    )
    private final List<PsychologicalExam> psychologicalExams = new ArrayList<>();

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

    public void addPsychologicalExam(List<PsychologicalExam> psychologicalExam) {
        psychologicalExams.addAll(psychologicalExam);
    }
}
