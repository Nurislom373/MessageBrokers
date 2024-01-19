package org.khasanof.config;

import org.khasanof.springamqp.config.RabbitConfiguration;
import org.khasanof.springamqp.config.RoutingConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Nurislom
 * @see org.khasanof.config
 * @since 1/20/2024 12:29 AM
 */
@Configuration
@Import({RabbitConfiguration.class, RoutingConfiguration.class})
public class SpringImportConfiguration {
}
