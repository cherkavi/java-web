package de.smava.recrt.jms;

import de.smava.recrt.exception.RecrtError;
import de.smava.recrt.exception.RecrtServiceException;
import de.smava.recrt.model.BankAccount;
import de.smava.recrt.service.BankAccountService;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import java.util.List;

@Service("bankAccountJmsProducer")
public class BankAccountJmsProducer implements BankAccountService {

    private static Logger LOG = Logger.getLogger(BankAccountJmsProducer.class);

    @Autowired
    private JmsTemplate template;

    private Destination destination;

    @Override
    public List<? extends BankAccount> getByAppUser(String appUserName) throws RecrtServiceException {
        throw new RecrtServiceException(new RecrtError("No asynchronous service to get bank account by user."));
    }

    @Override
    public BankAccount create(BankAccount account) throws RecrtServiceException {
        if (this.destination == null) {
            this.destination = new ActiveMQQueue(Constants.QUEUE_BANK_ACCOUNT_CREATE);
        }
        template.convertAndSend(destination, account);
        return null;
    }
}
