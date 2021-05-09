package br.com.alura.ecomerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class EmailService {
    public static void main(String[] args) {
        var consumer = new KafkaConsumer<String, String>(properties());
        consumer.subscribe(Collections.singletonList("ECOMMERCE_SEND_EMAIL"));

        while (true){
            var records = consumer.poll(Duration.ofMillis(100));
            if(!records.isEmpty()){
                System.out.println("Registro "+ records.count() +" encontrado ");
            }
            for(var record : records){
                System.out.println("Send email");
                System.out.println(record.key());
                System.out.println(record.value());
                System.out.println(record.offset());
                System.out.println(record.topic());

                try{
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("email enviado");
            }
        }
    }


    //Criar propriedades do consumidor
    public static Properties properties(){
        var properties = new Properties();
        // Setando config do servidor
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        //Setando o serializador da chave
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //Setando o serializador do valor
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // Setando o grupo que vamos ouvir
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, EmailService.class.getSimpleName());
        return properties;

    }
}
