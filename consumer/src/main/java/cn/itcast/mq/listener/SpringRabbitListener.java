package cn.itcast.mq.listener;


import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class SpringRabbitListener {
    // @RabbitListener(queues = "simple.queue")
    // public void listenSimpleQueueMessage(String msg) throws InterruptedException {
    //     System.out.println("spring消费者接收到的消息: 【" + msg + "】");
    // }

    @RabbitListener(queues = "simple.queue")
    public void listenWorkQueueMessage1(String msg) throws InterruptedException {
        System.out.println("消费者1接收到的消息: 【" + msg + "】" + LocalDateTime.now());
        Thread.sleep(20);
    }

    @RabbitListener(queues = "simple.queue")
    public void listenWorkQueueMessage2(String msg) throws InterruptedException {
        System.err.println("消费者2接收到的消息: 【" + msg + "】" + LocalDateTime.now());
        Thread.sleep(200);
    }


    // fanout
    @RabbitListener(queues = "dev.queue1")
    public void listenFanoutQueue1(String msg) throws InterruptedException {
        System.out.println("消费者接收到fanout.queue1的消息: 【" + msg + "】");
    }

    @RabbitListener(queues = "dev.queue2")
    public void listenFanoutQueue2(String msg) throws InterruptedException {
        System.err.println("消费者接收到fanout.queue2的消息: 【" + msg + "】");
    }


    // direct
    @RabbitListener(bindings = @QueueBinding(value = @Queue(name = "direct.queue1"), exchange = @Exchange(name = "dev.direct", type = ExchangeTypes.DIRECT), key = {"red", "blue"}))
    public void listenDirectQueue1(String msg) {
        System.out.println("消费者接收到direct.queue1的消息: 【" + msg + "】");
    }


    @RabbitListener(bindings = @QueueBinding(value = @Queue(name = "direct.queue2"), exchange = @Exchange(name = "dev.direct", type = ExchangeTypes.DIRECT), key = {"red", "green"}))
    public void listenDirectQueue2(String msg) {
        System.out.println("消费者接收到direct.queue2的消息: 【" + msg + "】");
    }


    // Topic
    @RabbitListener(bindings = @QueueBinding(value = @Queue(name = "topic.queue1"), exchange = @Exchange(name = "dev.topic", type = ExchangeTypes.TOPIC), key = "china.#"))
    public void listenTopicQueue1(String msg) {
        System.out.println("消费者接收到topic.queue1的消息: 【" + msg + "】");
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue(name = "topic.queue2"), exchange = @Exchange(name = "dev.topic", type = ExchangeTypes.TOPIC), key = "#.news"))
    public void listenTopicQueue2(String msg) {
        System.out.println("消费者接收到topic.queue2的消息: 【" + msg + "】");
    }


    // 对象接收
    @RabbitListener(queues = "obj.queue")
    public void listenObjectQueue(Map<String, Object> msg) {
        System.out.println("接收到消息: => 【" + msg + "】");
    }

}
