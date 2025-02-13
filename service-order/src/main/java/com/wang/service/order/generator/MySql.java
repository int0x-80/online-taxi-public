package com.wang.service.order.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-27:PM2:22
 */
public class MySql {

    public static void main(String[] args) {
        FastAutoGenerator.create(
                        "jdbc:mysql://localhost:3306/service-order?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC",
                        "root",
                        "root")
                .globalConfig(builder -> {
                    builder.author("Wang Yinuo") // 设置作者
                            .fileOverride() // 开启生成SpringBoot项目
                            .outputDir("/Users/wangyinuo/Downloads/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.wang.service.order") // 设置父包名
                            .pathInfo(Collections.singletonMap(
                                    OutputFile.mapperXml,
                                    "/Users/wangyinuo/Downloads/java/com/wang/service/driver/user/mapper"
                            ));// 设置父包模块名

                })
                .strategyConfig(builder -> {
                    builder.addInclude("order");
                })
                .templateEngine(new FreemarkerTemplateEngine()).execute();

    }
}
