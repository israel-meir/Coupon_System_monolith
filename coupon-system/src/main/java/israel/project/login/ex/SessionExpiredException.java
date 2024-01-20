package israel.project.login.ex;

public class SessionExpiredException extends RuntimeException {
    public SessionExpiredException(String massage) {
        super(massage);
    }
}
