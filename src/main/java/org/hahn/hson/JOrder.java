package org.hahn.hson;

/**
 * Created by jianghan on 2016/11/30.
 */
public enum JOrder {
  INCR("I", "incr"), DESC("D", "desc");

  private String orderSign;
  private String type;

  JOrder(String orderSign, String type) {
    this.orderSign = orderSign;
    this.type = type;
  }

  public static JOrder parse(String type) {
    for (JOrder t : values()) {
      if (t.getType().equalsIgnoreCase(type)) {
        return t;
      }
    }
    throw new IllegalArgumentException("Invalid JOrder: ".concat(type));
  }

  public static JOrder parseFromSign(String sign) {
    for (JOrder t : values()) {
      if (t.getOrderSign().equalsIgnoreCase(sign)) {
        return t;
      }
    }
    throw new IllegalArgumentException("Invalid JOrder: ".concat(sign));
  }

  public String getOrderSign() {
    return orderSign;
  }

  public String getType() {
    return type;
  }

}
