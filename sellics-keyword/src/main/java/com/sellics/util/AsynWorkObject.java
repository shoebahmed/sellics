package com.sellics.util;

import java.util.List;
import java.util.concurrent.Callable;
import lombok.Builder;

@Builder
public class AsynWorkObject implements Callable<Long> {

  private AWSConnectionUtil awsConnectionUtil;

  private String keywordCombination;

  private List<String> keyword;
  
  /**
   * Callable, here asynchronous call are made to AWS completion API.
   * The response is parsed and checked for keywords present.
   * If keyword is found in response than score is incremented by 1.
   */
  @Override
  public Long call() throws Exception {

    List<String> keywords = awsConnectionUtil.getOrderedKeyword(keywordCombination);
    Long count = keywords.stream().filter(this::checkKeyword).count();
    return count;
  }

  private boolean checkKeyword(String keyword) {
    return this.keyword.stream().anyMatch(k -> keyword.toLowerCase().indexOf(k) > 0);
  }
}
