package ru.rrenat358.reg;

import ru.geekbrains.cloud.common.messages.abs.ServerErrorResponse;

public class RegErrorResponse extends ServerErrorResponse {

  public RegErrorResponse(String reason) {
    super(reason);
  }
}
