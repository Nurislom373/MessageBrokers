package org.khasanof;

import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.AdminClientConfig;

import java.util.Properties;

import static org.khasanof.GlobalConstants.BOOTSTRAP_SERVER;

/**
 * @author Nurislom
 * @see org.khasanof
 * @since 4/30/2024 6:12 PM
 */
public abstract class KafkaAdminFactory {

    /**
     *
     * @return
     */
    public static Admin create() {
        return Admin.create(getProperties());
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        return properties;
    }
}
