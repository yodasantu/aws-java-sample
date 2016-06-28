package com.amazonaws.samples.jms;

import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Created by yaozhang on 6/28/2016.
 */
public class TextMessageSender {
    public static void main(String args[]) throws JMSException {
        ExampleConfiguration config = ExampleConfiguration.parseConfig("TextMessageSender", args);

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
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = session.createProducer(session.createQueue(config.getQueueName()));

        sendMessages(session, producer);

        // Close the connection. This will close the session automatically
        connection.close();
        System.out.println("Connection closed");
    }

    private static void sendMessages(Session session, MessageProducer producer) {
        BufferedReader inputReader = new BufferedReader(
                new InputStreamReader(System.in, Charset.defaultCharset()));

        try {
            String input;
            while (true) {
                System.out.print("Enter message to send (leave empty to exit): ");
                input = inputReader.readLine();
                if (input == null || input.equals("")) break;

                TextMessage message = session.createTextMessage(input);
                producer.send(message);
                System.out.println("Send message " + message.getJMSMessageID());
            }
        } catch (EOFException e) {
            // Just return on EOF
        } catch (IOException e) {
            System.err.println("Failed reading input: " + e.getMessage());
        } catch (JMSException e) {
            System.err.println("Failed sending message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
