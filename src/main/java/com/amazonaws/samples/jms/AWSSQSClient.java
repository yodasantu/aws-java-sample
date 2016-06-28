package com.amazonaws.samples.jms;

/**
 * Created by yaozhang on 6/28/2016.
 */
public class AWSSQSClient {

    public static void main(String[] args) {
//
//        // Create the connection factory using the environment variable credential provider.
//// Connections this factory creates can talk to the queues in us-east-1 region.
//        SQSConnectionFactory connectionFactory =
//                SQSConnectionFactory.builder()
//                        .withRegion(Region.getRegion(Regions.US_EAST_1))
//                        .withAWSCredentialsProvider(new EnvironmentVariableCredentialsProvider())
//                        .build();
//
//        // Create the connection.
//        SQSConnection connection = connectionFactory.createConnection();
//
//
//        // Get the wrapped client
//        AmazonSQSMessagingClientWrapper client = connection.getWrappedAmazonSQSClient();
//
//// Create an SQS queue named 'TestQueue' – if it does not already exist.
//        if (!client.queueExists("TestQueue")) {
//            client.createQueue("TestQueue");
//        }
//
//        // Create the non-transacted session with AUTO_ACKNOWLEDGE mode
//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//
//
//        // Create a queue identity with name 'TestQueue' in the session
//        Queue queue = session.createQueue("TestQueue");
//
//        // Create a producer for the 'TestQueue'.
//        MessageProducer producer = session.createProducer(queue);
//
//        // Create the text message.
//        TextMessage message = session.createTextMessage("Hello World!");
//
//// Send the message.
//        producer.send(message);
//        System.out.println("JMS Message " + message.getJMSMessageID());
//
//
//
//
//
//        // Create a consumer for the 'TestQueue'.
//        MessageConsumer consumer = session.createConsumer(queue);
//
//// Start receiving incoming messages.
//        connection.start();
//
//        // Receive a message from 'TestQueue' and wait up to 1 second
//        Message receivedMessage = consumer.receive(1000);
//
//// Cast the received message as TextMessage and print the text to screen.
//        if (receivedMessage != null) {
//            System.out.println("Received: " + ((TextMessage) receivedMessage).getText());
//        }
//
//
//        // Close the connection (and the session).
//        connection.close();


        /*
        JMS Message ID:8example-588b-44e5-bbcf-d816example2
Received: Hello World!
         */

    }
}
