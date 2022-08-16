package org.khasanof.apachekafka.transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/transaction/*")
@RequiredArgsConstructor
public class TransactionalController {

    private final TransactionalService service;

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody TransactionalEntity entity) {
        service.create(entity);
        return new ResponseEntity<>("Successfully Created - Transactional", HttpStatus.CREATED);
    }
}
