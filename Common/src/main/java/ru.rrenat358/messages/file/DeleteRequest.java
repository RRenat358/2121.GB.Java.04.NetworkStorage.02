package ru.rrenat358.messages.file;

import ru.rrenat358.messages.abs.AbstractFileMessage;

public class DeleteRequest extends AbstractFileMessage {

  public DeleteRequest(String fileName, String path) {
    super(fileName, path);
  }
}
