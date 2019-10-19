package com.sellics.util;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("awsConnectionUtil")
public class AWSConnectionUtil extends RestTemplate {

  String URL =
      "https://completion.amazon.com/search/complete?search-alias=aps&client=amazon-search-ui&mkt=1&q=";
  
  /**
   * To make a Get request to AWS API and get response.
   * @param keyword
   * @return
   */
  public List<String> getOrderedKeyword(String keyword) {

    String reponse = getForObject(URL + keyword, String.class);
    return parseResponse(reponse);
  }
  
  /**
   * Parse the response returned from AWS Completion API. 
   * Response string was not a proper JSON so, have to work on 
   * fixlength to parse this response.
   * @param response
   * @return
   */
  private List<String> parseResponse(String response) {

    String[] keywords = response
        .substring(response.indexOf("[", response.indexOf("[") + 1) + 1, response.indexOf("]"))
        .replace("\"", "").split(",");
    return Arrays.asList(keywords);
  }
}
