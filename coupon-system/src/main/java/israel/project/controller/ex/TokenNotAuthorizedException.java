package israel.project.controller.ex;

public class TokenNotAuthorizedException extends RuntimeException {
    public TokenNotAuthorizedException(String massage) {
        super(massage);
    }
}
