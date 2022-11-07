package ru.rrenat358.messages.abs;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public abstract class ServerErrorResponse extends AbstractMessage {

  @Getter
  private String reason;
}
