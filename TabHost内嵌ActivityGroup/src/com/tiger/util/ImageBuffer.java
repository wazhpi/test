package com.tiger.util;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;

/**
 * �DƬ�����
 * */
public class ImageBuffer {

	private Map<String, SoftReference<Bitmap>> buffer = new HashMap<String, SoftReference<Bitmap>>(); // �DƬ����

	/**
	 * ���DƬ���M������
	 * 
	 * @param key
	 *            �Iֵ
	 * @param image
	 *            Bitmapλ�D
	 * */
	public void put(String key, Bitmap image) {
		SoftReference<Bitmap> reference = new SoftReference<Bitmap>(image);
		synchronized (buffer) {
			buffer.put(key, reference);
		}
	}

	/**
	 * �ľ�����ȡ�ÈDƬ
	 * 
	 * @param key
	 *            �Iֵ
	 * @return Bitmapλ�D
	 * */
	public Bitmap get(String key) {
		Bitmap result = null;
		synchronized (buffer) {
			if (buffer.containsKey(key)) {
				result = buffer.get(key).get();
			}
		}
		return result;
	}

}
