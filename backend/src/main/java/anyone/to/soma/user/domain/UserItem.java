package anyone.to.soma.user.domain;

import anyone.to.soma.decoration.DecorationType;
import anyone.to.soma.user.domain.event.UserItemCreatedEvent;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserItem extends AbstractAggregateRoot<UserItem> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    public Long id;
    public DecorationType category;
    public Long itemId;

    public Long userId;

    public UserItem(DecorationType category, Long itemId, Long userId) {
        this(null, category, itemId, userId);
    }

    private UserItem(Long id, DecorationType category, Long itemId, Long userId) {
        this.id = id;
        this.category = category;
        this.itemId = itemId;
        this.userId = userId;
    }

    @PostPersist
    void created(){
        this.registerEvent(new UserItemCreatedEvent(this.getItemId(), this.getUserId()));
    }
}
