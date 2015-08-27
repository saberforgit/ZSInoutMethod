package com.example.definekeyboard;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.uzmap.pkg.uzcore.UZResourcesIDFinder;
import com.uzmap.pkg.uzcore.uzmodule.UZModuleContext;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 
 * @author tr
 * @time 2014-3-6
 * @description 自定义对话框工具类，用于控制键盘
 */
public class DefineKeyboardUtil {
	
	private Context mContext;
	private UZModuleContext moduleContext;
	private Keyboard keyboard_all; //全键盘对象
	private Keyboard keyboard_number; //数字键盘对象
	private Keyboard keyboard_sym; //符号键盘对象
	private StockKeyboardView mKeyboardView; //呈现虚拟键盘的视图。它处理渲染键和检测按键和触摸动作
	private String keyValues ="";
	public boolean isNum = false;// 是否数据键盘
	public boolean isSym = false;// 是否符号键盘
	public boolean isUpper = false;// 是否大写
	public View view;
	private String TAG = "Comming...";
	public DefineKeyboardUtil(Context mContext,StockKeyboardView mKeyboardView,View view,UZModuleContext moduleContext){
		this.mContext = mContext;
		this.mKeyboardView = mKeyboardView;
		int qwerty = UZResourcesIDFinder.getResXmlID("qwerty");
		keyboard_all = new Keyboard(mContext, qwerty);
		int interpunction = UZResourcesIDFinder.getResXmlID("interpunction");
		keyboard_number = new Keyboard(mContext, interpunction);
		int symbolsmore = UZResourcesIDFinder.getResXmlID("symbolsmore");
		keyboard_sym = new Keyboard(mContext, symbolsmore);
		this.view = view;
		this.moduleContext = moduleContext;
		this.mKeyboardView.setKeyboard(keyboard_all);
		this.mKeyboardView.setEnabled(true);
		this.mKeyboardView.setPreviewEnabled(true);
		this.mKeyboardView.setOnKeyboardActionListener(listener);
		/**启用或禁用键的反馈弹出*/
	//	this.mKeyboardView.setPreviewEnabled(false);
		
	}
	
	private OnKeyboardActionListener listener = new OnKeyboardActionListener() {
		
		@Override
		public void swipeUp() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void swipeRight() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void swipeLeft() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void swipeDown() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onText(CharSequence text) {
			// TODO Auto-generated method stub
			
		}
		
		//按键释放时触发方法
		@Override
		public void onRelease(int primaryCode) {
			// TODO Auto-generated method stub
			
		}

		//按键点击时触发方法
		@Override
		public void onPress(int primaryCode) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void onKey(int primaryCode, int[] keyCodes) {
			// TODO Auto-generated method stub
//			Editable editable = mEditText.getText();
//			int start = mEditText.getSelectionStart();
		//	System.out.println(editable.toString());
			switch(primaryCode){ //按键codes编码
			case Keyboard.KEYCODE_DONE://完成
				hideKeyboard();
				break;
			case Keyboard.KEYCODE_ALT://更多符号
				getMoreSymbols();
				break;
			case Keyboard.KEYCODE_CANCEL://取消
				hideKeyboard();
				break;
			case Keyboard.KEYCODE_DELETE://删除
//				if(editable != null && editable.length() > 0){
//					if(start > 0){
//						editable.delete(start-1, start);//开始，结束位置
//					}
//				}
				delWords(moduleContext);
				break;
			case Keyboard.KEYCODE_SHIFT: //大小写切换
				changeKey();
				mKeyboardView.setKeyboard(keyboard_all);
				break;
			case Keyboard.KEYCODE_MODE_CHANGE:	//切换到字符界面			
				changeKeyTONum();
		
			//	hideKeyboard();
				break;
			default:
		//		editable.insert(start, Character.toString((char) primaryCode));
				//System.out.println(editable.toString());
				addWords(moduleContext, primaryCode);
				break;
			}
		}

		
	};
	/**
	 * 切换符号键盘
	 */
	public void getMoreSymbols() {
		// TODO Auto-generated method stub
		if(isSym){ //当前普通数字键盘切换到更多
			isSym = false;
			mKeyboardView.setKeyboard(keyboard_number);
		}else{//当前为全字母键盘，切换为数字键盘
			isSym = true;
		//	randomNumKey();
			mKeyboardView.setKeyboard(keyboard_sym);
		}
	}
	/**
	 * 清空输入框所有内容
	 */
	public void clearEditTextContent(){

//		if(mEditText != null){
//			Editable editable = mEditText.getText();
//			int start = mEditText.getSelectionStart();
//			if(start > 0){
//				editable.clear();
//			}
//		}
		
	}
	
	
	/**
	 * 显示键盘
	 */
	public void showKeyboard(){
	//	clearEditTextContent();
	//	randomNumKey();
		mKeyboardView.setKeyboard(keyboard_all);
		int visibility = mKeyboardView.getVisibility();
		if(visibility == View.GONE || visibility == View.INVISIBLE){
			mKeyboardView.setVisibility(View.VISIBLE);
			Toast.makeText(mContext, "safdsfdsfsadf", 0).show();
		}
	}
	
	/**
	 * 隐藏键盘
	 */
	public void hideKeyboard(){
//		String str = mEditText.getText().toString();
		keyValues = null;
		int visibility = mKeyboardView.getVisibility();
		if(visibility == View.VISIBLE ){
			view.setVisibility(View.INVISIBLE);		
		}
	}
	
	/**
	 * 判断当前键盘是否可见
	 * @return true为键盘可见，false为键盘不可见
	 */ 
	public boolean isShowKeyboard(){
		int visibility = mKeyboardView.getVisibility();
		if(visibility == View.VISIBLE ){
			return true;
		}
		return false;
	}
	
	/**
	 * 切换键盘大小写字母
	 * 按照ascii码表可知，小写字母 = 大写字母+32;
	 */
	private void changeKey(){
		List<Key> keyList = keyboard_all.getKeys();
		if(isUpper){//如果为真表示当前为大写，需切换为小写
			isUpper = false;
			for (Key key : keyList) {
				if(key.label != null && isWord(key.label.toString())){
					key.label = key.label.toString().toLowerCase();
					key.codes[0] = key.codes[0]+32;
				}
			}
		}else{//如果为假表示当前为小写，需切换为大写
			isUpper = true;
			for (Key key : keyList) {
				if(key.label != null && isWord(key.label.toString())){
					key.label = key.label.toString().toUpperCase();
					key.codes[0] = key.codes[0]-32;
				}
			}
		}
	}
	
	/**
	 * 随机数字键盘,随机键盘LABEL中不能存在图片，否则在随机换位过程中会报错
	 */
	private void randomNumKey(){
		List<Key> keyList = keyboard_number.getKeys();
		int size = keyList.size();
		for (int i = 0; i < size; i++) {
			int random_a = (int)(Math.random()*(size));
			int random_b = (int)(Math.random()*(size));
			int code = keyList.get(random_a).codes[0];
			int codes = keyList.get(random_b).codes[0];
			System.out.println(code+"   "+codes);
			if(code==-5||code==-2||codes ==-5||codes==-2){
				continue;
			}else{
			CharSequence label = keyList.get(random_a).label;
			
			keyList.get(random_a).codes[0] = keyList.get(random_b).codes[0];
			keyList.get(random_a).label = keyList.get(random_b).label;
			
			keyList.get(random_b).codes[0] = code;
			keyList.get(random_b).label = label;
			}
			
		}
	}
	
	/**
	 * 数字键盘切换
	 */
	private void changeKeyTONum(){
		if(isNum){ //当前为数字键盘,切换为全字母键盘
			isNum = false;
			mKeyboardView.setKeyboard(keyboard_all);
		}else{//当前为全字母键盘，切换为数字键盘
			isNum = true;
		//	randomNumKey();
			mKeyboardView.setKeyboard(keyboard_number);
		}
	}
	
	/**判断是否为字母
	 * 
	 * @param str 需判断的字符串
	 */ 
	private boolean isWord(String str){
		String wordStr = "abcdefghijklmnopqrstuvwxyz";
		if(wordStr.indexOf(str.toLowerCase()) > -1){
			return true;
		}
		return false;
	}
	/**
	 * 删除字符
	 * @param moduleContext
	 * @param chars
	 */
	public void delWords(UZModuleContext moduleContext){
		Log.e(TAG,keyValues.length()+"");
		if(keyValues.length()>1){			
			keyValues = keyValues.substring(0,keyValues.length()-1);
		Log.e(TAG,keyValues);
		}else if(keyValues.length()==1){
			keyValues="";
		}
		getKeyValue(moduleContext, keyValues);
	}
	/**
	 * 添加字符
	 * @param moduleContext
	 * @param chars
	 * @param primaryCode
	 */
	public void addWords(UZModuleContext moduleContext,int primaryCode){
		char keyVal= (char) primaryCode;
		Log.e(TAG,keyVal+"");
		keyValues = keyValues+keyVal+"";
		Log.e(TAG,keyValues);
		getKeyValue(moduleContext, keyValues);
	}
	//获取点击的字符
	public void getKeyValue(UZModuleContext moduleContext,String chars)
	  {
	      JSONObject ret = new JSONObject();
	      try {
	        ret.put("keyboard", chars);
	        moduleContext.success(ret, false);
	      } catch (JSONException e) {
	        e.printStackTrace();
	        try {
	          ret.put("error", e.getMessage());
	        }
	        catch (JSONException e1) {
	          e1.printStackTrace();
	        }
	      }
	    }

}
