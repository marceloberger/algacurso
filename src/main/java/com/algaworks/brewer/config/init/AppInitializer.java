package com.algaworks.brewer.config.init;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.algaworks.brewer.config.JPAConfig;
import com.algaworks.brewer.config.SecurityConfig;
import com.algaworks.brewer.config.ServiceConfig;
import com.algaworks.brewer.config.WebConfig;
import com.algaworks.brewer.tenant.TenancyAspect;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return new Class<?>[] {JPAConfig.class, ServiceConfig.class, TenancyAspect.class, SecurityConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return new Class<?>[] {WebConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return new String[] {"/"};
	}
	
	@Override
	protected Filter[] getServletFilters() {
		
		
		HttpPutFormContentFilter httpPutFormContentFilter = new HttpPutFormContentFilter();
		
		
		return new Filter[] { httpPutFormContentFilter};
		
		
		
	}
	
	@Override
	protected void customizeRegistration(Dynamic registration) {
		registration.setMultipartConfig(new MultipartConfigElement(""));
		//recebe no construtor new MultipartConfigElement("")
		
		/*@param maxFileSize o tamanho máximo permitido para arquivos carregados
	     * @param maxRequestSize o tamanho máximo permitido para
	     * Solicitações multipart / form-data
	     * @param fileSizeThreshold Obtém o limite de tamanho após o qual os arquivos serão gravados no disco.
     *
     * @return o limite de tamanho após o qual os arquivos serão gravados no disco*/
		
		
		
	}

}
