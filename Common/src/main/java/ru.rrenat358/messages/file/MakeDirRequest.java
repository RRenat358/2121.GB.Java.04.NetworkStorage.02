package ru.rrenat358.messages.file;

import ru.rrenat358.messages.abs.AbstractFileMessage;

public class MakeDirRequest extends AbstractFileMessage {

  public MakeDirRequest(String fileName, String path) {
    super(fileName, path);
  }
}
