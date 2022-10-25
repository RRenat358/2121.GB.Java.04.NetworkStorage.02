package ru.rrenat358.list;

import lombok.Value;
import ru.rrenat358.abstractClass.AbstractMessage;

import java.util.List;

@Value
public class ListResponse extends AbstractMessage {

  private List<FileInfo> list;
  private String path;
}
