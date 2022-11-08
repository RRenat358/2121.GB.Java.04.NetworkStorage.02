package ru.rrenat358.messages.file;

import ru.rrenat358.messages.abs.ServerErrorResponse;

public class FileErrorResponse extends ServerErrorResponse {

  public FileErrorResponse(String reason) {
    super(reason);
  }
}
