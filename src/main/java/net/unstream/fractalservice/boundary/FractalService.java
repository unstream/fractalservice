package net.unstream.fractalservice.boundary;

import org.apache.commons.math3.complex.Complex;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import net.unstream.fractalservice.controller.ImageService;
import net.unstream.fractalservice.controller.MandelbrotService;
import net.unstream.fractalservice.entity.Fractal;
import net.unstream.fractalservice.entity.Quad;


@RequestScoped
@Path("/fractal")
public class FractalService {

  @Inject
  private MandelbrotService fractalService;

  @GET
  @Path("/mandelbrot")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Produces("image/png")
  public Response getMandelbrot(
      @DefaultValue("-1.5") @QueryParam("c0") double c0,
      @DefaultValue("-1") @QueryParam("c0i") double c0i,
      @DefaultValue("0.5") @QueryParam("c1") double c1,
      @DefaultValue("1") @QueryParam("c1i") double c1i) {

    Fractal fractal = new Fractal();
    fractal.setC0(new Complex(c0, c0i));
    fractal.setC1(new Complex(c1, c1i));

    Quad data = fractalService.create(fractal);
    ImageService imageService = new ImageService();
    byte[] image = imageService.createImage(data);
    ResponseBuilder response = Response.ok(image);
    return response.build();

  }
}
