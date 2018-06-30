package interview;

import interview.transaction.entity.Statistics;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

@SpringBootApplication
@EnableScheduling
public class TransactionServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(TransactionServiceApplication.class, args);
  }

  @Bean
  public Map<Long, Statistics> statsRepository() {
    return new ConcurrentHashMap<Long, Statistics>();
  }

  @Bean
  public TaskScheduler taskExecutor() {
    return new ConcurrentTaskScheduler(Executors.newScheduledThreadPool(3));
  }
}
