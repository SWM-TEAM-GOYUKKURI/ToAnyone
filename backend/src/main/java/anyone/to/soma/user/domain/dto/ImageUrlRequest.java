package anyone.to.soma.user.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
public class ImageUrlRequest {

    private String userImageUrl;

    public ImageUrlRequest(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

}
