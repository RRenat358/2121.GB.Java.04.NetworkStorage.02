package ru.rrenat358.messages.list;

import lombok.Value;
import ru.rrenat358.messages.abs.AbstractMessage;

@Value
public class ListRequest extends AbstractMessage {

  private String path;

}
