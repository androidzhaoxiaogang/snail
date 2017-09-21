package com.snail.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Utility class to load a Spring profile to be used as default
 * when there is no <code>spring.profiles.active</code> set in the environment or as command line argument.
 * If the value is not available in <code>application.yml</code> then <code>dev</code> profile will be used as default.
 */
public final class DefaultProfileUtil {

    private static final long serialVersionUID = 1L;

    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";

    public static final String SPRING_PROFILE_PRODUCTION = "prod";
    public static final String SPRING_PROFILE_TEST = "test";

    public static final String SPRING_PROFILE_CLOUD = "cloud";

    private static final Logger log = LoggerFactory.getLogger(DefaultProfileUtil.class);

    private static final String SPRING_PROFILE_ACTIVE = "spring.profiles.active";

    private static final Properties BUILD_PROPERTIES = readProperties();

    /**
     * Get a default profile from <code>application.yml</code>.
     */
    public static String getDefaultActiveProfiles(){
        if (BUILD_PROPERTIES != null) {
            String activeProfile = BUILD_PROPERTIES.getProperty(SPRING_PROFILE_ACTIVE);
            if (activeProfile != null && !activeProfile.isEmpty() &&
                (activeProfile.contains(SPRING_PROFILE_DEVELOPMENT)
                        ||activeProfile.contains(SPRING_PROFILE_PRODUCTION)
                    ||activeProfile.contains(SPRING_PROFILE_TEST))) {
                return activeProfile;
            }
        }
        log.warn("No Spring profile configured, running with default profile: {}", SPRING_PROFILE_DEVELOPMENT);
        return SPRING_PROFILE_DEVELOPMENT;
    }

    /**
     * Set a default to use when no profile is configured.
     */
    public static void addDefaultProfile(SpringApplication app) {
        Map<String, Object> defProperties =  new HashMap<>();

        defProperties.put(SPRING_PROFILE_ACTIVE, getDefaultActiveProfiles());
        app.setDefaultProperties(defProperties);
    }

    /**
     * Load application.yml from classpath.
     */
    private static Properties readProperties() {
        try {
            Resource resource = new ClassPathResource("/application.properties");
            return PropertiesLoaderUtils.loadProperties(resource);
        } catch (Exception e) {
            log.error("Failed to read application.properties to get default profile");
        }
        return null;
    }
}
