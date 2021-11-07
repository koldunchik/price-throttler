package com.price.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PriceThrottler implements PriceProcessor {

  private final ExecutorService taskPool = Executors.newCachedThreadPool();
  public final ConcurrentHashMap<PriceProcessor, PriceProcessorData> priceProcessorsData = new ConcurrentHashMap<>();

  Logger logger = LoggerFactory.getLogger(PriceThrottler.class);

  @Override
  public void onPrice(String ccyPair, double rate) {
    for (Map.Entry<PriceProcessor, PriceProcessorData> entry : priceProcessorsData.entrySet()) {
      PriceProcessor priceProcessor = entry.getKey();
      PriceProcessorData priceProcessorData = entry.getValue();
      priceProcessorData.addPair(new CurrencyRatePair(ccyPair, rate));

      CompletableFuture<Void> task = priceProcessorData.getTask();

      if (task == null || task.isDone()) {
        Runnable runnableTask = createRunnable(priceProcessor, priceProcessorData);
        task = CompletableFuture.runAsync(runnableTask, taskPool);
        priceProcessorData.setTask(task);
      }
    }
  }

  private Runnable createRunnable(PriceProcessor priceProcessor, PriceProcessorData priceProcessorData) {
    return () -> {

      boolean isActive = true;

      while (isActive) {
        CurrencyRatePair pair = priceProcessorData.getPair();
        if (pair != null) {
          priceProcessor.onPrice(pair.getCcyPair(), pair.getRate());
        } else {
          isActive = false;
        }
      }

    };
  }

  @Override
  public void subscribe(PriceProcessor priceProcessor) {
    PriceProcessorData priceProcessorData = new PriceProcessorData();
    priceProcessorsData.put(priceProcessor, priceProcessorData);
  }

  @Override
  public void unsubscribe(PriceProcessor priceProcessor) {
    priceProcessorsData.remove(priceProcessor);
  }

}
