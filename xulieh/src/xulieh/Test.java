
package xulieh;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import org.phprpc.util.PHPSerializer;
import com.caucho.burlap.io.BurlapInput;
import com.caucho.burlap.io.BurlapOutput;
import com.caucho.hessian.io.Hessian2StreamingInput;
import com.caucho.hessian.io.Hessian2StreamingOutput;
import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.exadel.flamingo.flex.messaging.amf.io.AMF3Deserializer;
import com.exadel.flamingo.flex.messaging.amf.io.AMF3Serializer;
import com.googlecode.jsonplugin.JSONUtil;
import com.sillycat.easyserialize.model.User;
public class MainTest {
public static void runTest(int times, Object data, String info,
    String filename) throws IllegalAccessException,
    IllegalArgumentException, InvocationTargetException, IOException,
    ClassNotFoundException {
   Date start, end;
   long size = 0;
   long stime = 0;
   long dtime = 0;
   //综述
   StringBuilder sb = new StringBuilder();
   StringBuilder categories = new StringBuilder();
   //序列化时间
   StringBuilder dataset1 = new StringBuilder();
   //反序列化时间
   StringBuilder dataset2 = new StringBuilder();
   //空间
   StringBuilder dataset3 = new StringBuilder();
   sb
     .append("<graph xaxisname='Continent' yaxisname='Export' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='"
       + times
       + "次"
       + info
       + "序列化与反序列化的比较' shownames='1' showvalues='1' decimals='0' formatNumberScale='0' baseFont='Tahama' baseFontSize='12'>");
   categories.append("<categories>");
   dataset1.append("<dataset seriesname='序列化时间' color='FDC12E'>");
   dataset2.append("<dataset seriesname='反序列化时间' color='56B9F9'>");
   dataset3.append("<dataset seriesname='空间' color='C9198D'>");
   System.out
     .println("--------------------------------------------");
   System.out.println(times + "次" + info);
   //Java
   try {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(bos);
    oos.writeObject(data);
    oos.close();
    byte[] b = bos.toByteArray();
    size = b.length;
    ByteArrayInputStream bis = new ByteArrayInputStream(b);
    ObjectInputStream ois = new ObjectInputStream(bis);
    ois.readObject();
    ois.close();
    start = new Date();
    for (int i = 0; i < times; i++) {
     bos = new ByteArrayOutputStream();
     oos = new ObjectOutputStream(bos);
     oos.writeObject(data);
     oos.close();
    }
    end = new Date();
    stime = end.getTime() - start.getTime();
    start = new Date();
    for (int i = 0; i < times; i++) {
     bis = new ByteArrayInputStream(b);
     ois = new ObjectInputStream(bis);
     ois.readObject();
     ois.close();
    }
    end = new Date();
    dtime = end.getTime() - start.getTime();
    categories.append("<category name='Java' />");
    dataset1.append("<set value='" + stime + "'/>");
    dataset2.append("<set value='" + dtime + "'/>");
    dataset3.append("<set value='" + size + "'/>");
    System.out.println("Java - " + "时间：" + stime + "|" + dtime + " 长度："
      + size);
   } catch (Exception e) {
    System.out.println("Java 不支持该类型");
   }
   //PHPRPC
   try {
    PHPSerializer formator1 = new PHPSerializer();
    byte[] b = formator1.serialize(data);
    size = b.length;
    formator1.unserialize(b);
    start = new Date();
    for (int i = 0; i < times; i++) {
     formator1.serialize(data);
    }
    end = new Date();
    stime = end.getTime() - start.getTime();
    start = new Date();
    for (int i = 0; i < times; i++) {
     formator1.unserialize(b);
    }
    end = new Date();
    dtime = end.getTime() - start.getTime();
    categories.append("<category name='PHPRPC' />");
    dataset1.append("<set value='" + stime + "'/>");
    dataset2.append("<set value='" + dtime + "'/>");
    dataset3.append("<set value='" + size + "'/>");
    System.out.println("PHPRPC - " + "时间：" + stime + "|" + dtime
      + " 长度：" + size);
   } catch (Exception e) {
    System.out.println("PHPRPC 不支持该类型");
   }
   //Hessian
   try {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    HessianOutput oos = new HessianOutput(bos);
    oos.writeObject(data);
    oos.close();
    byte[] b = bos.toByteArray();
    size = b.length;
    ByteArrayInputStream bis = new ByteArrayInputStream(b);
    HessianInput ois = new HessianInput(bis);
    ois.readObject();
    ois.close();
    start = new Date();
    for (int i = 0; i < times; i++) {
     bos = new ByteArrayOutputStream();
     oos = new HessianOutput(bos);
     oos.writeObject(data);
     oos.close();
    }
    end = new Date();
    stime = end.getTime() - start.getTime();
    start = new Date();
    for (int i = 0; i < times; i++) {
     bis = new ByteArrayInputStream(b);
     ois = new HessianInput(bis);
     ois.readObject();
     ois.close();
    }
    end = new Date();
    dtime = end.getTime() - start.getTime();
    categories.append("<category name='Hessian' />");
    dataset1.append("<set value='" + stime + "'/>");
    dataset2.append("<set value='" + dtime + "'/>");
    dataset3.append("<set value='" + size + "'/>");
    System.out.println("Hessian - " + "时间：" + stime + "|" + dtime
      + " 长度：" + size);
   } catch (Exception e) {
    System.out.println("Hessian 不支持该类型");
   }
   //Hessian2
   try {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    Hessian2StreamingOutput oos = new Hessian2StreamingOutput(bos);
    oos.writeObject(data);
    oos.close();
    byte[] b = bos.toByteArray();
    size = b.length;
    ByteArrayInputStream bis = new ByteArrayInputStream(b);
    Hessian2StreamingInput ois = new Hessian2StreamingInput(bis);
    ois.readObject();
    ois.close();
    start = new Date();
    for (int i = 0; i < times; i++) {
     bos = new ByteArrayOutputStream();
     oos = new Hessian2StreamingOutput(bos);
     oos.writeObject(data);
     oos.close();
    }
    end = new Date();
    stime = end.getTime() - start.getTime();
    start = new Date();
    for (int i = 0; i < times; i++) {
     bis = new ByteArrayInputStream(b);
     ois = new Hessian2StreamingInput(bis);
     ois.readObject();
     ois.close();
    }
    end = new Date();
    dtime = end.getTime() - start.getTime();
    categories.append("<category name='Hessian2' />");
    dataset1.append("<set value='" + stime + "'/>");
    dataset2.append("<set value='" + dtime + "'/>");
    dataset3.append("<set value='" + size + "'/>");
    System.out.println("Hessian2 - " + "时间：" + stime + "|" + dtime
      + " 长度：" + size);
   } catch (Exception e) {
    System.out.println("Hessian2 不支持该类型");
   }
   //Burlap
   try {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    BurlapOutput oos = new BurlapOutput(bos);
    oos.writeObject(data);
    oos.close();
    byte[] b = bos.toByteArray();
    size = b.length;
    ByteArrayInputStream bis = new ByteArrayInputStream(b);
    BurlapInput ois = new BurlapInput(bis);
    ois.readObject();
    ois.close();
    start = new Date();
    for (int i = 0; i < times; i++) {
     bos = new ByteArrayOutputStream();
     oos = new BurlapOutput(bos);
     oos.writeObject(data);
     oos.close();
    }
    end = new Date();
    stime = end.getTime() - start.getTime();
    start = new Date();
    for (int i = 0; i < times; i++) {
     bis = new ByteArrayInputStream(b);
     ois = new BurlapInput(bis);
     ois.readObject();
     ois.close();
    }
    end = new Date();
    dtime = end.getTime() - start.getTime();
    categories.append("<category name='Burlap' />");
    dataset1.append("<set value='" + stime + "'/>");
    dataset2.append("<set value='" + dtime + "'/>");
    dataset3.append("<set value='" + size + "'/>");
    System.out.println("Burlap - " + "时间：" + stime + "|" + dtime
      + " 长度：" + size);
   } catch (Exception e) {
    System.out.println("Burlap 不支持该类型");
   }
   //AMF3
   try {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    AMF3Serializer oos = new AMF3Serializer(bos);
    oos.writeObject(data);
    oos.close();
    byte[] b = bos.toByteArray();
    size = b.length;
    ByteArrayInputStream bis = new ByteArrayInputStream(b);
    AMF3Deserializer ois = new AMF3Deserializer(bis);
    ois.readObject();
    ois.close();
    start = new Date();
    for (int i = 0; i < times; i++) {
     bos = new ByteArrayOutputStream();
     oos = new AMF3Serializer(bos);
     oos.writeObject(data);
     oos.close();
    }
    end = new Date();
    stime = end.getTime() - start.getTime();
    start = new Date();
    for (int i = 0; i < times; i++) {
     bis = new ByteArrayInputStream(b);
     ois = new AMF3Deserializer(bis);
     ois.readObject();
     ois.close();
    }
    end = new Date();
    dtime = end.getTime() - start.getTime();
    categories.append("<category name='AMF3' />");
    dataset1.append("<set value='" + stime + "'/>");
    dataset2.append("<set value='" + dtime + "'/>");
    dataset3.append("<set value='" + size + "'/>");
    System.out.println("AMF3 - " + "时间：" + stime + "|" + dtime + " 长度："
      + size);
   } catch (Exception e) {
    System.out.println("AMF3 不支持该类型");
   }
   //json-lib
   try {
    JSON json = JSONSerializer.toJSON(data);
    size = json.toString().getBytes("UTF-8").length;
    JSONSerializer.toJava(json);
    start = new Date();
    for (int i = 0; i < times; i++) {
     JSONSerializer.toJSON(data);
    }
    end = new Date();
    stime = end.getTime() - start.getTime();
    start = new Date();
    for (int i = 0; i < times; i++) {
     JSONSerializer.toJava(json);
    }
    end = new Date();
    dtime = end.getTime() - start.getTime();
    categories.append("<category name='json-lib' />");
    dataset1.append("<set value='" + stime + "'/>");
    dataset2.append("<set value='" + dtime + "'/>");
    dataset3.append("<set value='" + size + "'/>");
    System.out.println("json-lib - " + "时间：" + stime + "|" + dtime
      + " 长度：" + size);
   } catch (Exception e) {
    System.out.println("json-lib 不支持该类型");
   }
   //jsonplugin
   try {
    String json = JSONUtil.serialize(data);
    size = json.getBytes("UTF-8").length;
    JSONUtil.deserialize(json);
    start = new Date();
    for (int i = 0; i < times; i++) {
     JSONUtil.serialize(data);
    }
    end = new Date();
    stime = end.getTime() - start.getTime();
    start = new Date();
    for (int i = 0; i < times; i++) {
     JSONUtil.deserialize(json);
    }
    end = new Date();
    dtime = end.getTime() - start.getTime();
    categories.append("<category name='jsonplugin' />");
    dataset1.append("<set value='" + stime + "'/>");
    dataset2.append("<set value='" + dtime + "'/>");
    dataset3.append("<set value='" + size + "'/>");
    System.out.println("jsonplugin - " + "时间：" + stime + "|" + dtime
      + " 长度：" + size);
   } catch (Exception e) {
    System.out.println("jsonplugin 不支持该类型");
   }
   categories.append("</categories>");
   dataset1.append("</dataset>");
   dataset2.append("</dataset>");
   dataset3.append("</dataset>");
   sb.append(categories);
   sb.append(dataset1);
   sb.append(dataset2);
   sb.append(dataset3);
   sb.append("</graph>");
   OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream(
     filename), "GBK");
   ow.write(sb.toString());
   ow.close();
   System.out
     .println("--------------------------------------------");
}
@SuppressWarnings("unchecked")
public static void main(String[] args) throws IllegalAccessException,
    IllegalArgumentException, InvocationTargetException, IOException,
    ClassNotFoundException {
   //整数
   runTest(20000, 12, "对整数12", "1.xml");
   //布尔值
   runTest(20000, true, "对布尔值true", "2.xml");
   //null
   runTest(20000, null, "对 null", "3.xml");
   //浮点数
   runTest(20000, 1.2, "对浮点数1.2", "4.xml");
   //LONG型
   runTest(20000, 1234567890987654321L, "对UInt64型1234567890987654321",
     "5.xml");
   //无穷大
   runTest(20000, Double.POSITIVE_INFINITY, "对无穷大", "6.xml");
   //字符串
   String s = "PHPRPC - perfect high performance remote procedure call";
   runTest(20000, s, "对字符串“" + s + "”", "7.xml");
   //10000个字节数组
   byte[] ba = new byte[10000];
   for (int i = 0; i < 10000; i++) {
    ba[i] = (byte) (i % 255);
   }
   runTest(2000, ba, "对10000个元素的字节数组", "8.xml");
   //100个相同字符串的字符串数组
   String[] sa = new String[100];
   for (int i = 0; i < 100; i++) {
    sa[i] = s;
   }
   runTest(2000, sa, "对100个相同元素的字符串数组", "9.xml");
   //100个不同字符串的字符串数组
   sa = new String[100];
   for (int i = 0; i < 100; i++) {
    sa[i] = s + i;
   }
   runTest(2000, sa, "对100个不同元素的字符串数组", "10.xml");
   //HASHTABLE的字符串
   HashMap h = new HashMap();
   for (int i = 0; i < 100; i++) {
    h.put(s + i, s + (i + 100));
   }
   runTest(2000, h, "对索引不同内容不同具有100个字符串元素和字符串索引的 Hashtable", "11.xml");
   //自定义POJO
   User tc = new User();
   tc.setId(1);
   tc.setUserName("Ma Bingyao");
   tc.setPassword("PHPRPC");
   tc.setAge(28);
   runTest(200000, tc, "对自定义类型对象", "12.xml");
}
}
