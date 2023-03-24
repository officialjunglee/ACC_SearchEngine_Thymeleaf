package com.acc.searchngine.frontend;

import com.acc.searchngine.model.ResultLists;
import com.acc.searchngine.model.SessionData;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.Request;
import com.sun.source.tree.Tree;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import org.jsoup.Connection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.acc.searchngine.processor.algo;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.util.*;

@Controller
public class AppController {

	@RequestMapping(value ="/hello" , method = RequestMethod.GET)
	public String sayHello(@ModelAttribute("data") SessionData data)
	{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("query",data.getQuery());
		return "/search?query="+data.getQuery();
	}

	@RequestMapping(value = "/search")
	public String search(ModelAndView modelAndView, @ModelAttribute("data") SessionData data, Model model) throws Exception{
			//modelAndView.addAttribute(search())
			String query = "football";
					//data.getQuery();
			modelAndView.setViewName("yellow");
			algo g1 = new algo();
			ArrayList results = g1.search(query);
		//ResultLists resultLists = new ResultLists();
		List<ResultLists> result = new ArrayList<>() ;
		for (int i=0; i< results.size();++i) {
			// Iterate over the TreeMap instance
			TreeMap<String,String> map = (TreeMap<String, String>) results.get(i);
			ResultLists obj = new ResultLists();
			for (Map.Entry<String, String> entry : map.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				if(key.equals("Blob"))
					obj.setBlob(value);
				else if(key.equals("Domain"))
					obj.setDomain(value);
				else if(key.equals("Link"))
					obj.setUrl(value);
				else
					obj.setOccurances(value);
			}
			result.add(obj);
		}
			modelAndView.addObject("results",result);
			model.addAttribute("results",result);
		return "yellow";
	}
}
