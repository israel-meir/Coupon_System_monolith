package israel.project.service.ex;

public class NoSuchCouponException extends RuntimeException {
    public NoSuchCouponException(String massage) {
        super(massage);
    }
}
