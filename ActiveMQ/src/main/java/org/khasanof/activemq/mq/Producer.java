package org.khasanof.activemq.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.khasanof.activemq.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Queue;

@RestController
@RequestMapping(value = "/produce")
public class Producer {

    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private Queue queue;

    @PostMapping
    public Student sendMessage(@RequestBody Student student) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String value = objectMapper.writeValueAsString(student);
            jmsTemplate.convertAndSend(queue, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return student;
    }
}
