package com.amazonaws.samples.jms;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Created by yaozhang on 6/28/2016.
 */
public class TopicPublishClient {

    TopicConnection conn = null;
    TopicSession session = null;
    Topic topic = null;

    public void setupPubSub()
            throws JMSException, NamingException {
        InitialContext iniCtx = new InitialContext();
        Object tmp = iniCtx.lookup("ConnectionFactory");
        TopicConnectionFactory tcf = (TopicConnectionFactory) tmp;
        conn = tcf.createTopicConnection();
        topic = (Topic) iniCtx.lookup("topic/testTopic");
        session = conn.createTopicSession(false,
                TopicSession.AUTO_ACKNOWLEDGE);
        conn.start();
    }

    public void sendAsync(String text)
            throws JMSException, NamingException {
        System.out.println("Begin sendAsync");
        // Setup the pub/sub connection, session
        setupPubSub();
        // Send a text msg
        TopicPublisher send = session.createPublisher(topic);
        TextMessage tm = session.createTextMessage(text);
        send.publish(tm);
        System.out.println("sendAsync, sent text=" + tm.getText());
        send.close();
        System.out.println("End sendAsync");
    }

    public void stop()
            throws JMSException {
        conn.stop();
        session.close();
        conn.close();
    }

    public static void main(String args[])
            throws Exception {
        System.out.println("Begin TopicSendClient, now=" +
                System.currentTimeMillis());
        TopicPublishClient client = new TopicPublishClient();
        client.sendAsync("A text msg, now=" + System.currentTimeMillis());
        client.stop();
        System.out.println("End TopicSendClient");
        System.exit(0);
    }

}
