package interview.transaction.entity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Transaction {

  @NotNull
  @Min(0)
  private final Double amount;
  @NotNull
  @Min(0)
  private final Long timestamp;

  public static Transaction getInstance(Double amount, Long timestamp) {
    return new Transaction(amount, timestamp);
  }

  public Boolean isValid(Long validityInterval) {
    return System.currentTimeMillis() - timestamp <= validityInterval;
  }
}
