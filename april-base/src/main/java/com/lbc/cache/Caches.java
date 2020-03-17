package com.lbc.cache;

import java.util.concurrent.TimeUnit;


public enum Caches {

  domainItem(30, TimeUnit.SECONDS, 1000),
  ;

  private int expire;
  private TimeUnit timeUnit;
  private int maximumSize;

  Caches(int expire, TimeUnit timeUnit, int maximumSize) {
    this.expire = expire;
    this.timeUnit = timeUnit;
    this.maximumSize = maximumSize;
  }

  public int getExpire() {
    return expire;
  }

  public TimeUnit getTimeUnit() {
    return timeUnit;
  }

  public int getMaximumSize() {
    return maximumSize;
  }
}
