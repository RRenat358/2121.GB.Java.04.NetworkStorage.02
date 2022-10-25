package ru.rrenat358.reg;

import lombok.Value;
import ru.rrenat358.reg.abstractClass.AbstractMessage;

@Value
public class RegRequest extends AbstractMessage {

  String login;
  String password;
  int capacity;
}
