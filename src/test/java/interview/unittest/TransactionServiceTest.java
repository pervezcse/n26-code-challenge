package interview.unittest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import interview.transaction.entity.Transaction;
import interview.transaction.service.StatisticsService;
import interview.transaction.service.TransactionService;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TransactionServiceTest {

  @MockBean
  private StatisticsService statisticsService;
  @Resource
  private TransactionService transactionService;
  
  @Test
  public void givenTransaction_whenUpdateStatistics_thenReturnSuccess() {
    Transaction tx = new Transaction(10.0, System.currentTimeMillis());
    when(statisticsService.updateStatistics(tx)).thenReturn(true);
    boolean result = transactionService.create(tx);
    assertThat(result).isEqualTo(true);
  }
}
