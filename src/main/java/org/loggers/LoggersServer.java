package org.loggers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.loggers.beans.LoggersConfiguration;
import org.loggers.resources.GithubResource;
import org.wso2.msf4j.MicroservicesRunner;

import java.io.File;
import java.io.IOException;

public enum  LoggersServer {

    INSTANCE;

    public static LoggersConfiguration configuration = null;
    private static final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    public static final Logger logger = LogManager.getLogger(LoggersServer.class);

    public LoggersServer configure(String configFile) {
        try {
            configuration = mapper.readValue(new File(configFile), LoggersConfiguration.class);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return this;
    }

    public void run() {
        logger.info("Starting LoggersServer..");
        new MicroservicesRunner()
                .deploy(new GithubResource())
                .start();
        logger.info("Started LoggersServer..");
    }
}
