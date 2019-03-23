package com.kengste.rabbitmq;

import com.kengste.rabbitmq.dto.UserDetails;
import com.kengste.rabbitmq.util.ApplicationConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/userservice")
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final RabbitTemplate rabbitTemplate;
    private MessageSender messageSender;

    @Autowired
    public UserService(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public MessageSender getMessageSender() {
        return messageSender;
    }

    @Autowired
    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }


    @RequestMapping(path = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendMessage(@RequestBody UserDetails user) {

        String exchange = RabbitmqApplication.APP_EXCHANGE_1;
        String routingKey = RabbitmqApplication.APP_ROUTING_KEY_1;

        /* Sending to Message Queue */
        try {
            messageSender.sendMessage(rabbitTemplate, exchange, routingKey, user);
            return new ResponseEntity<String>(ApplicationConstant.IN_QUEUE, HttpStatus.OK);

        } catch (Exception ex) {
            log.error("Exception occurred while sending message to the queue. Exception= {}", ex);
            return new ResponseEntity(ApplicationConstant.MESSAGE_QUEUE_SEND_ERROR,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



}