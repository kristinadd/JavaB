public class InsufficientFundsException extends RuntimeException { // Exception is checked; RuntimeException is unchecked
  public InsufficientFundsException(String message) {
      super(message);
  }
}

// By extending RuntimeException, InsufficientFundsException becomes an 
// unchecked exception. This means that the compiler doesn't require you 
// to catch or declare it in your method signatures.

