package interview.integrationtest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import interview.TransactionServiceApplication;
import interview.transaction.entity.Statistics;
import interview.transaction.entity.Transaction;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = TransactionServiceApplication.class)
@AutoConfigureMockMvc
public class IntegrationTest {

  @Resource
  private MockMvc mvc;

  @Test
  public void givenMultipleTransactionsInLast60Seconds_whenGetStatistics_thenReturnStatistics()
      throws Exception {
    List<Transaction> txList = LongStream.range(0, 10)
        .mapToObj(a -> new Transaction((double) a, System.currentTimeMillis()))
        .collect(Collectors.toList());
    assertThat(txList.size()).isEqualTo(10);
    Statistics stat = Statistics.builder(txList).build();
    for (Transaction tx : txList) {
      createTransaction(tx);
    }
    mvc.perform(MockMvcRequestBuilders.get("/statistics").contentType(APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(jsonPath("sum", is(stat.getSum())))
        .andExpect(jsonPath("avg", is(stat.getAvg())))
        .andExpect(jsonPath("max", is(stat.getMax())))
        .andExpect(jsonPath("min", is(stat.getMin())))
        .andExpect(jsonPath("count", is(stat.getCount().intValue()))).andDo(print());
  }

  private void createTransaction(Transaction tx) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    mvc.perform(MockMvcRequestBuilders.post("/transactions").contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(tx))).andExpect(status().isCreated()).andDo(print());
  }
}
