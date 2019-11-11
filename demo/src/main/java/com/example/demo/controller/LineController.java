package com.example.demo.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import com.example.demo.domain.StatusString;
import com.example.demo.function.ReadLine;

/**
 * Restful Controller with only three urls
 * Only support method GET
 *
 */
@RestController
@RequestMapping(value="/")
public class LineController {

	/**
	 * Get Method with url line/{id}
	 * @param lineid -> line number
	 * @param response
	 * @return http response with status code and content from readLine with the line number
	 */
	@RequestMapping(value="/line/{lineid}",method=RequestMethod.GET)
	public String findLineFromDoc(@PathVariable(value="lineid") long lineid,
			HttpServletResponse response) {
		String res = "Line: "+lineid+"\n";
		StatusString ss = readLine.read(lineid);
		res+=ss.getContent();
		response.setStatus(ss.getStatus());
		return res;
	}
	
	/**
	 * if line with no line id after, the id should be default 0
	 * @param response
	 * @return the first line in the file
	 */
	@RequestMapping(value="/line",method=RequestMethod.GET)
	public String findFirstLineFromDoc(HttpServletResponse response) {
		String res = "Line: "+1+"\n";
		StatusString ss = readLine.read(1L);
		res+=ss.getContent();
		response.setStatus(ss.getStatus());
		return res;
	}
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private ReadLine readLine;
	
	/**
	 * Default url only for test purpose
	 * @return status string
	 */
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String test() {
		String res = "run success\t";
		try {
			ReadLine rl = context.getBean(ReadLine.class);
			res+= rl.getFileAddress().equals("")?"No File provided!":"File Address: "+rl.getFileAddress();
		}catch(Exception e) {
			res+="Error Happen!!";
		}
		return res;
	}
}
