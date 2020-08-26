package com.prog.distribuida;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.created;

@Path("/customers")
public class CustomerResource {

  @Inject
  CustomerRepository customerRepository;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Customer> findAll() {
    return customerRepository.listAll();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("{id}")
  public Customer get(@PathParam("id") Long id) {
    return customerRepository.findById(id);
  }

  @Transactional
  @POST
  @Consumes("application/json")
  @Produces("application/json")
  public Response add(Customer entity) {
    customerRepository.persist(entity);
    return Response.status(Response.Status.CREATED).build();
  }

  @Transactional
  @PUT
  @Consumes("application/json")
  @Produces("application/json")
  @Path("{id}")
  public Response update(@PathParam("id") Long id, Customer customer) {
    Customer customerFound = customerRepository.findById(id);
    customerFound.setName(customer.getName());
    customerFound.setSurname(customer.getSurname());
    customerRepository.persist(customerFound);
    return Response.status(Response.Status.OK).build();
  }

  @Transactional
  @DELETE
  @Path("{id}")
  public void delete(@PathParam("id") Long id) {
    customerRepository.deleteById(id);
  }
}
