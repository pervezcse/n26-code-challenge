package interview.transaction.service;

import interview.transaction.entity.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {

  private final StatisticsService statisticsService;

  @Value("${transaction.validityperiod.millis}")
  private Long transactionValidityPeriod;

  public Boolean isTransactionValid(Transaction transaction) {
    return transaction.isValid(transactionValidityPeriod);
  }

  public boolean create(Transaction transaction) {
    return statisticsService.updateStatistics(transaction);
  }

}
