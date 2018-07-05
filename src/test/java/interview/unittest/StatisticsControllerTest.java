package interview.unittest;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import interview.controller.StatisticsController;
import interview.transaction.entity.Statistics;
import interview.transaction.entity.Transaction;
import interview.transaction.service.StatisticsService;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(StatisticsController.class)
public class StatisticsControllerTest {

  @MockBean
  private StatisticsService service;
  @Resource
  private MockMvc mvc;

  @Test
  public void givenMultipleTransactionsInLast60Seconds_whenGetStatistics_thenReturnStatistics()
      throws Exception {

    List<Transaction> txList = LongStream.range(0, 10)
        .mapToObj(a -> new Transaction((double) a, System.currentTimeMillis()))
        .collect(Collectors.toList());
    Statistics stat = Statistics.builder(txList).build();
    given(service.getStatistics()).willReturn(stat);
    mvc.perform(MockMvcRequestBuilders.get("/statistics").contentType(APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(jsonPath("$.sum", is(stat.getSum())))
        .andExpect(jsonPath("$.avg", is(stat.getAvg())))
        .andExpect(jsonPath("max", is(stat.getMax())))
        .andExpect(jsonPath("$.min", is(stat.getMin())))
        .andExpect(jsonPath("$.count", is(stat.getCount().intValue())));
  }
}
