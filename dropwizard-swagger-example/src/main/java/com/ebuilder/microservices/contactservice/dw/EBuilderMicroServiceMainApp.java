package com.ebuilder.microservices.contactservice.dw;

import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerDropwizard;

import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ebuilder.microservices.contactservice.rest.resources.ContactResource;

/**
 * 
 * Main Launcher class for this dropwizard-based micro-service
 *
 *
 */
public class EBuilderMicroServiceMainApp extends Application<EBuilderMicroServiceConfiguration>{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EBuilderMicroServiceMainApp.class);
	private final SwaggerDropwizard<EBuilderMicroServiceConfiguration> swaggerDropwizard = new SwaggerDropwizard<EBuilderMicroServiceConfiguration>();
	
	@Override
    public void initialize(Bootstrap<EBuilderMicroServiceConfiguration> b){
		swaggerDropwizard.onInitialize(b);
	}

	@Override
	public void run(EBuilderMicroServiceConfiguration conf, Environment env) throws Exception {
		LOGGER.info("[DSH] Registering Contact Resourses");
		
		String databaseType = conf.getDatabaseType();
		
		LOGGER.info("*********************** Database Type is -> " + databaseType);
		
		// This will initialize JDBI with given database settings in config.yml
		final DBIFactory fac = new DBIFactory();
		final DBI jdbi = fac.build(env, conf.getDataSourceFactory(), databaseType);
		
		// Register all your restful endpoints here
		env.jersey().register(new ContactResource(jdbi, env.getValidator(), databaseType));
		
		// This will autogenerate documentation via swagger
		swaggerDropwizard.onRun(conf, env, "localhost");
				
		LOGGER.info("***************************************************");
	}

	public static void main( String[] args ) throws Exception{
        new EBuilderMicroServiceMainApp().run(args);
    }
}
