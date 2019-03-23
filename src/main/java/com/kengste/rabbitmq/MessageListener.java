package com.kengste.rabbitmq;

import com.kengste.rabbitmq.dto.UserDetails;
import com.kengste.rabbitmq.util.ApplicationConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class MessageListener {

    private static final Logger log = LoggerFactory.getLogger(MessageListener.class);

    @RabbitListener(queues = RabbitmqApplication.APP_QUEUE_1)
    public void receiveMessageForApp1(final UserDetails data) {
        log.info("Received message: {} from app1 queue.", data);

        try {
            log.info("Making REST call to the API");
            log.info("<< Exiting receiveMessageForApp1() after API call.");
        } catch (HttpClientErrorException ex) {
            if(ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.info("Delay...");
                try {
                    Thread.sleep(ApplicationConstant.MESSAGE_RETRY_DELAY);
                } catch (InterruptedException e) { }

                log.info("Throwing exception so that message will be requed in the queue.");
                // Note: Typically Application specific exception should be thrown below
                throw new RuntimeException();
            } else {
                throw new AmqpRejectAndDontRequeueException(ex);
            }

        } catch(Exception e) {
            log.error("Internal server error occurred in API call. Bypassing message requeue {}", e);
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }

    @RabbitListener(queues = RabbitmqApplication.APP_QUEUE_2)
    public void receiveMessageForApp2(final UserDetails data) {
        log.info("Received message: {} from app2 queue.", data);

        try {
            log.info("Making REST call to the API");
            log.info("<< Exiting receiveMessageForApp2() after API call.");
        } catch (HttpClientErrorException ex) {
            if(ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.info("Delay...");
                try {
                    Thread.sleep(ApplicationConstant.MESSAGE_RETRY_DELAY);
                } catch (InterruptedException e) { }

                log.info("Throwing exception so that message will be requed in the queue.");
                // Note: Typically Application specific exception should be thrown below
                throw new RuntimeException();
            } else {
                throw new AmqpRejectAndDontRequeueException(ex);
            }

        } catch(Exception e) {
            log.error("Internal server error occurred in API call. Bypassing message requeue {}", e);
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }

}
