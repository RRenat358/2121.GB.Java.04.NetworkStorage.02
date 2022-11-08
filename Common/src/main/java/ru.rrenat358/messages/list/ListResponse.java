package ru.rrenat358.messages.list;

import lombok.Value;
import ru.rrenat358.messages.abs.AbstractMessage;

import java.util.List;

@Value
public class ListResponse extends AbstractMessage {

  private List<FileInfo> list;
  private String path;
}
