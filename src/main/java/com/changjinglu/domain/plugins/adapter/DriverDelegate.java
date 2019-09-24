package com.changjinglu.domain.plugins.adapter;

import lombok.Getter;
import lombok.experimental.Delegate;

import java.sql.Driver;
/**
 * <p> DriverDelegate </p>
 * <p> 数据库驱动代理 </p>
 * @since 2019/9/24 10:25 上午
 * @author fengxiao
 */
public class DriverDelegate implements Driver {

  @Delegate
  @Getter
  private final Driver driver;

  public DriverDelegate(Driver driver) {
    this.driver = driver;
  }
}
