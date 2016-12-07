package test;

import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import xulieh.PHPSerializer;

/*
array (size=1)
0 => 
  array (size=6)
    'ticket_type' => string '标准票' (length=9)
    'pay_info' => 
      array (size=2)
        'handlfee' => string '5' (length=1)
        'cash' => string '58.8' (length=4)
    'seat_id' => string '1011' (length=4)
    'handlfee' => string '5' (length=1)
    'lock_flag' => string '995841415155' (length=12)
    'is_discount' => string '0' (length=1)
    */

public class xulie {

	/**
	 * @param args
	 * @throws IllegalAccessException 
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws IllegalAccessException, UnsupportedEncodingException {

/*		String str = "a:2:{i:0;a:2:{s:7:\"orderid\";s:2:\"77\";s:5:\"level\";i:1;}i:1;"
                + "a:2:{s:7:\"orderid\";s:2:\"78\";s:5:\"level\";i:1;}}";
    PHPSerialize p = new PHPSerialize();
    PHPValue c = p.unserialize(str);
    System.out.println(c.toHashMap().toString());
    
    
    List list = (List)PHPSerializer.unserialize("a:3:{i:0;i:1241;i:1;i:4;i:2;i:16;}".getBytes());
		 for(Object o : list){
		System.out.println(o.toString());
		 }
 
    *
    */
		 HashMap map  = new HashMap();
		 HashMap map2  = new HashMap();
		 HashMap map3  = new HashMap();
		 map3.put("handlfee", "5");
		 map3.put("cash", "58.8");
		 
		 map2.put("ticket_type", "标准票");
		 map2.put("pay_info", map3);
		 map2.put("seat_id", "1011");
		 map2.put("handlfee", "5");
		 map2.put("lock_flag", "995841415155");
		 map2.put("is_discount", "0");
		 
		 
		 map.put("0", map2);
//		 String a = map.toString();
//		 String str = (String)PHPSerializer.serialize(a.getBytes());
	//	String str = PHPSerializer.cast(n, destClass);
		
		
	/*	String str1 = "a:1:{i:0;a:6:{s:11:\"ticket_type\";s:9:\"标准票\";s:8:\"pay_info\";a:2:{s:8:\"handlfee\";s:1:\"5\";s:4:\"cash\";s:4:\"58.8\";}s:7:\"seat_id\";s:4:\"1011\";s:8:\"handlfee\";s:1:\"5\";s:9:\"lock_flag\";s:12:\"995841415155\";s:11:\"is_discount\";s:1:\"0\";}}";
		List list = (List)PHPSerializer.unserialize("a:3:{i:0;i:1241;i:1;i:4;i:2;i:16;}".getBytes());
		//List list2 = (List) PHPSerializer.unserialize(str1.getBytes());
		for(Object o : list){
		System.out.println(o.toString());
		 }*/
		
		
		
		System.out.println("=========");
		//ArrayList list1 = new ArrayList();
		//list1.add(map);
		byte[] by  = PHPSerializer.serialize(map);
		String s = new String(by,"UTF-8");
		System.err.println(s);
		 
		 
		 
		
	}

}
