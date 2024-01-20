package israel.project.service.ex;

public class NoCouponsForSaleException extends RuntimeException {
    public NoCouponsForSaleException(String massage) {
        super(massage);
    }
}
