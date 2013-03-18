package com.example.webutil;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;



public class ImageLoader {
	public static ImageLoader mOnlyInstance = new ImageLoader();
	private final Map<String, Drawable> drawableMap;

	private ImageLoader() {
		drawableMap = new HashMap<String, Drawable>();
	}
	public static ImageLoader getOnlyInstance(){
		return mOnlyInstance;
	}

	public Drawable fetchDrawable(String urlString) {
		if (drawableMap.containsKey(urlString)) {
			return drawableMap.get(urlString);
		}
		InputStream is = Webutil.retrieveStream(urlString);
		Drawable drawable = Drawable.createFromStream(is, "src");

		if (drawable != null) {
			drawableMap.put(urlString, drawable);	                
		} 
		return drawable;
	}

	public void fetchDrawableOnThread(final String urlString, final ImageView imageView) {
		if (drawableMap.containsKey(urlString)) {
			imageView.setImageDrawable(drawableMap.get(urlString));
		}

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message message) {
				imageView.setImageDrawable((Drawable) message.obj);
			}
		};

		Thread thread = new Thread() {
			@Override
			public void run() {
				//TODO : set imageView to a "pending" image
				Drawable drawable = fetchDrawable(urlString);
				Message message = handler.obtainMessage(1, drawable);
				handler.sendMessage(message);
			}
		};
		thread.start();
	}	    
}


