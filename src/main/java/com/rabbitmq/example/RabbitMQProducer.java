package com.rabbitmq.example;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.*;

public class RabbitMQProducer {
  public static void main(String []args) throws Exception {
    ConnectionParameters params = new ConnectionParameters();
    params.setUsername("guest");
    params.setPassword("guest");
    params.setVirtualHost("/");
    params.setRequestedHeartbeat(0);

    ConnectionFactory factory = new ConnectionFactory(params);
    Connection conn = factory.newConnection("127.0.0.1", 5672);
    Channel channel = conn.createChannel();

    String exchangeName = "myExchange";
    String routingKey = "testRoute";
 
    int count = 1;
    if (args.length == 1) {
      count = Integer.parseInt(args[0]);
    }
    for (int i = 0; i < count; i++) {
      byte[] messageBodyBytes = ("Hello["+ (i + 1) + "], world!").getBytes();
      AMQP.BasicProperties properties = MessageProperties.PERSISTENT_TEXT_PLAIN;
      properties.setAppId("me");
      properties.setReplyTo("retTestRoute");
      channel.basicPublish(exchangeName, routingKey, properties, messageBodyBytes) ;
    }

    channel.close();
    conn.close();
  }
}
