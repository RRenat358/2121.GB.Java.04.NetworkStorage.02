package ru.rrenat358.messages.reg;

import lombok.Value;
import ru.rrenat358.messages.abs.AbstractMessage;

@Value
public class RegRequest extends AbstractMessage {

  String login;
  String password;
  int capacity;
}
