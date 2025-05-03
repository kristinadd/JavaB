package domain;

// prefer checked exception over unchecked
public class InsufficientFundsException extends Exception { // Exception is checked; RuntimeException is unchecked
  public InsufficientFundsException(String message) { 
      super(message);
  }
}

// By extending Exception, InsufficientFundsException becomes a 
// checked exception. This means that the compiler requires you 
// to catch or declare it in your method signatures.

