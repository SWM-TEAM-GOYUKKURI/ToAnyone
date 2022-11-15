package anyone.to.soma.user.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class PurchaseResponse {
    private Long remainingPoint;

    public PurchaseResponse(Long remainingPoint) {
        this.remainingPoint = remainingPoint;
    }
}
