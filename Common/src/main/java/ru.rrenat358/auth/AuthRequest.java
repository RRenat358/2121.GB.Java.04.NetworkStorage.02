package ru.rrenat358.auth;

import lombok.Value;
import ru.geekbrains.cloud.common.messages.abs.AbstractMessage;

@Value
public class AuthRequest extends AbstractMessage {

  private String login;
  private String password;
}
