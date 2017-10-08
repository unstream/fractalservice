package net.unstream.fractalservice;


import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

import io.swagger.jaxrs.config.BeanConfig;

/**
 * JAX-RS application.
 * 
 */
@ApplicationPath("api")
public class RESTConfiguration extends Application {

  public RESTConfiguration() {
    BeanConfig beanConfig = new BeanConfig();
    beanConfig.setVersion("1.0.1");
    beanConfig.setSchemes(new String[] {"http"});
    beanConfig.setHost("localhost:8080");
    beanConfig.setBasePath("/api");
    beanConfig.setResourcePackage("net.unstream.fractalservice");
    beanConfig.setScan(true);
  }
  

  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> resources = new HashSet<Class<?>>();
    resources.add(net.unstream.fractalservice.CORSFilter.class);
    resources.add(net.unstream.fractalservice.boundary.FractalService.class);
    resources.add(net.unstream.fractalservice.boundary.LongQuad.class);
    resources.add(io.swagger.jaxrs.listing.ApiListingResource.class);
    resources.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);
    return resources;
  }

}
