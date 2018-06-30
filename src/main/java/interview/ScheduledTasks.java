package interview;

import interview.transaction.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {

  private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
  private final StatisticsService statisticsService;

  /**
   * Scheduled cleanup for preventing statistics repository from being too big.
   */
  @Scheduled(fixedDelay = 3 * 60 * 1000, initialDelay = 3 * 60 * 1000)
  public void reportCurrentTime() {
    Long currentTimeStamp = System.currentTimeMillis();
    Long fromTileStamp = currentTimeStamp - 5 * 60 * 1000;
    log.info("Starting scheduled cleanup at: " + currentTimeStamp);
    statisticsService.cleanUp(fromTileStamp);
    log.info("Finished scheduled cleanup at: " + System.currentTimeMillis());
  }
}