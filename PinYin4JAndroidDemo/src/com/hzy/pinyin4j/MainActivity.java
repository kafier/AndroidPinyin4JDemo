package com.hzy.pinyin4j;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import com.hzy.pinyin4j.R.id;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {
	EditText etIn, etOut;
	Button btConv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		etIn = (EditText) findViewById(id.et_in);
		etOut = (EditText) findViewById(id.et_out);
		btConv = (Button) findViewById(id.bt_conv);
		btConv.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		String src = etIn.getText().toString();
		String outStr = getPinyinString(src);
		etOut.setText(outStr);
	}

	private String getPinyinString(String input) {
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		format.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);
		format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);

		StringBuilder outStr = new StringBuilder();

		char[] chars = input.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			String[] outArr = null;
			try {
				if (chars[i] >= 0x4e00 && chars[i] <= 0x9fa5) {
					outArr = PinyinHelper.toHanyuPinyinStringArray(chars[i], format);
				}
			} catch (BadHanyuPinyinOutputFormatCombination e) {
				e.printStackTrace();
			}
			if (outArr != null && outArr.length > 0) {
				outStr.append(outArr[0]);
			}
		}
		return outStr.toString();
	}

}
