package com.apicloud.moduleDemo;

import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Build;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.definekeyboard.DefineKeyboardUtil;
import com.example.definekeyboard.StockKeyboardView;
import com.uzmap.pkg.uzcore.UZResourcesIDFinder;
import com.uzmap.pkg.uzcore.UZWebView;
import com.uzmap.pkg.uzcore.annotation.UzJavascriptMethod;
import com.uzmap.pkg.uzcore.annotation.UzJavascriptProperty;
import com.uzmap.pkg.uzcore.uzmodule.UZModule;
import com.uzmap.pkg.uzcore.uzmodule.UZModuleContext;

/**
 * 该类映射至Javascript中moduleDemo对象<br><br>
 * <strong>Js Example:</strong><br>
 * var module = api.require('moduleDemo');<br>
 * module.xxx();
 * @author APICloud
 *
 */
public class ZSInputMethod extends UZModule {

	static final int ACTIVITY_REQUEST_CODE_A = 100;
	
	private AlertDialog.Builder mAlert;
	private Vibrator mVibrator;
	private UZModuleContext mJsCallback;
	private View view;
	private TextView textView;
	public DefineKeyboardUtil  mDefineKeyboardUtil;
	private UZWebView uzWebView;
	
	public ZSInputMethod(UZWebView webView) {
		super(webView);
		this.uzWebView = webView;
//		hideKeyBoard();
	}

	  @UzJavascriptMethod
	  public void jsmethod_addView(UZModuleContext moduleContext)
	  {
	    removeView();
	    Log.e("===", "sadfsd");
	   // getContext().getWindow().setSoftInputMode(3);
	  //  hideKeyBoard();
	 //   String title = moduleContext.optString("title");	
	    int layoutId = UZResourcesIDFinder.getResLayoutID("activity_define_keyboard");
	    this.view = View.inflate(this.mContext, layoutId, null);
	    int titleID = UZResourcesIDFinder.getResIdID("title");
	    this.textView = (TextView)this.view.findViewById(titleID);
	//    textView.setText(title);
	    Log.e("=====", "toCut");
	    int numKeyLayID = UZResourcesIDFinder.getResIdID("keyboard_view");
	    this.mDefineKeyboardUtil = new DefineKeyboardUtil(
	      this.mContext, (StockKeyboardView)this.view.findViewById(numKeyLayID),this.view,moduleContext);
	    this.mDefineKeyboardUtil.showKeyboard();
	    RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(-1, -2);
	    rlp.setMarginEnd(0);
	    addViewToCutWindow(this.view, rlp);
	  }

	  public void removeView() {
	    removeViewFromCurWindow(this.view);
	  }

	

	  public void addViewToCutWindow(View view, RelativeLayout.LayoutParams params)
	  {
		  Log.e("=====", "toCut");
	    this.uzWebView.addView(view, params);
	  }

	  public void jsmethod_removeView(UZModuleContext moduleContext)
	  {
	    removeViewFromCurWindow(this.view);
	  }

}
