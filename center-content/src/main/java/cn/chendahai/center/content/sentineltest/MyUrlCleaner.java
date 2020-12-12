package cn.chendahai.center.content.sentineltest;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.UrlCleaner;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;

//@Component
@Slf4j
public class MyUrlCleaner implements UrlCleaner {

    @Override
    public String clean(String originUrl) {
        // 让 /shares/1 与 /shares/2 的返回值相同
        // 返回/shares/{number}

        String[] split = originUrl.split("/");

        return Arrays.stream(split)
            .map(string -> {
                if (NumberUtils.isNumber(string)) {
                    return "{number}";
                }
                return string;
            })
            .reduce((a, b) -> a + "/" + b)
            .orElse("");
    }

    // 计算返奖
    public static void main(String[] args) {
        BigDecimal amount = new BigDecimal("20.00");
        BigDecimal odds = new BigDecimal("1.850");

        BigDecimal winAmount = amount.multiply(odds);
        BigDecimal rewardAmount = winAmount.subtract(winAmount.subtract(amount).multiply(new BigDecimal("0.20"))).setScale(2, BigDecimal.ROUND_FLOOR);
        System.out.println(rewardAmount);

    }
}
