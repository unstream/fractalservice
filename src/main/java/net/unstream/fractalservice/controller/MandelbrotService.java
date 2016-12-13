package net.unstream.fractalservice.controller;

import org.apache.commons.math3.complex.Complex;

import javax.inject.Named;

import java.util.logging.Logger;

import net.unstream.fractalservice.entity.Fractal;
import net.unstream.fractalservice.entity.Quad;

@Named
public class MandelbrotService {

  private static final Logger LOG = Logger.getLogger(MandelbrotService.class.getName());

  // @Inject
  // @Named("forkjoin")
  // private final FractalAlg alg = null;

  private static int maxIterations = 100;


  public MandelbrotService() {
  }

  public Quad create(Fractal fractal) {

    MandelbrotFunction f = new MandelbrotFunction(maxIterations);

    Quad data = new Quad();
    double width = fractal.getC1().getReal() - fractal.getC0().getReal();
    double height = fractal.getC1().getImaginary() - fractal.getC0().getImaginary();
    double xstep = width / data.getWidth();
    double ystep = height / data.getWidth();
    for (int x = 0; x < data.getWidth(); x++) {
      for (int y = 0; y < data.getWidth(); y++) {
        Complex z = fractal.getC0().add(new Complex(xstep * x, ystep * y));
        double r = f.apply(z);
        data.setXY(x, y, r);
      }
    }
    return data;
  }

  // /*
  // * (non-Javadoc)
  // *
  // * @see net.unstream.fractal.MandelbrotService#computeMandelBrotPng(net.unstream.fractal.Fractal)
  // */
  //
  // public Future<byte[]> computeMandelBrotPng(final Fractal fractal,
  // final int width, final int height)
  // throws MandelbrotServiceException {
  //
  // try {
  // long now = System.currentTimeMillis();
  // LOG.info("Starting to compute image ... ");
  // BufferedImage image = createMandelBrotImage(fractal, width, height);
  // LOG.info("Completed in " + (System.currentTimeMillis() - now) + " ms.");
  //
  // final ImageFormat format = ImageFormat.IMAGE_FORMAT_PNG;
  // final Map<String, Object> optionalParams = new HashMap<String, Object>();
  // ByteArrayOutputStream os = new ByteArrayOutputStream();
  // Sanselan.writeImage(image, os, format, optionalParams);
  // return new AsyncResult<byte[]>(os.toByteArray());
  // } catch (ImageWriteException | IOException e) {
  // LOG.error(e.getMessage(), e);
  // throw new MandelbrotServiceException(e);
  // }
  // }

  // /*
  // * (non-Javadoc)
  // *
  // * @see net.unstream.fractal.MandelbrotService#computeColorGradientPng(net.unstream.fractal.Fractal)
  // */
  // public byte[] computeColorGradientPng(final Colors colors) throws MandelbrotServiceException {
  // try {
  // Color[] map = generateColorMap(colors);
  //
  // final ImageFormat format = ImageFormat.IMAGE_FORMAT_PNG;
  // final Map<String, Object> optionalParams = new HashMap<String, Object>();
  // ByteArrayOutputStream os = new ByteArrayOutputStream();
  // BufferedImage image = new BufferedImage(map.length, 1, BufferedImage.TYPE_3BYTE_BGR);
  // for (int i = 0; i < map.length; i++) {
  // image.setRGB(i, 0, map[i].getRGB());
  // }
  // Sanselan.writeImage(image, os, format, optionalParams);
  // return os.toByteArray();
  // } catch (ImageWriteException | IOException e) {
  // LOG.error(e.getMessage(), e);
  // throw new MandelbrotServiceException(e);
  // }
  // }
  //
  // private BufferedImage createMandelBrotImage(final Fractal fractal, final int width, final int height) {
  // final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
  //
  // Color[] colorMap = generateColorMap(new Colors());
  //
  // Map<Integer, double[]> lines;
  // lines = alg.compute(fractal, width);
  // for (int y = 0; y < Math.min(height, lines.size()); y++) {
  // for (int x = 0; x < width; x++) {
  // double nsmooth = lines.get(y)[x];
  // Color color = mapColor(nsmooth, colorMap, fractal.getColors());
  // image.setRGB(x, y, color.getRGB());
  // }
  // }
  // return image;
  // }


}
