package ru.rrenat358.messages.auth;

import lombok.Value;
import ru.rrenat358.messages.abs.AbstractMessage;

@Value
public class AuthRequest extends AbstractMessage {

  private String login;
  private String password;
}
