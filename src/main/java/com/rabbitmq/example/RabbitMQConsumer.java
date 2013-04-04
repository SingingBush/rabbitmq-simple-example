package com.rabbitmq.example;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.*;

public class RabbitMQConsumer {

    private static final String HOSTNAME = "127.0.0.1";
    private static final int PORT = 5672;
    private static final String EXCHANGE_NAME = "myExchange";

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
        factory.setHost(HOSTNAME);
        factory.setPort(PORT);
        factory.setRequestedHeartbeat(0);

        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        String routingKey = "testRoute";
        boolean durable = true;

        channel.exchangeDeclare(EXCHANGE_NAME, "direct", durable);
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, routingKey);

        boolean autoAck = false;
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, autoAck, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery;
            try {
                delivery = consumer.nextDelivery();
            } catch (InterruptedException ie) {
                continue;
            }
            long deliveryTag = delivery.getEnvelope().getDeliveryTag();
            System.out.printf("Message received: [%s, %s] %s",
                              deliveryTag,
                              delivery.getProperties().getAppId(),
                              new String(delivery.getBody()));
            channel.basicAck(deliveryTag, false);
        }
    }
} 
