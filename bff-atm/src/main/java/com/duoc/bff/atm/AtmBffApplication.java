package com.duoc.bff.atm;
import com.duoc.bff.libcore.config.LegacyDataConfig; import com.duoc.bff.libcore.config.DataLoader;
import org.springframework.boot.SpringApplication; import org.springframework.boot.autoconfigure.SpringBootApplication; import org.springframework.context.annotation.Import;
@SpringBootApplication @Import({LegacyDataConfig.class, DataLoader.class})
public class AtmBffApplication { public static void main(String[] args){ SpringApplication.run(AtmBffApplication.class, args); } }
