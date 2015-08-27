package com.example.definekeyboard;

import com.apicloud.moduleDemo.ZSInputMethod;

import android.content.Context;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;

/**
 * 
 * @author tr
 * @time 2014-3-7
 * @description �Զ���keyboardView�ؼ�����дonLongPress()ʵ�ֳ���ɾ����������
 */
public class StockKeyboardView extends KeyboardView{

	public StockKeyboardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public StockKeyboardView(Context context, AttributeSet attrs, int defStyle) {   
        super(context, attrs, defStyle);   
    }
	@Override
	protected boolean onLongPress(Key popupKey) {
		// TODO Auto-generated method stub
		
		if(popupKey.codes[0] == Keyboard.KEYCODE_DELETE){
			//��ʹ��OnKeyboardActionListener�еĸ��ַ���ʵ�ָù���
//			getOnKeyboardActionListener().onKey(Keyboard.KEYCODE_DELETE, null);  
			
		}
		
		return super.onLongPress(popupKey);
	}   
	

}
