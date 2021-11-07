package com.price.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FastProcessor implements PriceProcessor {
  Logger logger = LoggerFactory.getLogger(FastProcessor.class);

  @Override
  public void onPrice(String ccyPair, double rate) {
    CurrencyRatePair currencyRatePair = new CurrencyRatePair(ccyPair, rate);
    int sleepTime = 1;
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
