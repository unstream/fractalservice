package net.unstream.fractalservice.controller;

import org.apache.sanselan.ImageFormat;
import org.apache.sanselan.ImageWriteException;
import org.apache.sanselan.Sanselan;

import javax.inject.Named;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.unstream.fractalservice.entity.Quad;

@Named
public class ImageService {

  private static final int COLOR_MAP_SIZE = 10000;

  public byte[] createImage(Quad data) {
    final BufferedImage image = new BufferedImage(data.getWidth(), data.getWidth(), BufferedImage.TYPE_3BYTE_BGR);

    Color[] colorMap = generateColorMap(new Colors());

    for (int y = 0; y < data.getWidth(); y++) {
      for (int x = 0; x < data.getWidth(); x++) {
        Color color = mapColor(data.getData()[x][y], colorMap, new Colors());
        image.setRGB(x, y, color.getRGB());
      }
    }

    try {

      final ImageFormat format = ImageFormat.IMAGE_FORMAT_PNG;

      final Map<String, Object> optionalParams = new HashMap<String, Object>();
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      Sanselan.writeImage(image, os, format, optionalParams);
      return os.toByteArray();
    } catch (ImageWriteException | IOException e) {
      // LOG.error(e.getMessage(), e);
      throw new MandelbrotServiceException(e);
    }

  }

  /**
   * Generate a color gradient. It starts with the first color and ends with the last color.
   * @param colors Defines the colors for the iterations.
   * @return Color map
   */
  private Color[] generateColorMap(Colors colors) {
    final Color[] colorMap = new Color[COLOR_MAP_SIZE];
    final int iMax = colors.getIterations()[colors.getIterations().length - 1];
    int idx = 0;
    int it0 = colors.getIterations()[0] - 1;
    int it1 = colors.getIterations()[0];
    Color c0 = Color.WHITE;
    Color c1 = Color.BLACK;
    for (int i = 0; i < COLOR_MAP_SIZE; i++) {
      // compute current iteration
      float currentIteration = 0.0f + colors.getIterations()[0] + (iMax - colors.getIterations()[0]) * i / (float) COLOR_MAP_SIZE;
      if (currentIteration >= it1) {
        idx += 1;
        it0 = colors.getIterations()[idx - 1];
        it1 = colors.getIterations()[idx];
        c0 = Color.decode(colors.getColors()[idx - 1]);
        c1 = Color.decode(colors.getColors()[idx]);
      }
      /*
       * Interpolate between the colors c0 and c1
       */
      float r = 1.0f * (currentIteration - it0) / (it1 - it0);

      int red = (int) ((1f - r) * c0.getRed() + r * c1.getRed());
      int green = (int) ((1f - r) * c0.getGreen() + r * c1.getGreen());
      int blue = (int) ((1f - r) * c0.getBlue() + r * c1.getBlue());
      Color color = new Color(red, green, blue);
      colorMap[i] = color;
    }
    return colorMap;
  }

  private Color mapColor(final double nsmooth, final Color[] colorMap, final Colors colors) {
    int[] cis = colors.getIterations();
    int i = (int) Math.round((nsmooth - cis[0]) * colorMap.length / (cis[cis.length - 1] - cis[0]));

    if (i < 0) {
      i = 0;
    } else if (i >= colorMap.length) {
      i = colorMap.length - 1;
    }
    return colorMap[i];
  }

}
