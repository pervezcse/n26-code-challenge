package interview.unittest;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import interview.controller.TransactionController;
import interview.transaction.entity.Transaction;
import interview.transaction.service.TransactionService;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

  @MockBean
  private TransactionService service;
  @Resource
  private MockMvc mvc;

  @Test
  public void givenValidTransaction_whenCreate_thenReturnCreatedSuccessfully() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    Transaction tx = new Transaction(10.0, System.currentTimeMillis());
    given(service.isTransactionValid(any(Transaction.class))).willReturn(true);
    given(service.create(any(Transaction.class))).willReturn(true);
    mvc.perform(MockMvcRequestBuilders.post("/transactions")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(tx)))
        .andExpect(status().isCreated());
  }
}
