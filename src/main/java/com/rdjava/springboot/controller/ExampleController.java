package com.rdjava.springboot.controller;

import com.rdjava.springboot.constant.LogConstants;
import com.rdjava.springboot.service.ExampleService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("example")
@Log4j2
public class ExampleController {
    @Autowired
    private final ExampleService exampleService;

    @GetMapping("/flatten-list")
    public List<String> flattenList() {
        log.info(LogConstants.LOG_POINT);
        return exampleService.getFlattenList();
    }

    @GetMapping("/map-list")
    public List<String> mapLists() {
        log.info(LogConstants.LOG_POINT);
        return exampleService.getMapList();
    }
}
