package me.jorgeb.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ButacasBackendMainTest {

    @Test
    @DisplayName("La aplicación se inicia correctamente")
    public void contextLoads() {
    }

}
