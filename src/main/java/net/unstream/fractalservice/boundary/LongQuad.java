/**
 *
 *
 */
package net.unstream.fractalservice.boundary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class LongQuad.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LongQuad {
  private String name;
  final static private int width = 500;

  public int getWidth() {
    return width;
  }

  final private long[][] data = new long[width][width];

  public long[][] getData() {
    return data;
  }

  public void setXY(int x, int y, long d) {
    data[x][y] = d;
  }
}
