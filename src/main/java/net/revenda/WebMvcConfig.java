package net.revenda;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

    @Override
    public void addFormatters(@NonNull FormatterRegistry registry) {
        registry.addFormatter(new CpfFormatter());
    }
}
