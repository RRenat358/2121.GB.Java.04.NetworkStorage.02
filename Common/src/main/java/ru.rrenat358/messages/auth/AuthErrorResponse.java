package ru.rrenat358.messages.auth;

import ru.rrenat358.messages.abs.ServerErrorResponse;

public class AuthErrorResponse extends ServerErrorResponse {

  public AuthErrorResponse(String reason) {
    super(reason);
  }
}
