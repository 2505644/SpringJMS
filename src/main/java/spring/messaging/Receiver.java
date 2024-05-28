package spring.messaging;

import spring.db.JmsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;

import javax.jms.JMSException;
import javax.jms.TextMessage;

public class Receiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    @Autowired
    private JmsRepository jmsRepo;

    @JmsListener(destination = "${spring.activemq.destination}")
    public void receive(TextMessage textMessage) {
        try {
            LOGGER.debug("The received message: " + textMessage.getText());
            jmsRepo.writeToDB(textMessage);
        } catch (JMSException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
