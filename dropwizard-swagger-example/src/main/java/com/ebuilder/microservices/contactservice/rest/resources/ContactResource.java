package com.ebuilder.microservices.contactservice.rest.resources;

import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.skife.jdbi.v2.DBI;

import com.ebuilder.microservices.contactservice.dao.ContactDAO;
import com.ebuilder.microservices.contactservice.dto.Contact;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@Path("/contact")
@Api(value = "/contact", description = "This restful api service endpoint handles CRUD operations related to user contacts.")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContactResource {

	private final ContactDAO contactDao;
	private final Validator validator;
	
	public ContactResource(DBI jdbi, Validator validator, String databaseType) {
		contactDao = jdbi.onDemand(ContactDAO.class);
		if(databaseType.equals("h2")) {
			contactDao.createContactTable();
		}
		this.validator = validator;
	}
	
	@GET
	@Path("/hello/{name}")
	@ApiOperation(
		value = "Returns the echoed string with 'hello' phrase prepended",
		notes = "API call for returning the echoed string with 'hello' phrase prepended",
		response = String.class
	)
	public Response hello(
		@ApiParam(value = "Input string to be echoed with hello", required = true) @PathParam("name") String name){
		return Response.ok("Hello " + name).build();
	}
	
	@GET
	@Path("/getContact/{id}")
	@ApiOperation(
		value = "Get the Contact according to the given id",
		notes = "API call for returning the relevant contact given the contact id",
		response = Contact.class
	)	
	public Response getContact(
		@ApiParam(value = "Contact id to be looked for", required = true) @PathParam("id") String id){	
		try {
			Contact ct = contactDao.getContactByID(Long.valueOf(URLDecoder.decode(id)));
			return Response.ok(ct).build();
		} catch(Exception ex) {
			return Response.status(Status.BAD_REQUEST).entity(ex).build();
		}
	}
	
	@GET
	@Path("/viewAllContacts")
	@ApiOperation(
		value = "Get all available Contact according to the given id",
		notes = "API call for returning the relevant contact given the contact id",
		response = List.class
	)
	public Response viewAllContacts(){	
		try {
			List<Contact> contacts = contactDao.getAllContacts();
			return Response.ok(contacts).build();
		} catch(Exception ex) {
			return Response.status(Status.BAD_REQUEST).entity(ex).build();
		}
	}
	
	@POST
	@Path("/createContact")
	@ApiOperation(
		value = "Create a new contact based on given information",
		notes = "API call for creating a new contact based on given information",
		response = List.class
	)
	@ApiResponses(value = {        
		@ApiResponse(code = 200, message = "Successfully created the contact")
	})
	public Response createContact(
		@ApiParam(value = "Contact to be created", required = true) Contact contact) throws URISyntaxException{
		
		System.out.println("contact.getFirstName() is [" + contact.getFirstName() + "]");
		Set<ConstraintViolation<Contact>> violations = validator.validate(contact);
		if(violations.size() > 0){
			ArrayList<String> messages = new ArrayList<String>();
			for(ConstraintViolation<Contact> violation : violations){
				messages.add(violation.getPropertyPath().toString()+" : "+violation.getMessage());
			}
			return Response.status(Status.BAD_REQUEST).entity(messages).build();
		}else{
			long contactID = contactDao.createContact(contact.getFirstName() , contact.getLastName() , contact.getPhone());
			System.out.println("Contact ID is ["+ contactID + "]");	
			//return Response.created(new URI(URLEncoder.encode(Long.toString(contactID)))).entity(contactID).build();
			return Response.ok(contactID).build();
		}
	}
	
	@PUT
	@Path("/updateContact/{id}")
	@ApiOperation(
		value = "Update an existing contact based on given information",
		notes = "API call for updating an existing contact based on given information and contact id",
		response = Contact.class
	)
	public Response updateContact(
		@ApiParam(value = "Contact id to be updated", required = true) @PathParam("id") String id, 
		@ApiParam(value = "Contact to be updated", required = true) Contact contact){
		
		try {
			long urlDecaodedId = Long.valueOf(URLDecoder.decode(id));
		
			Set<ConstraintViolation<Contact>> violations = validator.validate(contact);
			if(violations.size() > 0){
				ArrayList<String> messages = new ArrayList<String>();
				for(ConstraintViolation<Contact> violation : violations){
					messages.add(violation.getPropertyPath().toString()+" : "+violation.getMessage());
				}
				return Response.status(Status.BAD_REQUEST).entity(messages).build();
			} else{
				contactDao.updateContact( urlDecaodedId , contact.getFirstName(), contact.getLastName(), contact.getPhone() );
				return Response.ok( new Contact( urlDecaodedId , contact.getFirstName(), contact.getLastName(), contact.getPhone())  ).build();
			}
		} catch(Exception ex) {
			return Response.status(Status.BAD_REQUEST).entity(ex).build();
		}
	}
	
	@DELETE
	@Path("/deleteContact/{id}")
	@ApiOperation(
		value = "Delete an existing contact based on given information",
		notes = "API call for deleting an existing contact based on contact id"
	)
	@ApiResponses(value = {        
		@ApiResponse(code = 200, message = "Successfully deleted the contact")
	})
	public Response deleteContact(@PathParam("id") String id){
		try {
			contactDao.deleteContact(Long.valueOf(URLDecoder.decode(id)));
			return Response.noContent().build();
		} catch(Exception ex) {
			return Response.status(Status.BAD_REQUEST).entity(ex).build();
		}
	}
}