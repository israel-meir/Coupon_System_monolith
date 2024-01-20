package israel.project.controller.advice;

import israel.project.controller.CompanyController;
import israel.project.service.ex.UnlikelyDateException;
import israel.project.service.ex.LimitedRemovalException;
import israel.project.service.ex.TheCouponAlreadyExistsException;
import israel.project.service.ex.NoSuchCouponException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(assignableTypes = CompanyController.class)
@ResponseBody
public class CompanyControllerAdvice {

    @ExceptionHandler(TheCouponAlreadyExistsException.class)
    public ProblemDetail handelTheCouponAlreadyExistsException(TheCouponAlreadyExistsException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(NoSuchCouponException.class)
    public ProblemDetail handleNoSuchCouponException(NoSuchCouponException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgumentException(IllegalArgumentException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(LimitedRemovalException.class)
    public ProblemDetail handleLimitedRemovalException(LimitedRemovalException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(UnlikelyDateException.class)
    public ProblemDetail handleAnUnlikelyDateExceptio(UnlikelyDateException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
}
