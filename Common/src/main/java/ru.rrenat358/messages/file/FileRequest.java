package ru.rrenat358.messages.file;

import lombok.Value;
import ru.rrenat358.messages.abs.AbstractMessage;

@Value
public class FileRequest extends AbstractMessage {

  String fileName;
  String path;

}
