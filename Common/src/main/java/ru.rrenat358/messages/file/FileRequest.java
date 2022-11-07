package ru.rrenat358.messages.file;

import lombok.Value;
import ru.geekbrains.cloud.common.messages.abs.AbstractMessage;

@Value
public class FileRequest extends AbstractMessage {

  String fileName;
  String path;

}
