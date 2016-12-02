package org.hahn.hson;

/**
 * Created by jianghan on 2016/11/23.
 */
public class JSONMeta {

  private String field;
  private JType type;
  private int offset;
  private JType kType;
  private JType vType;
  private JOrder order;

  public JSONMeta(String field, JType type) {
    this.field = field;
    this.type = type;
  }

  public void setTupleListType(JType kType, JType vType, JOrder order) {
    this.kType = kType;
    this.vType = vType;
    this.order = order;
  }

  public void setOffset(int offset) {
    this.offset = offset;
  }

  public String getField() {
    return field;
  }

  public JType getType() {
    return type;
  }

  public int getOffset() {
    return offset;
  }

  public JType getkType() {
    return kType;
  }

  public JType getvType() {
    return vType;
  }

  public JOrder getOrder() {
    return order;
  }

}
