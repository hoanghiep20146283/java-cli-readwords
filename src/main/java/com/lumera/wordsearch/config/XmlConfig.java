package com.lumera.wordsearch.config;

import com.lumera.wordsearch.constant.ProcessorType;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class XmlConfig implements Serializable {

  private static final long serialVersionUID = -2876047373968338587L;
  private List<CmdOptionConfig> cmdOptionConfigs;

  @Getter
  @Setter
  @NoArgsConstructor
  public static class CmdOptionConfig implements Serializable {

    private static final long serialVersionUID = -2905574804235011603L;
    private String name;
    private String paramLabel;
    private String description;
    private String optionValueType;
    private ProcessorType processorType;
    private String converterType;
  }
}
