package com.rabbitmq.example;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.*;

public class RabbitMQProducer {

    private static final String HOSTNAME = "127.0.0.1";
    private static final int PORT = 5672;

  public static void main(String []args) throws Exception {

      ConnectionFactory factory = new ConnectionFactory();
      factory.setUsername("guest");
      factory.setPassword("guest");
      factory.setVirtualHost("/");
      factory.setHost(HOSTNAME);
      factory.setPort(PORT);
      factory.setRequestedHeartbeat(0);

    Connection conn = factory.newConnection();
    Channel channel = conn.createChannel();

    String exchangeName = "myExchange";
    String routingKey = "testRoute";
 
    int count = 1;
    if (args.length == 1) {
      count = Integer.parseInt(args[0]);
    }
    for (int i = 0; i < count; i++) {
      byte[] messageBodyBytes = ("Hello["+ (i + 1) + "], world!").getBytes();

        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .contentType("text/plain")
                .deliveryMode(2)
                .priority(1)
                .appId("me")
                .replyTo("retTestRoute")
                .build();

      channel.basicPublish(exchangeName, routingKey, properties, messageBodyBytes) ;
    }

    channel.close();
    conn.close();
  }
}
