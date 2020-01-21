package org.strep.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.Context;

@Configuration
public class ConnectorConfig {
 
/**
 * Configuration bean to implement https
 * @return a tomcat server
 */
@Bean
public ServletWebServerFactory servletContainer() {
    TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
        @Override
        protected void postProcessContext(Context context) {
            //Uncomment the following block if you want only SSL queries
            /*
            SecurityConstraint securityConstraint = new SecurityConstraint();
            securityConstraint.setUserConstraint("CONFIDENTIAL");
            SecurityCollection collection = new SecurityCollection();
            collection.addPattern("/*");
            securityConstraint.addCollection(collection);
            context.addConstraint(securityConstraint);
            */
        }
    };
    //Comment out the following line if you want ssl
    //tomcat.addAdditionalTomcatConnectors(redirectConnector());

    //Comment the following 3 files if SSL is not desired
    Connector connector8080 = new Connector("org.apache.coyote.http11.Http11NioProtocol");
    connector8080.setPort(80);
    tomcat.addAdditionalTomcatConnectors(connector8080);

    return tomcat;
}

/**
 * This method redirects to port 8443 when an user sends a request to port 8080
 * @return a connection to port 8443
 */
private Connector redirectConnector() {
    Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
    connector.setScheme("http");
    connector.setPort(8080);
    connector.setSecure(false);
    connector.setRedirectPort(8443);
    return connector;
}
}