package anyone.to.soma.user.domain.dto;

import anyone.to.soma.user.domain.PsychologicalExam;
import anyone.to.soma.user.domain.type.Age;
import anyone.to.soma.user.domain.type.Gender;
import anyone.to.soma.user.domain.type.Job;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
@AllArgsConstructor
public class ProfileRequest {

    private String nickname;
    private Gender gender;
    private Age age;
    private Job job;
    private List<PsychologicalExam> psychologicalExams;
}
