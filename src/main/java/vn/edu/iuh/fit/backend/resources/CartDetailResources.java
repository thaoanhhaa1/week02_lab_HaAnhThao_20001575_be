package vn.edu.iuh.fit.backend.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import vn.edu.iuh.fit.backend.models.CartDetail;
import vn.edu.iuh.fit.backend.services.CartDetailServices;

import java.util.List;
import java.util.Optional;

@Path("/cart-details")
public class CartDetailResources {
    private final CartDetailServices cartDetailServices;

    public CartDetailResources() {
        cartDetailServices = new CartDetailServices();
    }

    @GET
    @Produces("text/plain")
    @Path("/{cus_id}/count")
    public Response countByCustomer(@PathParam("cus_id") long customerId) {
        return Response.ok(cartDetailServices.countByCustomer(customerId)).build();
    }

    @GET
    @Produces("application/json")
    @Path("/{cus_id}")
    public Response getCartDetailsByCustomerId(@PathParam("cus_id") long customerId) {
        List<CartDetail> cartDetails = cartDetailServices.getCartDetailsByCustomerId(customerId);

        return Response.ok(cartDetails).build();
    }

    @GET
    @Produces("application/json")
    @Consumes("application/json")
    public Response getByProductIds(@QueryParam("product-ids") List<Long> productIds, @QueryParam("cus_id") long customerId) {
        List<CartDetail> cartDetails = cartDetailServices.getByProductIds(productIds, customerId);

        System.out.println(cartDetails);

        return Response.ok().build();
    }

    @POST
    @Produces("application/json")
    public Response add(CartDetail cartDetail) {
        boolean added = cartDetailServices.add(cartDetail);

        if (added)
            return Response.ok().build();

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Consumes("application/json")
    public Response updateQuantity(CartDetail cartDetail) {
        Optional<Boolean> optional = cartDetailServices.updateQuantity(cartDetail);

        if (optional.isEmpty())
            return Response.status(Response.Status.NOT_FOUND).build();

        if (optional.get())
            return Response.ok().build();

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Consumes("application/json")
    public Response delete(CartDetail cartDetail) {
        Optional<Boolean> optional = cartDetailServices.delete(cartDetail.getProduct().getProduct_id(), cartDetail.getCart().getCustomer().getId());

        if (optional.isEmpty())
            return Response.status(Response.Status.NOT_FOUND).build();

        if (optional.get())
            return Response.ok().build();

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Consumes("application/json")
    @Path("/products")
    public Response deleteByProductIds(@QueryParam("product-ids") List<Long> productIds, @QueryParam("cus-id") long customerId) {
        boolean deleted = cartDetailServices.deleteByProductIds(productIds, customerId);

        if (deleted)
            return Response.ok().build();

        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
