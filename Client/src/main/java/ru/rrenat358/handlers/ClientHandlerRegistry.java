package ru.rrenat358.handlers;

import ru.rrenat358.reg.abstractClass.AbstractMessage;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class ClientHandlerRegistry {

  private static final Map<Class<? extends AbstractMessage>, ClientRequestHandler> REQUEST_HANDLER_MAP;

  static {
    Map<Class<? extends AbstractMessage>, ClientRequestHandler> requestHandlerMap = new HashMap<>();
//    requestHandlerMap.put(ListResponse.class, new ListResponseHandler());
//    requestHandlerMap.put(RegErrorResponse.class, new RegErrorResponseHandler());
//    requestHandlerMap.put(RegSuccessResponse.class, new RegSuccessResponseHandler());
//    requestHandlerMap.put(AuthErrorResponse.class, new AuthErrorResponseHandler());
//    requestHandlerMap.put(AuthSuccessResponse.class, new AuthSuccessResponseHandler());
//    requestHandlerMap.put(FileMessage.class, new FileDownloadHandler());
//    requestHandlerMap.put(FileErrorResponse.class, new FileErrorResponseHandler());

    REQUEST_HANDLER_MAP = Collections.unmodifiableMap(requestHandlerMap);
  }

  public static ClientRequestHandler getHandler(Class<?> messageClass) {
    return REQUEST_HANDLER_MAP.get(messageClass);
  }
}
