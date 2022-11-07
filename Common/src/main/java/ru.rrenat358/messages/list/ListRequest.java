package ru.rrenat358.messages.list;

import lombok.Value;
import ru.geekbrains.cloud.common.messages.abs.AbstractMessage;

@Value
public class ListRequest extends AbstractMessage {

  private String path;

}
