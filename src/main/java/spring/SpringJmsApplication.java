package spring;

import spring.db.JmsRepository;
import spring.messaging.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class SpringJmsApplication implements ApplicationRunner {

    private Sender sender;

    private JmsRepository jmsRepo;

    @Autowired
    public void setSender(Sender sender) {
        this.sender = sender;
    }

    @Autowired
    public void setJmsRepo(JmsRepository jmsRepo) {
        this.jmsRepo = jmsRepo;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        for (int i = 0; i < 5; i++) {
            TimeUnit.SECONDS.sleep(1);
            sender.send(i);
        }
        TimeUnit.SECONDS.sleep(3);
        jmsRepo.showDataInDB();
        System.exit(0);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringJmsApplication.class, args);
    }
}
