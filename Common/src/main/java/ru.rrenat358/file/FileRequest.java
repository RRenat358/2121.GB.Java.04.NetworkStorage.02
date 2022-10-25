package ru.rrenat358.file;

import lombok.Value;
import ru.rrenat358.abstractClass.AbstractMessage;

@Value
public class FileRequest extends AbstractMessage {

  String fileName;
  String path;

}
