package ru.rrenat358.messages.auth;

import lombok.Value;
import ru.geekbrains.cloud.common.messages.abs.AbstractMessage;

@Value
public class AuthSuccessResponse extends AbstractMessage {

  private String login;

}
