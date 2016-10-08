package com.ebuilder.microservices.contactservice.dw;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EBuilderMicroServiceConfiguration extends Configuration {

	@Valid
	@NotNull
	@JsonProperty
	private String databaseType;
	
	@Valid
	@NotNull
	@JsonProperty
	private DataSourceFactory database = new DataSourceFactory(); 
	
    public String getDatabaseType() {
        return databaseType;
    }
	
    public DataSourceFactory getDataSourceFactory() {
		return database;
	}
	
}
