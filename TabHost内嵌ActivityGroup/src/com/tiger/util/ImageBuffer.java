package com.tiger.util;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;

/**
 * 圖片緩存類
 * */
public class ImageBuffer {

	private Map<String, SoftReference<Bitmap>> buffer = new HashMap<String, SoftReference<Bitmap>>(); // 圖片緩存

	/**
	 * 將圖片放進緩存中
	 * 
	 * @param key
	 *            鍵值
	 * @param image
	 *            Bitmap位圖
	 * */
	public void put(String key, Bitmap image) {
		SoftReference<Bitmap> reference = new SoftReference<Bitmap>(image);
		synchronized (buffer) {
			buffer.put(key, reference);
		}
	}

	/**
	 * 從緩存中取得圖片
	 * 
	 * @param key
	 *            鍵值
	 * @return Bitmap位圖
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
