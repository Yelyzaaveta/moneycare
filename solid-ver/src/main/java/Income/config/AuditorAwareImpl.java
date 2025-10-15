package Income.config;

/*
  @author Orynchuk
  @project moneycare
  @class AuditorAwareImpl
  @version 1.0.0
  @since 15.10.2025 - 22.44
*/

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(System.getProperty("user.name"));
    }
}