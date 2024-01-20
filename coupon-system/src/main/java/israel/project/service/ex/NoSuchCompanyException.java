package israel.project.service.ex;

public class NoSuchCompanyException extends RuntimeException {
    public NoSuchCompanyException(String massage) {
        super(massage);
    }
}
