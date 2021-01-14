package cn.chendahai.ray.auth;

import cn.chendahai.ray.security.SecurityException;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionErrorHandler {

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ErrorBody> error(SecurityException e) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        log.error("GlobalExceptionErrorHandler>>> requestUrl: {}", request.getRequestURI());
        getRequestParameter(request);
        log.error("发生SecurityException异常", e);
        return new ResponseEntity<>(
            ErrorBody.builder()
                .body(e.getMessage())
                .status(HttpStatus.UNAUTHORIZED.value())
                .build(),
            HttpStatus.UNAUTHORIZED
        );
    }

    public void getRequestParameter(HttpServletRequest request) {
        Enumeration parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = (String) parameterNames.nextElement();
            String value = request.getParameter(name);
            System.out.println(name + "=" + value);
        }
    }
}

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class ErrorBody {

    private String body;
    private int status;
}