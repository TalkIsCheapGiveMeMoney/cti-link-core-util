package com.tinet.ctilink.web;

import com.tinet.ctilink.util.ContextUtil;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 通过代理的方式让Spring注入Servlet
 * 
 * @author Jiangsl
 *
 */
@SuppressWarnings("serial")
public class DelegatingServletProxy extends GenericServlet {
	private String targetBean;
	private Servlet proxy;

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		proxy.service(req, res);
	}

	@Override
	public void init() throws ServletException {
		this.targetBean = getServletName();
		this.proxy = (Servlet) ContextUtil.getContext().getBean(targetBean);
		proxy.init(getServletConfig());
	}
}