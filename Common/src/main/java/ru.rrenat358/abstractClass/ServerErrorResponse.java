package ru.rrenat358.abstractClass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public abstract class ServerErrorResponse extends AbstractMessage {

  @Getter
  private String reason;

  public static class AuthErrorResponse extends ServerErrorResponse {

    public AuthErrorResponse(String reason) {
      super(reason);
    }
  }
}
