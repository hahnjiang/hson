package org.hahn.hson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jianghan on 2016/11/9.
 * <p>
 * field1:I/L/D/S/TupleList(S:D)
 * <p>
 * Assert.assertEquals(f1, "age|I,item_list|T|SDD,name|S,tag_list|T|SDD");
 * Assert.assertEquals(f2, "item_list|T|SLI");
 */
public class JSONSchema {

  private Map<String, JSONMeta> metaMap = new HashMap<>();

  public JSONSchema(String line) {
    String[] fields = line.split(",");
    for (String field : fields) {
      String[] cols = field.split("\\|");
      JSONMeta meta = new JSONMeta(cols[0], JType.parseFromSign(cols[1]));
      if (meta.getType() == JType.Tuple2List) {
        JType kType = JType.parseFromSign(cols[2].substring(0, 1));
        JType vType = JType.parseFromSign(cols[2].substring(1, 2));
        JOrder order = JOrder.parseFromSign(cols[2].substring(2, 3));
        meta.setTupleListType(kType, vType, order);
      }
      metaMap.put(meta.getField(), meta);
    }
  }

}
