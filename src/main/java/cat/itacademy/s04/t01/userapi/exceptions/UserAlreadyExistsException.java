package cat.itacademy.s04.t01.userapi.exceptions;

public class UserAlreadyExisstException extends RuntimeException {
  public UserAlreadyExisstException(String message) {
    super(message);
  }
}
