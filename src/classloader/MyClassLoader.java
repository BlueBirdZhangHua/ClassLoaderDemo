/*************************************************************************************
 * Module Name:com.funshion.video.activity
 * File Name:MyClassLoader.java 
 * Description:TODO
 * Author: 张华
 * Copyright 2007-, Funshion Online Technologies Ltd.
 * All Rights Reserved
 * 版权 2007-，北京风行在线技术有限公司
 * 所有版权保护
 * This is UNPUBLISHED PROPRIETARY SOURCE CODE of Funshion Online Technologies Ltd.;
 * the contents of this file may not be disclosed to third parties, copied or
 * duplicated in any form, in whole or in part, without the prior written
 * permission of Funshion Online Technologies Ltd.
 * 这是北京风行在线技术有限公司未公开的私有源代码。本文件及相关内容未经风行在线技术有
 * 限公司事先书面同意，不允许向任何第三方透露，泄密部分或全部; 也不允许任何形式的私自备份。
 ***************************************************************************************/
package classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MyClassLoader extends ClassLoader {
	private String mDirPath;

	public MyClassLoader(String dirPath) {
		mDirPath = dirPath;
	}

	@Override
	protected Class<?> findClass(String arg0) throws ClassNotFoundException {
		byte[] classData = getClassData(arg0);  //根据类的二进制名称,获得该class文件的字节码数组  
        if (classData == null) {  
            throw new ClassNotFoundException();  
        }  
		System.out.println("Size"+classData.length);
        Class loadedClass = defineClass(arg0, classData, 0, classData.length);  //将class的字节码数组转换成Class类的实例  
		return loadedClass;
	}

	private byte[] getClassData(String name) {
		InputStream is = null;
		try {
			String path = createClassFilePath(name);
			URL url = new URL(path);
			byte[] buff = new byte[1024 * 4];
			int len = -1;
			is = url.openStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((len = is.read(buff)) != -1) {
				baos.write(buff, 0, len);
			}
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	private String createClassFilePath(String className) {
		return mDirPath + File.separator + className + ".class";
	}

}
