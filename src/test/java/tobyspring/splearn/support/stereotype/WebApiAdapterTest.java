package tobyspring.splearn.support.stereotype;


import jakarta.transaction.Transactional;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import tobyspring.splearn.SplearnTestConfiguration;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
@AutoConfigureMockMvc
public @interface WebApiAdapterTest {
}
