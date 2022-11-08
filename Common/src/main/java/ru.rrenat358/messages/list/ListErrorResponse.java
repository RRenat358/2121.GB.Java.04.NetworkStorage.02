package ru.rrenat358.messages.list;

import ru.rrenat358.messages.abs.ServerErrorResponse;

public class ListErrorResponse extends ServerErrorResponse {

  public ListErrorResponse(String reason) {
    super(reason);
  }
}
