package exceptions;

public class CardNotFoundException extends Exception {

   public CardNotFoundException(String errorMsg) {
      super(errorMsg);
   }
}
