package com.tinet.ctilink.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeUtil {
	public static void writeObjectList(String file, Object object){	
		try{ 
			FileOutputStream fs = new FileOutputStream(file); 
			ObjectOutputStream os = new ObjectOutputStream(fs); 
			os.writeObject(object);
			os.close(); 
		}catch(Exception ex){ 
			ex.printStackTrace(); 
		} 
	}
	public static Object readObjectList(String file){
		try{ 
			Object res = null;
			File serFile=new File(file);
			if(serFile.exists()){
				FileInputStream fs = new FileInputStream(file);
				ObjectInputStream os = new ObjectInputStream(fs);
				res = os.readObject();
				os.close();
			}
			return res;
		}catch(Exception ex){ 
			ex.printStackTrace(); 
		}
		return null;
	}
}
