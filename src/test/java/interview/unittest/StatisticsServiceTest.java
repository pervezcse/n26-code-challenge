package interview.unittest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import interview.transaction.entity.Statistics;
import interview.transaction.entity.Statistics.StatisticsBuilder;
import interview.transaction.entity.Transaction;
import interview.transaction.service.StatisticsService;
import java.util.Map;
import java.util.stream.LongStream;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class StatisticsServiceTest {

  @MockBean
  private Map<Long, Statistics> statsRepository;
  @Resource
  private StatisticsService statisticsService;

  @Test
  public void givenNoTransactionInLast60Seconds_whenGetStatistics_thenReturnNullStatistics() {
    when(statsRepository.get(any(Long.class))).thenReturn(Statistics.getNullInstance());
    Statistics result = statisticsService.getStatistics();
    assertThat(result.getSum()).isEqualTo(0.0);
    assertThat(result.getAvg()).isEqualTo(0.0);
    assertThat(result.getMax()).isEqualTo(0.0);
    assertThat(result.getMin()).isEqualTo(0.0);
    assertThat(result.getCount()).isEqualTo(0);
  }

  @Test
  public void givenMultipleTransactionInLast60Seconds_whenGetStatistics_thenReturnStatistics() {
    StatisticsBuilder statisticsBuilder = Statistics
        .builder(new Transaction(0.0, System.currentTimeMillis()));
    LongStream.range(1, 10).forEach(
        a -> statisticsBuilder.transaction(new Transaction((double)a, System.currentTimeMillis())));
    Statistics stat = statisticsBuilder.build();
    when(statsRepository.get(any(Long.class))).thenReturn(stat);
    Statistics result = statisticsService.getStatistics();
    assertThat(result.getSum()).isEqualTo(stat.getSum());
    assertThat(result.getAvg()).isEqualTo(stat.getAvg());
    assertThat(result.getMax()).isEqualTo(stat.getMax());
    assertThat(result.getMin()).isEqualTo(stat.getMin());
    assertThat(result.getCount()).isEqualTo(stat.getCount());
  }
}
