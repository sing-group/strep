package org.strep.configuration;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Stablish the configuration for e-mail sending
 * Porperties are captured from the application.properties file
 * Made using (Lokesh Gupta)
 *   https://howtodoinjava.com/spring-core/send-email-with-spring-javamailsenderimpl-example/
 * @author José Ramón Méndez
 */
@Configuration
public class EmailConfig
{

    /**
     * The mail host used for sending messages
     */
    @Value("${spring.mail.host}")
    private String mailHost;

    /**
     * The port used for connecting mailHost
     */
    @Value("${spring.mail.port}")
    private String mailPort;

    /**
     * The username used for connecting mailHost
     */
    @Value("${spring.mail.username}")
    private String mailUsername;

    /**
     * The password used for connecting mailHost
     */
    @Value("${spring.mail.password}")
    private String mailPassword;

    /**
     * The mail transport protocol used to send the message
     */
    @Value("${spring.mail.properties.mail.transport.protocol}")
    private String mailTransportProtocol;

    /**
     * true if SMTP is athenticated
     */
    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String mailSMTPAuth;

    /**
     * true if startTLS should be enabled
     */
    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String mailStartTLS;

    /**
     * SSL trusted hosts
     */
    @Value("${spring.mail.properties.mail.smtp.ssl.trust}")
    private String mailSSLTrust;

    /**
     * True if Mail debug is enabled
     */
    @Value("${spring.mail.properties.mail.debug}")
    private String mailDebug;

    @Bean
    public JavaMailSender getJavaMailSender()
    {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailHost);
        mailSender.setPort(Integer.parseInt(mailPort));
          
        mailSender.setUsername(mailUsername);
        mailSender.setPassword(mailPassword);
          
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", mailTransportProtocol);
        props.put("mail.smtp.auth", mailSMTPAuth);
        props.put("mail.smtp.starttls.enable", mailStartTLS);
        props.put("mail.smtp.ssl.trust", mailSSLTrust);
        props.put("mail.debug", mailDebug);
          
        return mailSender;
    }
}