package br.senac.backend.swagger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import io.swagger.jaxrs.config.BeanConfig;

public class SwaggerConfigurationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		BeanConfig beanCfg = new BeanConfig();
		beanCfg.setBasePath("");
		beanCfg.setHost("localhost:8080");
		beanCfg.setTitle("Tooeater API Doc");
		beanCfg.setResourcePackage("br.senac.backend");
		beanCfg.setPrettyPrint(true);
		beanCfg.setScan(true);
		beanCfg.setSchemes(new String[] {"http"});
		beanCfg.setVersion("1.0");
	}

}
