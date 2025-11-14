package me.blackwater.blogsai2.domain.exception;

public class InvalidCountryCodeException extends RuntimeException {
  public InvalidCountryCodeException(String message) {
    super(message);
  }
}
