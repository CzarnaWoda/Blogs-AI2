package me.blackwater.blogsai2.domain.exception;

public class InvalidPhoneNumberException extends RuntimeException {
  public InvalidPhoneNumberException(String message) {
    super(message);
  }
}
