package com.ebuilder.microservices.contactservice.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import com.ebuilder.microservices.contactservice.dao.mappers.ContactMapper;
import com.ebuilder.microservices.contactservice.dto.Contact;

public interface ContactDAO {
	
	//only used for creating the table on in-memory database
	@SqlUpdate("create table contact (id bigint auto_increment, firstName varchar(100), lastName varchar(100), phone varchar(10))")
	void createContactTable();

	//select name from something where id = :id
	@Mapper(ContactMapper.class)
	@SqlQuery("select * from contact where id = :id")
	Contact getContactByID(@Bind("id") long id);
	
	@Mapper(ContactMapper.class)
	@SqlQuery("select * from contact")
	List<Contact> getAllContacts();
	
	@SqlUpdate("insert into contact (firstName, lastName, phone) values (:firstName, :lastName, :phone)")
	@GetGeneratedKeys
	int createContact(@Bind("firstName") String firstName, @Bind("lastName") String lastName, @Bind("phone") String phone);
	
	@SqlUpdate("update contact set firstName = :firstName, lastName = :lastName, phone = :phone where id = :id")
	void updateContact(@Bind("id") long id, @Bind("firstName") String firstName, @Bind("lastName") String lastName, @Bind("phone") String phone);
	
	@SqlUpdate("delete from contact where id = :id")
	void deleteContact(@Bind("id") long id);
}
