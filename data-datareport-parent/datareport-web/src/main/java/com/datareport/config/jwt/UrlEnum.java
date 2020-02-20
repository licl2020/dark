package com.datareport.config.jwt;

import java.util.ArrayList;
import java.util.List;

/**
 * jwt过滤的url
 * @author 16472
 *
 */
public enum UrlEnum {

	Login("Login"),
	cssandjs("cssandjs"),
	LoginVerify("LoginVerify"),
	kickout("kickout"),
	prompt("prompt");
		
		private String url;

		
		
		private UrlEnum(String url){
			this.url=url;
		}
		
		
		public static List<String> getUrlList() {
			List<String> list = new ArrayList<String>();
			for (UrlEnum e : UrlEnum.values()) {
				list.add(e.url);
			}
			return list;
		}


		public String getUrl() {
			return url;
		}


		public void setUrl(String url) {
			this.url = url;
		}
		
		

}
