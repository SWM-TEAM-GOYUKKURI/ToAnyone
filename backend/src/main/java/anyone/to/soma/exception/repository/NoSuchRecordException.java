package anyone.to.soma.exception.repository;

import anyone.to.soma.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class NoSuchRecordException extends ApplicationException {

    public NoSuchRecordException() {
        super("데이터베이스에 해당 항목이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
    }


}
