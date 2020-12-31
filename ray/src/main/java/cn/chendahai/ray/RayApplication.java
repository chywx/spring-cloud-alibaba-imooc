package cn.chendahai.ray;/**
 * @author chy
 * @date 2020/12/31 0031 下午 17:02
 * Description：
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 功能描述
 *
 * @author chy
 * @date 2020/12/31 0031
 */
@SpringBootApplication
@MapperScan("cn.chendahai.ray.dao")
public class RayApplication {

    public static void main(String[] args) {
        SpringApplication.run(RayApplication.class, args);
    }

}
