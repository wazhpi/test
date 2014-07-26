package com.tiger.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

/**
 * �h�ˈDƬ�
 * */
public class LoadRemoteImage {

	private ExecutorService pool; // ���̳�

	private final int MESSAGE_OK = 1; // �h�ˈDƬ�@ȡ�ɹ���Ϣ
	private final int MESSAGE_ERROR = -1; // �h�ˈDƬ�@ȡ�e�`��Ϣ

	private ImageBuffer imageBuffer; // �DƬ����

	/**
	 * ���캯�� ���г�ʼ������
	 * */
	public LoadRemoteImage() {
		pool = Executors.newCachedThreadPool();
		imageBuffer = new ImageBuffer();
	}

	/**
	 * �O���h�ˈDƬ�¼��O ��
	 * 
	 * @param url
	 *            �D��URL��ַ
	 * @param listener
	 *            �h�ˈDƬ�O ��
	 * */
	public void setRemoteImageListener(final String url,
			final OnRemoteImageListener listener) {

		/*
		 * �h�ˈDƬ��Ϣ̎��Handler
		 */
		final Handler handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				int what = msg.what;
				switch (what) {
				case MESSAGE_OK: // �ɹ�
					listener.onRemoteImage((Bitmap) msg.obj); // �{��onRemoteImage���{����
					break;
				case MESSAGE_ERROR: // �e�`
					listener.onError((String) msg.obj); // //�{��onError���{����
					break;
				}
			}

		};

		/*
		 * �򾀳̳���������΄� ���d�o��URL��ַ�DƬ
		 */
		pool.execute(new Runnable() {

			@Override
			public void run() {
				try {
					Bitmap image = null;
					/*
					 * ����DƬ�����Л]��ԓ�DƬ���t���d���뾏����
					 */
					if ((image = imageBuffer.get(url)) == null) {
						byte[] resource = httpRequestByteArray(url); // HTTPՈ��DƬ�ֹ�����
						image = optimizeBitmap(resource, 100, 100); // �@�Ã����ĈD��
						imageBuffer.put(url, image);
					}
					handler.sendMessage(handler
							.obtainMessage(MESSAGE_OK, image)); // �h�ˈD�����d�ɹ�
				} catch (Exception e) {
					/*
					 * ����̎��
					 */
					handler.sendMessage(handler.obtainMessage(MESSAGE_ERROR,
							e.getMessage()));
					return;
				}
			}
		});
	}

	/**
	 * ʹ��HTTP GET��ʽՈ��
	 * 
	 * @param url
	 *            URL��ַ
	 * @return HttpEntiry����
	 * @throws Exception
	 * */
	private byte[] httpRequestByteArray(String url) throws Exception {
		byte[] result = null;
		HttpGet httpGet = new HttpGet(url);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse;
		httpResponse = httpClient.execute(httpGet);
		int httpStatusCode = httpResponse.getStatusLine().getStatusCode();
		/*
		 * �Д�HTTP��B�a�Ƿ��200
		 */
		if (httpStatusCode == HttpStatus.SC_OK) {
			result = EntityUtils.toByteArray(httpResponse.getEntity());
		} else {
			throw new Exception("HTTP: " + httpStatusCode);
		}
		return result;
	}

	private Bitmap optimizeBitmap(byte[] resource, int maxWidth, int maxHeight) {
		Bitmap result = null;
		int length = resource.length;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		result = BitmapFactory.decodeByteArray(resource, 0, length, options);
		int widthRatio = (int) Math.ceil(options.outWidth / maxWidth);
		int heightRatio = (int) Math.ceil(options.outHeight / maxHeight);
		if (widthRatio > 1 || heightRatio > 1) {
			if (widthRatio > heightRatio) {
				options.inSampleSize = widthRatio;
			} else {
				options.inSampleSize = heightRatio;
			}
		}
		options.inJustDecodeBounds = false;
		result = BitmapFactory.decodeByteArray(resource, 0, length, options);
		return result;
	}

	/**
	 * �h�ˈDƬ�O ��
	 * */
	public interface OnRemoteImageListener {

		/**
		 * �h�ˈDƬ̎��
		 * 
		 * @param image
		 *            λ�D�DƬ
		 * */
		void onRemoteImage(Bitmap image);

		/**
		 * �e�`̎��
		 * 
		 * @param error
		 *            �e�`��Ϣ
		 * */
		void onError(String error);

	}

}
