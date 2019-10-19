package com.sellics.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.util.StringUtils;
import lombok.Builder;

@Builder
public class KeywordUtil {

  private static String[] words;

  private static List<String> keywords;

  public static List<String> split(String keyword) {

    keywords = new ArrayList<>();
    if (StringUtils.hasLength(keyword)) {
      words = keyword.split(" ");
      permutation(0);
    }

    return keywords;
  }

  private static void swap(int pos1, int pos2) {
    String temp = words[pos1];
    words[pos1] = words[pos2];
    words[pos2] = temp;
  }

  private static void permutation(int start) {
    String keyword = "";
    if (start != 0) {
      for (int i = 0; i < start; i++) {
        keyword += words[i] + "+";
      }
      keywords.add(keyword.substring(0, keyword.length() - 1));
    }

    for (int i = start; i < words.length; i++) {
      swap(start, i);
      permutation(start + 1);
      swap(start, i);
    }
  }
}
