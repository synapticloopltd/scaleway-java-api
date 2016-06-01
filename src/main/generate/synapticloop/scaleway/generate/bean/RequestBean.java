package synapticloop.scaleway.generate.bean;

import java.util.ArrayList;
import java.util.List;

/*
 * Copyright (c) 2016 Synapticloop.
 * 
 * All rights reserved.
 * 
 * This code may contain contributions from other parties which, where 
 * applicable, will be listed in the default build file for the project 
 * ~and/or~ in a file named CONTRIBUTORS.txt in the root of the project.
 * 
 * This source code and any derived binaries are covered by the terms and 
 * conditions of the Licence agreement ("the Licence").  You may not use this 
 * source code or any derived binaries except in compliance with the Licence.  
 * A copy of the Licence is available in the file named LICENSE.txt shipped with 
 * this source code or binaries.
 */


import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RequestBean {
	private String title;
	private String comment;

	private List<MethodBean> methodBeans = new ArrayList<MethodBean>();

	public RequestBean(Element element) {
		// get the title
		this.title = element.select(".panel-heading h3").first().text();
		this.comment = element.select(".panel-body p").first().text();
		
		Elements methodElements = element.select(".panel-body section");
		for (Element methodElement : methodElements) {
			methodBeans.add(new MethodBean(methodElement));
		}
	}

	@Override
	public String toString() {
		for (MethodBean methodBean : methodBeans) {
			System.out.println(methodBean.toString());
		}
		return "RequestBean [title=" + this.title + ", comment=" + this.comment + "]";
		
	}

}
