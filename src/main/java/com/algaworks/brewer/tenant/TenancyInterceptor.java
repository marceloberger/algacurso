package com.algaworks.brewer.tenant;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class TenancyInterceptor extends HandlerInterceptorAdapter {
	
	private static final String TENANT_ID = "TENANT_ID";
	
	
		@SuppressWarnings("unchecked")
		@Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
				throws Exception {
			  
			
		
			Map<String, Object> variaveis = (Map<String, Object>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
			
			final String tenantId = "tenantid";
			
			if(variaveis.containsKey(tenantId)) {
				
				request.setAttribute(TENANT_ID, variaveis.get(tenantId));
			}
			return true;
		}
		
		
		public static String getTenantId() {
			
			RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
			
			if(attributes != null) {
				
				return (String) attributes.getAttribute(TENANT_ID, RequestAttributes.SCOPE_REQUEST);
			}
			
			return null;
			
			
		}
	

}
