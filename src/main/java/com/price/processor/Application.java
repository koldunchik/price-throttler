package com.price.processor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.AbstractMap;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }


  @Bean
  public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
    return args -> {

      PriceThrottler priceThrottler = new PriceThrottler();
      priceThrottler.subscribe(new FastestProcessor());
      priceThrottler.subscribe(new SlowProcessor());

      AbstractMap.SimpleEntry<String, Double> pairEURUSD = new AbstractMap.SimpleEntry<>("EURUSD", Math.random() * 2.8d);
      priceThrottler.onPrice(pairEURUSD.getKey(), pairEURUSD.getValue());

      Thread.sleep(2000);
      System.exit(0);

    };
  }

}
