package ru.rrenat358.messages.reg;

import ru.rrenat358.messages.abs.ServerErrorResponse;

public class RegErrorResponse extends ServerErrorResponse {

  public RegErrorResponse(String reason) {
    super(reason);
  }
}
