package ru.rrenat358.file;

import ru.rrenat358.abstractClass.AbstractFileMessage;

public class DeleteRequest extends AbstractFileMessage {

  public DeleteRequest(String fileName, String path) {
    super(fileName, path);
  }
}
