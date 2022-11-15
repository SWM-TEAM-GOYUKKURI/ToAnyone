package anyone.to.soma.user.domain.dto;

import anyone.to.soma.decoration.DecorationType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
public class ItemRequest {

    public DecorationType category;
    public Long itemId;
    public Long price;

    public ItemRequest(DecorationType category, Long itemId, Long price) {
        this.category = category;
        this.itemId = itemId;
        this.price = price;
    }
}
