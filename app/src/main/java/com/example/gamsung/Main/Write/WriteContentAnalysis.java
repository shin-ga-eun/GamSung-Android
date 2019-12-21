package com.example.gamsung.Main.Write;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//자바정규식표현을 통해 해시태그내용을 추출하는 클래스
public class WriteContentAnalysis {

	private String content;
	public Pattern	p = Pattern.compile("\\#([0-9a-zA-Z가-힣]*)");
	public Matcher m;
	
	public WriteContentAnalysis(String content){
		this.content = content;
		m = p.matcher(content);
		
	}


	public ArrayList<String> hashTags() {
		String str;
		ArrayList<String> result = new ArrayList<>();
		int i=0;
		
		while(m.find()) {
			
			str = m.group();
			result.add(str.substring(1, str.length()));
			
			i++;
		} 
		
		return result;
	}
	


}


