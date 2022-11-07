package ru.rrenat358.messages.file;

import ru.geekbrains.cloud.common.messages.abs.AbstractFileMessage;

public class DeleteRequest extends AbstractFileMessage {

  public DeleteRequest(String fileName, String path) {
    super(fileName, path);
  }
}
