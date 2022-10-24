package ru.rrenat358.abstractClass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.rrenat358.AbstractMessage;

@AllArgsConstructor
public abstract class ServerErrorResponse extends AbstractMessage {

  @Getter
  private String reason;
}
