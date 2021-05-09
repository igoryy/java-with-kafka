package br.com.alura.ecomerce;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class NewOrder {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // create new producer
        var produces = new KafkaProducer<String, String>(properties());

        //Create value and key
        var value = "1001, 1233, 90999";

        //Create record
        var record = new ProducerRecord<>("ECOMMERCE_NEW_ORDER" , value , value);

        Callback callback = (data, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }
            System.out.println("Sucesso enviado");
        };

        var email =  "Thank you for yout order !";
        var emailRecord=  new ProducerRecord<>("ECOMMERCE_SEND_EMAIL" , email ,email);

        produces.send(record, callback).get();
        produces.send(emailRecord, callback).get();

    }

    //Criar propriedades do consumidor
    public static Properties properties(){
        var properties = new Properties();

        // Setando config do servidor
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");

        //Setando o serializador da chave
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //Setando o serializador do valor
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return properties;

    }

}
