package org.springframework.samples.mvc.views;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Controller
@RequestMapping("/views/*")
public class ViewsController {
	private static Pattern pattern =  Pattern.compile("(.*products/)(.*)");
	@GetMapping("html")
	public @ResponseBody List<TotalObject> prepare(@RequestParam String link, Model model) {
		String html = sendGet(link, null);
		System.out.println("in:::" + System.currentTimeMillis());
		String product_id = "";
		Pattern pattern =  Pattern.compile("(\"originalProductNo\" : \"\\d*\",)");
		Matcher matcher = pattern.matcher(html);
		if(matcher.find()) {
			String prefix = matcher.group(1);
			product_id =prefix.replaceAll("\\D", "");
		}
		
		Matcher matcher1 = this.pattern.matcher(link);
		String url ="";
		if(matcher1.find()) {
			url = matcher1.group(1);
		}else {
			return null;
		}
		url += product_id;
		
		Map<String, TotalObject> total = new TreeMap<String, TotalObject>();
		sendGetRequest(url, 1, total);
		
		
		List totalList = new ArrayList(total.entrySet());
		Collections.sort(totalList, new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				Map.Entry<String, TotalObject> o11 = (Map.Entry<String, TotalObject>)o1;
				Map.Entry<String, TotalObject> o12 = (Map.Entry<String, TotalObject>)o2;
				return  o12.getValue().totalCount.intValue() - o11.getValue().totalCount.intValue();
			}
		});
 		for(Object oo:totalList) {
			 Map.Entry<String, TotalObject> ooo = (Map.Entry<String, TotalObject>)oo;
			 ooo.getValue().getAvgScore();
		}
		 
		return totalList;
	}
	
	@GetMapping("home")
	public String home(Model model) {
		
		
		
		return "views/home";
	}
	
	 public void sendGetRequest(String productUrl, int page, Map<String, TotalObject> total) {
		 	String param = "size=20&sortType=REVIEW_RANKING&contentType=ALL&topicCode&page=" + page;
		 	String data = sendGet(productUrl+"/reviews/page.json",param);
		 	JSONObject jObj = JSONObject.fromObject(data);
		 	JSONArray jArray =  jObj.getJSONObject("htReturnValue").getJSONObject("pagedResult").getJSONArray("content");
		 	int totalPage = jObj.getJSONObject("htReturnValue").getJSONObject("pagedResult").getInt("totalPages");
		 	
		 	
		 	for(int i=0; i< jArray.size(); i++) {
		 		JSONObject jo = jArray.getJSONObject(i);
		 		
		 		String oName = jo.getString("purchasedOptionContents");
		 		String score = jo.getString("reviewScore");
		 		if(total.get(oName) == null) {
		 			TotalObject tobj = new TotalObject();
		 			tobj.totalCount = 1d;
		 			tobj.totalScore = Double.parseDouble(score);
		 			total.put(oName, tobj);
		 		}else {
		 			TotalObject tobj = total.get(oName);
		 			tobj.totalCount++;
		 			tobj.totalScore= tobj.totalScore+Double.parseDouble(score);
		 		}
		 	}
		 	if(page < totalPage) {
		 		sendGetRequest(productUrl, ++page, total);
		 	}
	 }
	
	
	 public static String sendGet(String url, String param) {
	        String result = "";
	        BufferedReader in = null;
	        try {
	            String urlNameString = url + "?" + param;
	            if(param == null || param.equals("")) {
	            	urlNameString = url;
	            }
	            
	            URL realUrl = new URL(urlNameString);
	            // 打开和URL之间的连接
	            URLConnection connection = realUrl.openConnection();
	            // 设置通用的请求属性
	            connection.setRequestProperty("accept", "*/*");
	            connection.setRequestProperty("connection", "Keep-Alive");
	            connection.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            // 建立实际的连接
	            connection.connect();
	            in = new BufferedReader(new InputStreamReader(
	                    connection.getInputStream()));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        // 使用finally块来关闭输入流
	        finally {
	            try {
	                if (in != null) {
	                    in.close();
	                }
	            } catch (Exception e2) {
	                e2.printStackTrace();
	            }
	        }
	        return result;
	    }
	

	@GetMapping("/viewName")
	public void usingRequestToViewNameTranslator(Model model) {
		model.addAttribute("foo", "bar");
		model.addAttribute("fruit", "apple");
	}

	@GetMapping("pathVariables/{foo}/{fruit}")
	public String pathVars(@PathVariable String foo, @PathVariable String fruit) {
		// No need to add @PathVariables "foo" and "fruit" to the model
		// They will be merged in the model before rendering
		return "views/html";
	}

	@GetMapping("dataBinding/{foo}/{fruit}")
	public String dataBinding(@Valid JavaBean javaBean, Model model) {
		// JavaBean "foo" and "fruit" properties populated from URI variables 
		return "views/dataBinding";
	}
	
	
	
	

}
