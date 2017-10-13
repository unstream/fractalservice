package net.unstream.fractalservice.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import net.unstream.fractalservice.entity.Fractal;
import net.unstream.fractalservice.entity.Quad;

import org.apache.commons.math3.complex.Complex;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class MandelbrotServiceTest {

	@Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
	
	MandelbrotService service = new MandelbrotService();
	
	@Test
	public void testCreate() throws Exception {
		Fractal fractal = Fractal.builder()
				.c0(new Complex(-1.5, -1))
				.c1(new Complex(0.5, 1))
				.iterations(5000)
				.build();
		Quad data = service.create(fractal);
		writeTempImage(data, "create");
		
	}

	@Test
	public void testAuto() throws Exception {
		Fractal fractal = Fractal.builder()
				.c0(new Complex(-1.5, -1))
				.c1(new Complex(0.5, 1))
				.iterations(500000)
				.build();
		Quad data = service.autostop(fractal);
		writeTempImage(data, "auto");

	}

	private void writeTempImage(Quad data, String name) throws FileNotFoundException,
			IOException {
		File file = testFolder.newFile(name + "testimage.png");
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(new ImageService().createImage(data));
		System.out.println(file.getPath());
		fos.close();
	}

}
