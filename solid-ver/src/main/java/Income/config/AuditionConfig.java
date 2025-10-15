package Income.config;

/*
  @author Orynchuk
  @project moneycare
  @class AuditionConfig
  @version 1.0.0
  @since 15.10.2025 - 22.44
*/

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class AuditionConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }

}