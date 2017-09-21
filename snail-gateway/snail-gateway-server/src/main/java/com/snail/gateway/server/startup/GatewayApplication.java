package com.snail.gateway.server.startup;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Created by Administrator on 2017/9/19.
 */

@SpringBootApplication
@EnableZuulProxy
public class GatewayApplication {
}
