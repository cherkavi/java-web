package de.smava.recrt.jms.config;

import de.smava.recrt.jms.Constants;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.destination.DynamicDestinationResolver;

import javax.jms.Session;

@Configuration
@EnableJms
@ComponentScan({"de.smava.recrt.jms"})
public class JmsConfig {

    @Bean
    public JmsTemplate getJmsTemplate() {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setDefaultDestination(new ActiveMQQueue(Constants.TOPIC_DEFAULT));
        template.setExplicitQosEnabled(true);
        template.setDeliveryPersistent(false);
        template.setTimeToLive(60000);
        template.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
        template.setMessageConverter(getMessageConverter());
        return template;
    }

    @Bean
    public DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setPubSubDomain(false);
        factory.setDestinationResolver(new DynamicDestinationResolver());
        factory.setConcurrency("1");
        factory.setMessageConverter(getMessageConverter());
        return factory;
    }

    private ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL("vm://localhost");
        return factory;
    }

    private MessageConverter getMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTypeIdPropertyName("JMSType");
        return converter;
    }

}
