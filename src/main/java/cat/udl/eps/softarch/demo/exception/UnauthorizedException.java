package cat.udl.eps.softarch.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.UNAUTHORIZED, reason = "Unauthorized")
public class UnauthorizedException  extends RuntimeException{
}



