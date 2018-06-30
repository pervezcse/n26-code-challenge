package interview.transaction.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Statistics {

  private final Double sum;
  private final Double avg;
  private final Double max;
  private final Double min;
  private final Long count;
  
  public static StatisticsBuilder builder(Transaction transaction) {
    return new StatisticsBuilder(transaction);
  }
  
  public static Statistics getNullInstance() {
    return new StatisticsBuilder().build();
  }

  public static class StatisticsBuilder {

    private Double sum;
    private Double avg;
    private Double max;
    private Double min;
    private Long count;

    private StatisticsBuilder() {
      this.sum = 0.0;
      this.count = 0L;
      this.avg = 0.0;
      this.max = 0.0;
      this.min = 0.0;
    }
    
    private StatisticsBuilder(Transaction transaction) {
      this.sum = transaction.getAmount();
      this.count = 1L;
      this.avg = this.sum / this.count;
      this.max = transaction.getAmount();
      this.min = transaction.getAmount();
    }

    /**
     * Update statistics using transaction data.
     * @param transaction data for updating statistics
     * @return
     */
    public StatisticsBuilder transaction(Transaction transaction) {
      this.sum += transaction.getAmount();
      this.count++;
      this.avg = this.sum / this.count;
      this.max = this.max >= transaction.getAmount() ? this.max : transaction.getAmount();
      this.min = this.min <= transaction.getAmount() ? this.min : transaction.getAmount();
      return this;
    }

    /**
     * Update statistics using other statistics data.
     * @param statistics data for updating this statistics data
     * @return
     */
    public StatisticsBuilder statistics(Statistics statistics) {
      this.sum += statistics.sum;
      this.count += statistics.count;
      this.avg = this.sum / this.count;
      this.max = this.max >= statistics.max ? this.max : statistics.max;
      this.min = this.min <= statistics.min ? this.min : statistics.min;
      return this;
    }

    public Statistics build() {
      Statistics statistics = new Statistics(this.sum, this.avg, this.max, this.min, this.count);
      return statistics;
    }
  }
}
