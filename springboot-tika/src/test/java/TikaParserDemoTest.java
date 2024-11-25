import org.apache.tika.exception.TikaException;
import org.example.demo.Main;
import org.example.demo.service.TikaParserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * //TODO
 *
 * @author 彭耀煌
 * @date 2024/11/25 14:26
 */
@SpringBootTest(classes = Main.class)
@RunWith(SpringRunner.class)
public class TikaParserDemoTest {

    @Autowired
    private TikaParserService tikaParserService;

    @Test
    public void testTikaParser() throws TikaException, IOException {
        tikaParserService.parser(Paths.get("F:", "Java开发手册（黄山版）.pdf"));
    }

}
