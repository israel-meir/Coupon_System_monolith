package israel.project.service.ex;

public class CouponAlreadyExistsException extends RuntimeException {
    public CouponAlreadyExistsException(String massage) {
        super(massage);
    }
}
