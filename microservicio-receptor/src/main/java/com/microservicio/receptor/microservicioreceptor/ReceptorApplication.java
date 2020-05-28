package com.microservicio.receptor.microservicioreceptor;

//import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
 
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
//import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmq.examples.QueueingConsumer;

@SpringBootApplication
public class ReceptorApplication {

    private final static String QUEUE_NAME = "MAIN_QUEUE";

	public static void main(String[] args) throws IOException, TimeoutException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
    
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] A la espera de mensajes. Para salir pulse: CTRL+C");
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, true, consumer);
    
        while (true) {
        QueueingConsumer.Delivery delivery = consumer.nextDelivery();
        String message = new String(delivery.getBody());
        System.out.println(" [x] Recibido: '" + message + "'");
        doWork(message);
        System.out.println(" [x] Hecho!!! ");
        }
    }
    
    private static void doWork(String task) throws InterruptedException {
        Thread.sleep(8000);
      }
}