package org.example.demo.service;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;

/**
 * //TODO
 *
 * @author 彭耀煌
 * @date 2024/11/25 13:57
 */
@Service
public class TikaParserService {

    @Autowired
    private Tika tika;


    public void parser(Path srcPath) throws TikaException, IOException {
        String result = tika.parseToString(srcPath);
        System.out.println(result);
    }

}
