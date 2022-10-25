package ru.rrenat358.auth;

import lombok.Value;
import ru.rrenat358.reg.abstractClass.AbstractMessage;

@Value
public class AuthSuccessResponse extends AbstractMessage {

  private String login;

}
