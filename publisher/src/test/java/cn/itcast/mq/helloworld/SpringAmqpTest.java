package cn.itcast.mq.helloworld;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringAmqpTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSimpleQueue() {
        String queueName = "simple.queue";
        String message = "hello, spring amqp!";
        rabbitTemplate.convertAndSend(queueName, message);
    }

    @Test
    public void testWorkQueue() throws InterruptedException {
        String queueName = "simple.queue";
        String message = "hello, message => ";
        for (int i = 0; i < 50; i++) {
            rabbitTemplate.convertAndSend(queueName, message + i);
            // 睡眠20毫秒
            Thread.sleep(20);
        }
    }

    @Test
    public void testSendFanoutExchange() {
        // 交换机名称
        String exchangeName = "dev.fanout";
        // 消息
        String message = "Hello! Every One!";
        // 发送消息
        // 发送消息参数分别为：交换机名称、RoutingKey（暂时为空）、消息
        rabbitTemplate.convertAndSend(exchangeName, "", message);
    }

    @Test
    public void testSendDirectExchange() {
        // 交换机名称
        String exchangeName = "dev.direct";
        // 消息
        String message = "Hello! Gay勇!";
        // 发送消息
        // 发送消息参数分别为：交换机名称、RoutingKey（暂时为空）、消息
        rabbitTemplate.convertAndSend(exchangeName, "red", message);
    }

    @Test
    public void testSendTopicExchange() {
        // 交换机名称
        String exchangeName = "dev.topic";
        // 消息
        String message = "CHINA NUMBER_ONE!";
        // 发送消息
        // 发送消息参数分别为：交换机名称、RoutingKey（暂时为空）、消息
        // rabbitTemplate.convertAndSend(exchangeName, "china.news", message);
        rabbitTemplate.convertAndSend(exchangeName, "china.history", message);
    }


    @Test
    public void testSendObjQueue() {
        Map<String, Object> obj = new HashMap<>();
        obj.put("Name", "张三");
        obj.put("Age", 20);
        obj.put("Gender", true);
        obj.put("hobby", new String[]{"抽烟", "喝酒", "烫头", "敲代码"});
        rabbitTemplate.convertAndSend("obj.queue", obj);
    }
}
