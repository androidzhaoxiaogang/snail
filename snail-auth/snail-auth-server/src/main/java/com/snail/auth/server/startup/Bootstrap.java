package com.snail.auth.server.startup;

import com.snail.web.config.DefaultProfileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

import static com.snail.web.config.DefaultProfileUtil.SPRING_PROFILE_CLOUD;
import static com.snail.web.config.DefaultProfileUtil.SPRING_PROFILE_DEVELOPMENT;
import static com.snail.web.config.DefaultProfileUtil.SPRING_PROFILE_PRODUCTION;

@EnableAutoConfiguration
@SpringBootApplication
public class Bootstrap {
	private static final Logger LOGGER = LoggerFactory.getLogger(Bootstrap.class);

	@Autowired
	private Environment env;

	@PostConstruct
	public void initApplication() {
		LOGGER.info("Running with Spring profile(s) : {}", Arrays.toString(env.getActiveProfiles()));
		Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
		if (activeProfiles.contains(SPRING_PROFILE_DEVELOPMENT) &&
				activeProfiles.contains(SPRING_PROFILE_PRODUCTION)) {
			LOGGER.error("You have misconfigured your application! It should not run " +
					"with both the 'dev' and 'prod' profiles at the same time.");
		}
		if (activeProfiles.contains(SPRING_PROFILE_DEVELOPMENT) &&
				activeProfiles.contains(SPRING_PROFILE_CLOUD)) {
			LOGGER.error("You have misconfigured your application! It should not" +
					"run with both the 'dev' and 'cloud' profiles at the same time.");
		}
	}

	/**
	 * Main method, used to run the application.
	 *
	 * @param args the command line arguments
	 * @throws UnknownHostException if the local host name could not be resolved into an address
	 */
	public static void main(String[] args) throws UnknownHostException {
		SpringApplication app = new SpringApplication(Bootstrap.class);
		DefaultProfileUtil.addDefaultProfile(app);
		Environment env = app.run(args).getEnvironment();
		LOGGER.info("\n----------------------------------------------------------\n\t" +
						"Application '{}' is running! Access URLs:\n\t" +
						"Local: \t\thttp://127.0.0.1:{}\n\t" +
						"External: \thttp://{}:{}\n-----------------------------------",
				env.getProperty("spring.application.name"),
				env.getProperty("server.port"),
				InetAddress.getLocalHost().getHostAddress(),
				env.getProperty("server.port"));

	}
}


