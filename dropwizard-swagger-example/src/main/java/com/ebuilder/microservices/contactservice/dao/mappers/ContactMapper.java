package com.ebuilder.microservices.contactservice.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.ebuilder.microservices.contactservice.dto.Contact;

public class ContactMapper implements ResultSetMapper<Contact>{

	@Override
	public Contact map(int id, ResultSet res, StatementContext ctx) throws SQLException {
		return new Contact(res.getLong("id"),res.getString("firstName"), res.getString("lastName"), res.getString("phone"));
	}

}
