package com._33gram.gradlemvc.config;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;

import com._33gram.gradlemvc.common.constant.Log;


public class DefaultProfiles {
	
    //private static final Logger logger = LoggerFactory.getLogger(DefaultProfiles.class);
    private static final String DEFAULT_PROFILE_PROPERTIES_PATH = "/WEB-INF/classes/properties/config/profile.properties";
    private Properties properties;

    public enum Profile {

    	APP_TYPE("app.type", "user"), DB_FRAMEWORK("db.framework", "hibernate");

        private String key;
        private String defaultValue;

        Profile(String key, String defaultValue) {
            this.key = key;
            this.defaultValue = defaultValue;
        }

        public String getKey() {
            return key;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    public DefaultProfiles(ServletContext servletContext) {
        properties = new Properties();
        try {
            String defaultPropertyPath = servletContext.getRealPath(DEFAULT_PROFILE_PROPERTIES_PATH);
            FileInputStream fis = new FileInputStream(defaultPropertyPath);
            properties.load(new BufferedInputStream(fis));
            fis.close();
        } catch (IOException e) {
        	//logger.debug("failed to load init.properties.. Caused by : {}", e.getMessage());
            Log.DEV_LOGGER.debug("failed to load init.properties.. Caused by : {}", e.getMessage());
        }
    }

    public String get(Profile profile) {
        return properties.getProperty(profile.getKey(), profile.getDefaultValue());
    }

}
