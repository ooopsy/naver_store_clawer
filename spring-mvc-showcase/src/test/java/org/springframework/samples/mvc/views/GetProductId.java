package org.springframework.samples.mvc.views;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class GetProductId {

	@Test
	public void test() {
		
		
		String link = "https://smartstore.naver.com/mysweety/products/555727008#revw";
		
		Pattern pattern =  Pattern.compile("(.*products/\\d*)(.*)");
		Matcher matcher = pattern.matcher(link);
		if(matcher.find()) {
			String prefix = matcher.group(1);
			
			String product_id = matcher.group(2);
			System.out.println(prefix);
			System.out.println(product_id);
		}
		
		
		
		
	}

}
