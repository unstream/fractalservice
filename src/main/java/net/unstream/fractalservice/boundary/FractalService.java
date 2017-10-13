/**
 *
 * Project:        Toll Collect
 *
 * adesso AG
 * Rotherstr. 19
 * 10245 Berlin
 * Germany
 * Tel. +49 (0)30 7262033-0
 * Mail: office@adesso.de
 * Web: http://www.adesso.de
 */
package net.unstream.fractalservice.boundary;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;

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

import java.io.ByteArrayOutputStream;
import java.util.zip.DeflaterOutputStream;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import net.unstream.fractalservice.controller.ImageService;
import net.unstream.fractalservice.controller.MandelbrotService;
import net.unstream.fractalservice.entity.Fractal;
import net.unstream.fractalservice.entity.Quad;


/**
 * The Class FractalService.
 */
@RequestScoped
@Path("/fractal")
@Api(value = "Fractal Service API", authorizations = {
  @Authorization(value = "sampleoauth", scopes = {})
})
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
      @DefaultValue("1") @QueryParam("c1i") double c1i,
      @DefaultValue("100") @QueryParam("iterations") int iterations) {

    Fractal fractal = Fractal.builder()
        .c0(new Complex(c0, c0i))
        .c1(new Complex(c1, c1i))
        .iterations(iterations)
        .build();

    Quad data = fractalService.create(fractal);
    ImageService imageService = new ImageService();
    byte[] image = imageService.createImage(data);
    ResponseBuilder response = Response.ok(image);
    return response.build();
  }

  @GET
  @Path("/quad")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getQuad(
      @DefaultValue("-1.5") @QueryParam("c0") double c0,
      @DefaultValue("-1") @QueryParam("c0i") double c0i,
      @DefaultValue("0.5") @QueryParam("c1") double c1,
      @DefaultValue("1") @QueryParam("c1i") double c1i,
      @DefaultValue("100") @QueryParam("iterations") int iterations) {

    Fractal fractal = Fractal.builder()
        .c0(new Complex(c0, c0i))
        .c1(new Complex(c1, c1i))
        .iterations(iterations)
        .build();

    Quad data = fractalService.create(fractal);
    return Response.ok().entity(data).build();
  }

  /**
   * Gets the long smile quad.
   *
   * @param c0 the c0
   * @param c0i the c0i
   * @param c1 the c1
   * @param c1i the c1i
   * @param iterations the iterations
   * @return the long smile quad
   */
  @GET
  @Path("/longquad")
  @ApiOperation(value = "Compute parts of the mandelbrot set.",
    notes = "bla bla",
    response = LongQuad.class)
  // @Produces(SmileMediaTypes.APPLICATION_JACKSON_SMILE)
  @Produces(MediaType.APPLICATION_JSON)
  public Response getLongSmileQuad(
      @DefaultValue("-1.5") @ApiParam(value = "Real part of the complex coordinate c0.", required = true) @QueryParam("c0") double c0,
      @DefaultValue("-1") @QueryParam("c0i") double c0i,
      @DefaultValue("0.5") @QueryParam("c1") double c1,
      @DefaultValue("1") @QueryParam("c1i") double c1i,
      @DefaultValue("-1") @QueryParam("iterations") int iterations) {

    Fractal fractal = Fractal.builder()
        .c0(new Complex(c0, c0i))
        .c1(new Complex(c1, c1i))
        .iterations(iterations)
        .build();
    Quad data;
    if (iterations != -1) {
    	data = fractalService.create(fractal);
    } else {
    	data = fractalService.autostop(fractal);
    }
    LongQuad longQuad = LongQuad.builder().name("Ingo").build();
    for (int x = 0; x < data.getWidth(); x++) {
      for (int y = 0; y < data.getWidth(); y++) {
        longQuad.setXY(x, y, Math.round(data.getData()[x][y]));
      }
    }
    Response response = Response.ok().entity(longQuad).build();
    return response;
  }

  @GET
  @Path("/kryoquad")
  @Produces("application/octet-stream")
  public Response getKryoQuad(
      @DefaultValue("-1.5") @QueryParam("c0") double c0,
      @DefaultValue("-1") @QueryParam("c0i") double c0i,
      @DefaultValue("0.5") @QueryParam("c1") double c1,
      @DefaultValue("1") @QueryParam("c1i") double c1i,
      @DefaultValue("100") @QueryParam("iterations") int iterations) {

    Fractal fractal = Fractal.builder()
        .c0(new Complex(c0, c0i))
        .c1(new Complex(c1, c1i))
        .iterations(iterations)
        .build();

    Quad data = fractalService.create(fractal);
    // LongQuad longQuad = new LongQuad();
    // for (int x = 0; x < data.getWidth(); x++) {
    // for (int y = 0; y < data.getWidth(); y++) {
    // longQuad.setXY(x, y, Math.round(data.getData()[x][y]));
    // }
    // }

    Kryo kryo = new Kryo();
    // ...
    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    Output output = new Output(new DeflaterOutputStream(bout, true));
    kryo.writeObject(output, data);
    output.close();

    // BinaryQuad bQuad = new BinaryQuad();
    // bQuad.setData(Base64.getEncoder().encodeToString(bout.toByteArray()));
    return Response.ok().entity(bout.toByteArray()).build();
  }


}
