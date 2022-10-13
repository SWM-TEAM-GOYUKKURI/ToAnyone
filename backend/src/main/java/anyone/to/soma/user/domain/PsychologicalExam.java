package anyone.to.soma.user.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Embeddable
public class PsychologicalExam {

    private Long questionId;
    private Long answerId;

    public PsychologicalExam(Long questionId, Long answerId) {
        this.questionId = questionId;
        this.answerId = answerId;
    }
}
