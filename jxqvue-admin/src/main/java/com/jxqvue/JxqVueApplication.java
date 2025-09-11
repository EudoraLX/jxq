package com.jxqvue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author jxqvue
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class JxqVueApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(JxqVueApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  金鑫泉启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                " .-------.       ____     __        \n" +
                " ''-'   `'-'    `-..-'              ");
    }
}
