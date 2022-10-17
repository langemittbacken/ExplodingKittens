package exceptions;

public class PlayerNotFoundException extends Exception {
   
   public PlayerNotFoundException(String errorMsg) {
      super(errorMsg);
   }
}
