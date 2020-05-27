package com.alicloud.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TracerConfig {
    @Bean
    public io.opentracing.Tracer tracer() {
        io.jaegertracing.Configuration config = new io.jaegertracing.Configuration("user");  //设置服务名称，和springboot工程名称可以保持一致
        io.jaegertracing.Configuration.SenderConfiguration sender = new io.jaegertracing.Configuration.SenderConfiguration();
        sender.withEndpoint("http://tracing-analysis-dc-bj.aliyuncs.com/adapt_hzyoy9rvkq@13c43744a1eb365_hzyoy9rvkq@53df7ad2afe8301/api/traces"); //阿里云接入点
        config.withSampler(new io.jaegertracing.Configuration.SamplerConfiguration().withType("const").withParam(1));
        config.withReporter(new io.jaegertracing.Configuration.ReporterConfiguration().withSender(sender).withMaxQueueSize(10000));
        return config.getTracer();
    }

    @Bean //必须new 一个RestTemplate并放入spring容器当中,否则启动时报错
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(30 * 1000);
        httpRequestFactory.setConnectTimeout(30 * 3000);
        httpRequestFactory.setReadTimeout(30 * 3000);
        return new RestTemplate(httpRequestFactory);
    }
}
