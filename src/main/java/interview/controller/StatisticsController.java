package interview.controller;

import interview.transaction.entity.Statistics;
import interview.transaction.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

  private final StatisticsService statisticsService;

  @GetMapping
  public Statistics getStatistics() {
    return statisticsService.getStatistics();
  }

}
