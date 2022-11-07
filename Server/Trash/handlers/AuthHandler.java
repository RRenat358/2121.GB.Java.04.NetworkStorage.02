package ru.rrenat358.handlers;

import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ru.rrenat358.messages.auth.AuthErrorResponse;
import ru.rrenat358.messages.auth.AuthRequest;
import ru.rrenat358.messages.auth.AuthSuccessResponse;
import ru.rrenat358.service.AuthService;
import ru.rrenat358.service.ClientService;

@Log4j2
@AllArgsConstructor
public class AuthHandler implements ServerRequestHandler {

  private AuthService authService;

  @Override
  public void handle(ChannelHandlerContext ctx, Object msg, ClientService clientService) {
    AuthRequest authRequest = (AuthRequest) msg;

    String login = authRequest.getLogin();
    String password = authRequest.getPassword();

    if (authService.authUser(login, password)) {
      clientService.setAuthorized(true);
      ctx.writeAndFlush(new AuthSuccessResponse(login)).addListener(channelFuture -> {
        if (channelFuture.isSuccess()) {
          log.info(ctx.name() + "- AuthSuccessResponse sent: " + login);
        }
      });
    } else {
      String reason = "Invalid login/password";

      ctx.writeAndFlush(new AuthErrorResponse(reason)).addListener(channelFuture -> {
        if (channelFuture.isSuccess()) {
          log.info(ctx.name() + "- AuthErrorResponse sent: " + reason);
        }
      });
    }
  }
}
