package anyone.to.soma.user.domain.type;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Age {

    TEN,
    TWENTY,
    THIRTY,
    FORTY,
    FIFTY,
    NOT_SELECTED;

    @JsonCreator
    public static Age from(String value) {
        return Age.valueOf(value.toUpperCase());
    }
}
