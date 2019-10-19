package com.sellics.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sellics.dto.ScoreDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = {"/api/estimate"})
@Api(value = "estimateapi", description = "API Operations for Keyword Estimate.",
    tags = {"Keyword Estimate API"})
public interface KeywordController {

  /**
   * Keyword search API, is used get score from AWS Completion API.
   * It breaks the word and hits the AWS service with various combination. 
   * @param keyword
   * @return
   */
  @ApiOperation(value = "Keyword API to generate score.")
  @GetMapping("/{keyword}")
  public Mono<ResponseEntity<ScoreDTO>> getKeywordScore(@PathVariable String keyword);
}
