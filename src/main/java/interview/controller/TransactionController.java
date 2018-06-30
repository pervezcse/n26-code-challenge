package interview.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import interview.transaction.entity.Transaction;
import interview.transaction.service.TransactionService;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/transactions")
@RequiredArgsConstructor
public class TransactionController {

  private final TransactionService transactionService;

  /**
   * Create transaction if valid otherwise send 204.
   * @param transaction to be processed
   * @return
   */
  @PostMapping
  public ResponseEntity<Void> create(@Valid @NotNull @RequestBody Transaction transaction) {
    if (transactionService.isTransactionValid(transaction)) {
      transactionService.create(transaction);
      return new ResponseEntity<>(CREATED);
    } else {
      return new ResponseEntity<>(NO_CONTENT);
    }
  }
}
