package interview.transaction.service;

import interview.transaction.entity.Statistics;
import interview.transaction.entity.Transaction;
import java.util.Map;
import java.util.Optional;
import java.util.stream.LongStream;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsService {

  @Value("${transaction.validityperiod.millis}")
  private Long transactionValidityPeriod;

  private final Map<Long, Statistics> statsRepository;

  /**
   * Update or create 60 seconds statistics of a given timestamp from a valid transaction.
   * 
   * @param transaction
   *          from which statistics will be created or updated.
   */
  public boolean updateStatistics(Transaction transaction) {
    Long txStartTimeStamp = transaction.getTimestamp();
    long txEndTimeStamp = transaction.getTimestamp() + transactionValidityPeriod;
    for (long timeStamp = txStartTimeStamp; timeStamp <= txEndTimeStamp; timeStamp++) {
      this.statsRepository.compute(timeStamp,
          (k, v) -> (v == null) ? Statistics.builder(transaction).build()
              : Statistics.builder(transaction).statistics(v).build());
    }
    return true;
  }

  /**
   * Get statistics of a given timestamp.
   * 
   * @return
   */
  public Statistics getStatistics() {
    Long currentTimeStamp = System.currentTimeMillis();
    Statistics stats = statsRepository.get(currentTimeStamp);
    return Optional.ofNullable(stats).orElse(Statistics.getNullInstance());
  }

  /**
   * Cleanup statistics repository from unwanted data.
   * 
   * @param fromTimeStamp
   *          timestamp from where cleanup will start
   */
  public void cleanUp(Long fromTimeStamp) {
    if (fromTimeStamp != null && fromTimeStamp > 0) {
      Long curentTimeStamp = System.currentTimeMillis();
      Long safetyPad = 61000L;
      Long toTimeStamp = curentTimeStamp - safetyPad;
      if (fromTimeStamp < toTimeStamp) {
        LongStream.range(fromTimeStamp, toTimeStamp).forEach(t -> this.statsRepository.remove(t));
      }
    }
  }
}
