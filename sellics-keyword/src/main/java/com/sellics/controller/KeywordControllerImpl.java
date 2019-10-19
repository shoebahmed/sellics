package com.sellics.controller;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.sellics.dto.ScoreDTO;
import com.sellics.util.AWSConnectionUtil;
import com.sellics.util.AsynWorkObject;
import com.sellics.util.KeywordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequiredArgsConstructor
@Slf4j
public class KeywordControllerImpl implements KeywordController {

  private final AWSConnectionUtil awsConnectionUtil;
  
  private final ExecutorService executorService = Executors.newWorkStealingPool(10);
  
  private final Long OFFSET = 50L;

  public Mono<ResponseEntity<ScoreDTO>> getKeywordScore(String keyword) {
    
    return Mono.just(keyword).publishOn(Schedulers.single()).map(subject -> {

      List<String> keywordCombination = KeywordUtil.split(subject);
      List<Future<Long>> futureList = new ArrayList<Future<Long>>();

      for (String kc : keywordCombination) {
        AsynWorkObject workObject = AsynWorkObject.builder().awsConnectionUtil(awsConnectionUtil)
            .keyword(Arrays.asList(keyword.toLowerCase().split(" "))).keywordCombination(kc)
            .build();
        Future<Long> future = executorService.submit(workObject);
        futureList.add(future);
      }

      Long score = futureList.stream().map(f -> {
        try {
          return f.get();
        } catch (Exception e) {
          log.error("Unable to get response from AWS service.");
        }
        return null;
      }).collect(Collectors.summingLong(Long::longValue));

      return new ResponseEntity<>(ScoreDTO.builder().keyword(keyword).score(OFFSET + score).build(),
          HttpStatus.OK);
    }).timeout(Duration.ofSeconds(10)).onErrorReturn(new ResponseEntity<>(
        ScoreDTO.builder().keyword(keyword).score(new Long("0")).build(), HttpStatus.OK));
  }
}
