package ru.rrenat358.messages.abs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class AbstractFileMessage extends AbstractMessage{

  private String fileName;
  private String path;

}
