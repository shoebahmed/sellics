package com.sellics.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ScoreDTO {

  String keyword;

  Long score;
}
