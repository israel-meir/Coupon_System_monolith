package israel.project.service.ex;

public class TheCouponAlreadyExistsException extends RuntimeException {
    public TheCouponAlreadyExistsException(String massage) {
        super(massage);
    }
}
