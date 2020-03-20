package com.progmasters.mars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class MarsApplication {
    public static void main(String[] args) {
        SpringApplication.run(MarsApplication.class, args);
    }
    //TODO Kezd 'dagadni' az alkamazás, és híznak a package-ek. Ilyenkor érdemes elgondolkozni valami újabb, átláthatóbb
    // felépítésen, jelenleg layer-enként/szerepenként vannak szétbontva az osztályok, ezt lehetne optimalizálni tovább,
    // ha még feature szerint is bontanátok
    // (külön package ami user-hez, külön ami institutehoz tartozik stb és az alá minden DTO,Domain,Service,repo, controller...)

    //TODO Vannak benne vagány megoldások! hajrá! ;)
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

}
