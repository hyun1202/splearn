package tobyspring.splearn.support.stereotype;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
@Transactional
public @interface ApplicationService {
}
