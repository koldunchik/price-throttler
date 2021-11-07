package com.price.processor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;

public class PriceProcessorData {

  CompletableFuture<Void> task;
  LinkedBlockingQueue<CurrencyRatePair> ccyQueue;
  public CurrencyRatePair lastPair = null;

  public PriceProcessorData() {
    this.task = null;
    this.ccyQueue = new LinkedBlockingQueue<>(1);
  }

  public CompletableFuture<Void> getTask() {
    return task;
  }

  public void setTask(CompletableFuture<Void> task) {
    this.task = task;
  }

  public CurrencyRatePair getPair() {
    CurrencyRatePair pair = ccyQueue.poll();
    if (pair != null) {
      lastPair = pair;
    }
    return pair;
  }

  public void addPair(CurrencyRatePair currencyRatePair) {
    while (!ccyQueue.offer(currencyRatePair)) {
      ccyQueue.poll();
    }
  }

}
