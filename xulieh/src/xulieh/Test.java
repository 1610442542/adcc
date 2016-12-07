
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
   //����
   StringBuilder sb = new StringBuilder();
   StringBuilder categories = new StringBuilder();
   //���л�ʱ��
   StringBuilder dataset1 = new StringBuilder();
   //�����л�ʱ��
   StringBuilder dataset2 = new StringBuilder();
   //�ռ�
   StringBuilder dataset3 = new StringBuilder();
   sb
     .append("<graph xaxisname='Continent' yaxisname='Export' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='"
       + times
       + "��"
       + info
       + "���л��뷴���л��ıȽ�' shownames='1' showvalues='1' decimals='0' formatNumberScale='0' baseFont='Tahama' baseFontSize='12'>");
   categories.append("<categories>");
   dataset1.append("<dataset seriesname='���л�ʱ��' color='FDC12E'>");
   dataset2.append("<dataset seriesname='�����л�ʱ��' color='56B9F9'>");
   dataset3.append("<dataset seriesname='�ռ�' color='C9198D'>");
   System.out
     .println("--------------------------------------------");
   System.out.println(times + "��" + info);
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
    System.out.println("Java - " + "ʱ�䣺" + stime + "|" + dtime + " ���ȣ�"
      + size);
   } catch (Exception e) {
    System.out.println("Java ��֧�ָ�����");
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
    System.out.println("PHPRPC - " + "ʱ�䣺" + stime + "|" + dtime
      + " ���ȣ�" + size);
   } catch (Exception e) {
    System.out.println("PHPRPC ��֧�ָ�����");
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
    System.out.println("Hessian - " + "ʱ�䣺" + stime + "|" + dtime
      + " ���ȣ�" + size);
   } catch (Exception e) {
    System.out.println("Hessian ��֧�ָ�����");
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
    System.out.println("Hessian2 - " + "ʱ�䣺" + stime + "|" + dtime
      + " ���ȣ�" + size);
   } catch (Exception e) {
    System.out.println("Hessian2 ��֧�ָ�����");
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
    System.out.println("Burlap - " + "ʱ�䣺" + stime + "|" + dtime
      + " ���ȣ�" + size);
   } catch (Exception e) {
    System.out.println("Burlap ��֧�ָ�����");
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
    System.out.println("AMF3 - " + "ʱ�䣺" + stime + "|" + dtime + " ���ȣ�"
      + size);
   } catch (Exception e) {
    System.out.println("AMF3 ��֧�ָ�����");
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
    System.out.println("json-lib - " + "ʱ�䣺" + stime + "|" + dtime
      + " ���ȣ�" + size);
   } catch (Exception e) {
    System.out.println("json-lib ��֧�ָ�����");
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
    System.out.println("jsonplugin - " + "ʱ�䣺" + stime + "|" + dtime
      + " ���ȣ�" + size);
   } catch (Exception e) {
    System.out.println("jsonplugin ��֧�ָ�����");
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
   //����
   runTest(20000, 12, "������12", "1.xml");
   //����ֵ
   runTest(20000, true, "�Բ���ֵtrue", "2.xml");
   //null
   runTest(20000, null, "�� null", "3.xml");
   //������
   runTest(20000, 1.2, "�Ը�����1.2", "4.xml");
   //LONG��
   runTest(20000, 1234567890987654321L, "��UInt64��1234567890987654321",
     "5.xml");
   //�����
   runTest(20000, Double.POSITIVE_INFINITY, "�������", "6.xml");
   //�ַ���
   String s = "PHPRPC - perfect high performance remote procedure call";
   runTest(20000, s, "���ַ�����" + s + "��", "7.xml");
   //10000���ֽ�����
   byte[] ba = new byte[10000];
   for (int i = 0; i < 10000; i++) {
    ba[i] = (byte) (i % 255);
   }
   runTest(2000, ba, "��10000��Ԫ�ص��ֽ�����", "8.xml");
   //100����ͬ�ַ������ַ�������
   String[] sa = new String[100];
   for (int i = 0; i < 100; i++) {
    sa[i] = s;
   }
   runTest(2000, sa, "��100����ͬԪ�ص��ַ�������", "9.xml");
   //100����ͬ�ַ������ַ�������
   sa = new String[100];
   for (int i = 0; i < 100; i++) {
    sa[i] = s + i;
   }
   runTest(2000, sa, "��100����ͬԪ�ص��ַ�������", "10.xml");
   //HASHTABLE���ַ���
   HashMap h = new HashMap();
   for (int i = 0; i < 100; i++) {
    h.put(s + i, s + (i + 100));
   }
   runTest(2000, h, "��������ͬ���ݲ�ͬ����100���ַ���Ԫ�غ��ַ��������� Hashtable", "11.xml");
   //�Զ���POJO
   User tc = new User();
   tc.setId(1);
   tc.setUserName("Ma Bingyao");
   tc.setPassword("PHPRPC");
   tc.setAge(28);
   runTest(200000, tc, "���Զ������Ͷ���", "12.xml");
}
}
