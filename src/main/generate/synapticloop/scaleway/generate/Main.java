package synapticloop.scaleway.generate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;

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

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import synapticloop.scaleway.generate.bean.RequestBean;

public class Main {
	private static List<RequestBean> REQUEST_BEANS = new ArrayList<RequestBean>();

	public static void main(String[] args) {
		Document document = null;
		try {
			document = Jsoup.parse(new File("./api-docs/index.html"), "UTF-8");
			Elements commandElements = document.select(".panel.panel-default");
			for (Element element : commandElements) {
				REQUEST_BEANS.add(new RequestBean(element));
			}
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		
		for (RequestBean requestBean : REQUEST_BEANS) {
			System.out.println(requestBean.toString());
		}
	}
}
