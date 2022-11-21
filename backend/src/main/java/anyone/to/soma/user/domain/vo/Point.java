package anyone.to.soma.user.domain.vo;

import anyone.to.soma.exception.ApplicationException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Point {

    @Column(name = "point", nullable = false)
    private Long point;

    public Point(Long point) {
        validateValue(point);
        this.point = point;
    }

    public Point() {
        this.point = 0L;
    }

    public Long getValue() {
        return point;
    }

    private void validateValue(Long point) {
        if (Objects.isNull(point) || point < 0L) {
            throw new ApplicationException("유효하지 않은 포인트 값입니다.");
        }
    }

    public void minus(Long price) {
        if(this.point - price < 0L) {
            throw new ApplicationException("포인트가 부족합니다.");
        }

        this.point -= price;
    }

    public void increase(Long point) {
        this.point += point;
    }
}
