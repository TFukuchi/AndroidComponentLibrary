package jp.classmethod.android.componentlibrary.widget;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;


import android.widget.TimePicker;

/**
 * SDKVersion 10以下の場合のNumberPickerコントローラーの操作を行うクラス
 * @author komuro
 *
 */
public class GBNumberPickerController extends UITimePickerController {

	@Override
	public void overrideTimePicker(UITimePicker picker, Calendar calendar) {
		try {
			Field f = TimePicker.class.getDeclaredField("mMinutePicker");
			f.setAccessible(true);
			Object numberPicker = f.get(picker);
			Class<?>[] args = {
				int.class,
				int.class,
				String[].class
			};
			Class<?> clazz = Class.forName("android.widget.NumberPicker");
			Method m = clazz.getDeclaredMethod("setRange", args);
			
			String[] items = createMinItems(unit);
			maxIdx = items.length - 1;
			
			Object[] params = {
				0,
				maxIdx, 
				items
			};
			m.invoke(numberPicker, params);
			
			// TODO: Minutes変更時にHourが追随するようにNumberPicker.onValueChangedListenerの設定が必要
			
			picker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
			picker.setCurrentMinute(calendar.get(Calendar.MINUTE));
			
			// 編集不可
			picker.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}		
	}

	
}
