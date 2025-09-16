package com.duoc.bff.web;
import com.duoc.bff.libcore.config.LegacyDataConfig; import com.duoc.bff.libcore.config.DataLoader;
import org.springframework.boot.SpringApplication; import org.springframework.boot.autoconfigure.SpringBootApplication; import org.springframework.context.annotation.Import;
@SpringBootApplication @Import({LegacyDataConfig.class, DataLoader.class})
public class WebBffApplication { public static void main(String[] args){ SpringApplication.run(WebBffApplication.class, args); } }
