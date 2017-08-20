package com.pintabar.usermanagement.app.api.config;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.pintabar.commons.api.rest.mappers.AppExceptionMapper;
import com.pintabar.commons.api.rest.mappers.GenericExceptionMapper;
import com.pintabar.commons.api.rest.mappers.RestValidationExceptionMapper;
import com.pintabar.usermanagement.api.UserManagementAPI;
import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.validation.JAXRSBeanValidationInInterceptor;
import org.apache.cxf.jaxrs.validation.JAXRSBeanValidationOutInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * @author Lucas.Godoy on 22/07/17.
 */
@Configuration
public class CXFConfiguration {

	@Autowired
	private Bus bus;

	@Autowired
	private AppExceptionMapper appExceptionMapper;

	@Autowired
	private GenericExceptionMapper genericExceptionMapper;

	@Autowired
	private RestValidationExceptionMapper restValidationExceptionMapper;

	@Autowired
	private UserManagementAPI userManagementAPI;

	@Bean
	public Server rsServer() {
		JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
		endpoint.setBus(bus);
		endpoint.setAddress("/");
		setServiceBeans(endpoint);
		setProviders(endpoint);
		return endpoint.create();
	}

	private void setServiceBeans(JAXRSServerFactoryBean endpoint) {
		endpoint.setServiceBean(userManagementAPI);
	}

	private void setProviders(JAXRSServerFactoryBean endpoint) {
		endpoint.setProvider(new JacksonJsonProvider());
		endpoint.setProvider(appExceptionMapper);
		endpoint.setProvider(genericExceptionMapper);
		endpoint.setProvider(restValidationExceptionMapper);
		endpoint.setInInterceptors(Collections.singletonList(new JAXRSBeanValidationInInterceptor()));
		endpoint.setOutInterceptors(Collections.singletonList(new JAXRSBeanValidationOutInterceptor()));
	}
}
