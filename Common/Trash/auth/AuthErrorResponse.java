package ru.rrenat358.auth;


import ru.rrenat358.abstractClass.ServerErrorResponse;


public class AuthErrorResponse extends ServerErrorResponse {

  public AuthErrorResponse(String reason) {
    super(reason);
  }
}
