package com.shamee.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import proto.shamee.PersonProto;
import proto.shamee.ResponseProto;

@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping(value = "/index", produces = "application/x-protobuf")
    public ResponseProto.Response index(@RequestBody PersonProto.Person person){

        return ResponseProto.Response.newBuilder()
                .setCode(200)
                .setMessage("OK")
                .setData("hello:" + person.getName()).build();
    }

}
