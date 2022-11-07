package ru.rrenat358.handlers;

import ru.rrenat358.messages.abs.AbstractMessage;
import ru.rrenat358.messages.auth.AuthRequest;
import ru.rrenat358.messages.file.DeleteRequest;
import ru.rrenat358.messages.file.FileMessage;
import ru.rrenat358.messages.file.FileRequest;
import ru.rrenat358.messages.file.MakeDirRequest;
import ru.rrenat358.messages.list.ListRequest;
import ru.rrenat358.messages.reg.RegRequest;
import ru.rrenat358.netty.NettyServer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ServerHandlerRegistry {

  private static final Map<Class<? extends AbstractMessage>, ServerRequestHandler> REQUEST_HANDLER_MAP;

  static {
    Map<Class<? extends AbstractMessage>, ServerRequestHandler> requestHandlerMap = new HashMap<>();

    requestHandlerMap.put(ListRequest.class, new ListRequestHandler());
    requestHandlerMap.put(AuthRequest.class, new AuthHandler(NettyServer.getAuthService()));
    requestHandlerMap.put(RegRequest.class, new RegHandler(NettyServer.getAuthService()));
    requestHandlerMap.put(FileMessage.class, new FileUploadHandler());
    requestHandlerMap.put(FileRequest.class, new FileRequestHandler());
    requestHandlerMap.put(DeleteRequest.class, new DeleteRequestHandler());
    requestHandlerMap.put(MakeDirRequest.class, new MakeDirRequestHandler());

    REQUEST_HANDLER_MAP = Collections.unmodifiableMap(requestHandlerMap);
  }

  public static ServerRequestHandler getHandler(Class<?> messageClass) {
    return REQUEST_HANDLER_MAP.get(messageClass);
  }
}
