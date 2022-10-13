package anyone.to.soma.user.domain.type;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Job {
    STUDENT,
    STUDENT_UNIV,
    MANAGEMENT,
    OFFICE_TECH,
    PROFESSIONAL,
    FREELANCER,
    SIMPLE_LABOR,
    SELLER,
    SERVICE,
    SELF_EMP,
    HOME_MAKER,
    NOT_WORKING,
    NOT_SELECTED;

    @JsonCreator
    public static Job from(String value) {
        return Job.valueOf(value.toUpperCase());
    }
}
