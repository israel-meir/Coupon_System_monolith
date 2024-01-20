package israel.project.controller.advice;

import israel.project.controller.CustomerController;
import israel.project.controller.ex.TokenNotAuthorizedException;
import israel.project.login.ex.SessionExpiredException;
import israel.project.service.ex.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice(assignableTypes = CustomerController.class)
@ResponseBody
public class CustomerControllerAdvice {

    @ExceptionHandler(NoSuchCustomerException.class)
    public ProblemDetail handleNoSuchCustomer(NoSuchCustomerException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(CouponAlreadyExistsException.class)
    public ProblemDetail handleCouponAlreadyExistsException(CouponAlreadyExistsException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(ThereAreNoCouponsException.class)
    public ProblemDetail handleThereAreNoCouponsException(ThereAreNoCouponsException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(TokenNotAuthorizedException.class)
    public ProblemDetail handleTokenNotAuthorizedException(TokenNotAuthorizedException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(SessionExpiredException.class)
    public ProblemDetail handleSessionExpiredException(SessionExpiredException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(NoCouponsForSaleException.class)
    public ProblemDetail handleNoCouponsForSaleException(NoCouponsForSaleException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }


}

