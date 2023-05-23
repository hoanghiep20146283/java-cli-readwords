package com.lumera.wordsearch.config;

import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class XmlConfig implements Serializable {

  private List<CmdOptionConfig> cmdOptionConfigs;

  @Getter
  @Setter
  @NoArgsConstructor
  public static class CmdOptionConfig implements Serializable {

    private String name;
    private String paramLabel;
    private String description;
    private String type;
    private String processorType;
  }
}
