package com.price.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FastestProcessor implements PriceProcessor {
  Logger logger = LoggerFactory.getLogger(FastestProcessor.class);

  @Override
  public void onPrice(String ccyPair, double rate) {
    CurrencyRatePair currencyRatePair = new CurrencyRatePair(ccyPair, rate);
    int sleepTime = 0;
    try {
      Thread.sleep(sleepTime);
    } catch (InterruptedException e) {
    }

    logger.info("Processed {} in {} ms", currencyRatePair, sleepTime);
  }

  @Override
  public void subscribe(PriceProcessor priceProcessor) {

  }

  @Override
  public void unsubscribe(PriceProcessor priceProcessor) {

  }
}
