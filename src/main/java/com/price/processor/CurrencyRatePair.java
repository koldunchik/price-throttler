package com.price.processor;

final class CurrencyRatePair {

  private final String ccyPair;
  private final double rate;

  public CurrencyRatePair(String ccyPair, double rate) {
    this.ccyPair = ccyPair;
    this.rate = rate;
  }

  public String getCcyPair() {
    return ccyPair;
  }

  public double getRate() {
    return rate;
  }

  @Override
  public String toString() {
    return String.format("CurrencyRatePair {ccyPair %s rate= %f}", ccyPair, rate);
  }
}
