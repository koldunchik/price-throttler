package com.price.processor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApplicationTest {

  @Test
  void testLastPrice() {
    PriceThrottler priceThrottler = new PriceThrottler();

    PriceProcessor fastestSubscriber = new FastestProcessor();
    PriceProcessor slowSubscriber = new SlowProcessor();
    PriceProcessor fastSubscriber = new FastProcessor();

    priceThrottler.subscribe(fastestSubscriber);
    priceThrottler.subscribe(slowSubscriber);
    priceThrottler.subscribe(fastSubscriber);

    for (int i = 0; i< 10000; i++) {
      priceThrottler.onPrice("EURUSD", i * 1.0d);
    }

    try {
      Thread.sleep(1000 * 2);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    assertEquals(9999.0d, priceThrottler.priceProcessorsData.get(slowSubscriber).lastPair.getRate());
    assertEquals(9999.0d, priceThrottler.priceProcessorsData.get(fastSubscriber).lastPair.getRate());
    assertEquals(9999.0d, priceThrottler.priceProcessorsData.get(fastestSubscriber).lastPair.getRate());

  }
}