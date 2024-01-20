package israel.project.service.ex;

public class LimitedRemovalException extends RuntimeException {
    public LimitedRemovalException(String massage) {
        super(massage);
    }
}
