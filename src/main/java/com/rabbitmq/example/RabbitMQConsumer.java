package com.rabbitmq.example;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.*;
public class RabbitMQConsumer {

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
    String queueName = "myQueue";
    String routingKey = "testRoute";
    boolean durable = true;

    channel.exchangeDeclare(exchangeName, "direct", durable);
    channel.queueDeclare(queueName, durable);
    channel.queueBind(queueName, exchangeName, routingKey);

    boolean noAck = false;
    QueueingConsumer consumer = new QueueingConsumer(channel);
    channel.basicConsume(queueName, noAck, consumer);

    boolean runInfinite = true;
    while (runInfinite) {
      QueueingConsumer.Delivery delivery;
      try {
        delivery = consumer.nextDelivery();
      } catch (InterruptedException ie) {
        continue;
      }
      long deliveryTag = delivery.getEnvelope().getDeliveryTag();
      System.out.println("Message received: [" + deliveryTag + ", "+  delivery.getProperties().getAppId()+"] " + new String(delivery.getBody()));
      channel.basicAck(deliveryTag, false); 
    }
    channel.close();
    conn.close();
  }
} 
