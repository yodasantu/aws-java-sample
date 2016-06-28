package com.amazonaws.samples.jms;

import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import java.util.concurrent.TimeUnit;

/**
 * Created by yaozhang on 6/28/2016.
 */
public class SyncMessageReceiver {

    public static void main(String args[]) throws JMSException {
        ExampleConfiguration config = ExampleConfiguration.parseConfig("SyncMessageReceiver", args);

        ExampleCommon.setupLogging();

        // Create the connection factory based on the config
        SQSConnectionFactory connectionFactory =
                SQSConnectionFactory.builder()
                        .withRegion(config.getRegion())
                        .withAWSCredentialsProvider(config.getCredentialsProvider())
                        .build();

        // Create the connection
        SQSConnection connection = connectionFactory.createConnection();

        // Create the queue if needed
        ExampleCommon.ensureQueueExists(connection, config.getQueueName());

        // Create the session
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        MessageConsumer consumer = session.createConsumer(session.createQueue(config.getQueueName()));

        connection.start();

        receiveMessages(session, consumer);

        // Close the connection. This will close the session automatically
        connection.close();
        System.out.println("Connection closed");
    }

    private static void receiveMessages(Session session, MessageConsumer consumer) {
        try {
            while (true) {
                System.out.println("Waiting for messages");
                // Wait 1 minute for a message
                Message message = consumer.receive(TimeUnit.MINUTES.toMillis(1));
                if (message == null) {
                    System.out.println("Shutting down after 1 minute of silence");
                    break;
                }
                ExampleCommon.handleMessage(message);
                message.acknowledge();
                System.out.println("Acknowledged message " + message.getJMSMessageID());
            }
        } catch (JMSException e) {
            System.err.println("Error receiving from SQS: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
