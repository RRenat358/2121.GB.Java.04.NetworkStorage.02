package ru.rrenat358.file;

import ru.rrenat358.abstractClass.AbstractFileMessage;

public class MakeDirRequest extends AbstractFileMessage {

  public MakeDirRequest(String fileName, String path) {
    super(fileName, path);
  }
}
