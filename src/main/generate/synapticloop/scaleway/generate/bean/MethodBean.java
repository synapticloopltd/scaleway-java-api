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

public class MethodBean {

	private String shortComment;
	private String methodType;
	private String exampleUrl;
	private List<String> comments = new ArrayList<String>();

	public MethodBean(Element methodElement) {
		Elements panelHeadingElements = methodElement.select(".panel-heading div");
		this.shortComment = panelHeadingElements.get(0).text();
		this.methodType = panelHeadingElements.get(1).text();
		this.exampleUrl = panelHeadingElements.get(2).text();
		
		Elements panelBodyElements = methodElement.select(".panel-body p");
		for (Element panelBodyElement : panelBodyElements) {
			comments.add(panelBodyElement.text());
		}
	}

	@Override
	public String toString() {
		return "MethodBean [shortComment=" + this.shortComment + ", methodType=" + this.methodType + ", exampleUrl=" + this.exampleUrl + ", comments=" + this.comments + "]";
	}
}
