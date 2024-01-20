package israel.project.service.ex;

public class NoSuchCustomerException extends RuntimeException {
    public NoSuchCustomerException(String massage) {
        super(massage);
    }
}
