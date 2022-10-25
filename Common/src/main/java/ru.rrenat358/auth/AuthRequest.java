package ru.rrenat358.auth;

import lombok.Value;
import ru.rrenat358.reg.abstractClass.AbstractMessage;

@Value
public class AuthRequest extends AbstractMessage {

  private String login;
  private String password;
}
