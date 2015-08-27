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
 * @description �Զ���Ի��򹤾��࣬���ڿ��Ƽ���
 */
public class DefineKeyboardUtil {
	
	private Context mContext;
	private UZModuleContext moduleContext;
	private Keyboard keyboard_all; //ȫ���̶���
	private Keyboard keyboard_number; //���ּ��̶���
	private Keyboard keyboard_sym; //���ż��̶���
	private StockKeyboardView mKeyboardView; //����������̵���ͼ����������Ⱦ���ͼ�ⰴ���ʹ�������
	private String keyValues ="";
	public boolean isNum = false;// �Ƿ����ݼ���
	public boolean isSym = false;// �Ƿ���ż���
	public boolean isUpper = false;// �Ƿ��д
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
		/**���û���ü��ķ�������*/
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
		
		//�����ͷ�ʱ��������
		@Override
		public void onRelease(int primaryCode) {
			// TODO Auto-generated method stub
			
		}

		//�������ʱ��������
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
			switch(primaryCode){ //����codes����
			case Keyboard.KEYCODE_DONE://���
				hideKeyboard();
				break;
			case Keyboard.KEYCODE_ALT://�������
				getMoreSymbols();
				break;
			case Keyboard.KEYCODE_CANCEL://ȡ��
				hideKeyboard();
				break;
			case Keyboard.KEYCODE_DELETE://ɾ��
//				if(editable != null && editable.length() > 0){
//					if(start > 0){
//						editable.delete(start-1, start);//��ʼ������λ��
//					}
//				}
				delWords(moduleContext);
				break;
			case Keyboard.KEYCODE_SHIFT: //��Сд�л�
				changeKey();
				mKeyboardView.setKeyboard(keyboard_all);
				break;
			case Keyboard.KEYCODE_MODE_CHANGE:	//�л����ַ�����			
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
	 * �л����ż���
	 */
	public void getMoreSymbols() {
		// TODO Auto-generated method stub
		if(isSym){ //��ǰ��ͨ���ּ����л�������
			isSym = false;
			mKeyboardView.setKeyboard(keyboard_number);
		}else{//��ǰΪȫ��ĸ���̣��л�Ϊ���ּ���
			isSym = true;
		//	randomNumKey();
			mKeyboardView.setKeyboard(keyboard_sym);
		}
	}
	/**
	 * ����������������
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
	 * ��ʾ����
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
	 * ���ؼ���
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
	 * �жϵ�ǰ�����Ƿ�ɼ�
	 * @return trueΪ���̿ɼ���falseΪ���̲��ɼ�
	 */ 
	public boolean isShowKeyboard(){
		int visibility = mKeyboardView.getVisibility();
		if(visibility == View.VISIBLE ){
			return true;
		}
		return false;
	}
	
	/**
	 * �л����̴�Сд��ĸ
	 * ����ascii����֪��Сд��ĸ = ��д��ĸ+32;
	 */
	private void changeKey(){
		List<Key> keyList = keyboard_all.getKeys();
		if(isUpper){//���Ϊ���ʾ��ǰΪ��д�����л�ΪСд
			isUpper = false;
			for (Key key : keyList) {
				if(key.label != null && isWord(key.label.toString())){
					key.label = key.label.toString().toLowerCase();
					key.codes[0] = key.codes[0]+32;
				}
			}
		}else{//���Ϊ�ٱ�ʾ��ǰΪСд�����л�Ϊ��д
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
	 * ������ּ���,�������LABEL�в��ܴ���ͼƬ�������������λ�����лᱨ��
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
	 * ���ּ����л�
	 */
	private void changeKeyTONum(){
		if(isNum){ //��ǰΪ���ּ���,�л�Ϊȫ��ĸ����
			isNum = false;
			mKeyboardView.setKeyboard(keyboard_all);
		}else{//��ǰΪȫ��ĸ���̣��л�Ϊ���ּ���
			isNum = true;
		//	randomNumKey();
			mKeyboardView.setKeyboard(keyboard_number);
		}
	}
	
	/**�ж��Ƿ�Ϊ��ĸ
	 * 
	 * @param str ���жϵ��ַ���
	 */ 
	private boolean isWord(String str){
		String wordStr = "abcdefghijklmnopqrstuvwxyz";
		if(wordStr.indexOf(str.toLowerCase()) > -1){
			return true;
		}
		return false;
	}
	/**
	 * ɾ���ַ�
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
	 * ����ַ�
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
	//��ȡ������ַ�
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
