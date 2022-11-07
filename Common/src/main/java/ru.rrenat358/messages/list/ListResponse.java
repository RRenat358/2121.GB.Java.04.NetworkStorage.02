package ru.rrenat358.messages.list;

import lombok.Value;
import ru.geekbrains.cloud.common.messages.abs.AbstractMessage;

import java.util.List;

@Value
public class ListResponse extends AbstractMessage {

  private List<FileInfo> list;
  private String path;
}
