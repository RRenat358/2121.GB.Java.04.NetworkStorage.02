package ru.rrenat358.list;

import lombok.Value;
import ru.rrenat358.abstractClass.AbstractMessage;

@Value
public class ListRequest extends AbstractMessage {

  private String path;

}
