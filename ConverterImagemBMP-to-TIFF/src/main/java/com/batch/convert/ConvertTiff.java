      package com.batch.convert;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import com.github.jaiimageio.plugins.tiff.TIFFImageWriteParam;

public class ConvertTiff {

	public static void main(String[] args) throws IOException {

		ConvertTiff convertTiff = new ConvertTiff();

		File inFile = new File("C:\\gavioesdafiel\\DSP-30818\\2019\\08\\06\\11\\");

		for (File arquivo : inFile.listFiles()) {

			convertTiff.convertImagem(arquivo);

			convertTiff.renameFile(arquivo.getAbsolutePath());

		}
	}

	public boolean convertImagem(File arquivoAtual) throws IOException {

		ImageOutputStream ios = null;

		ImageWriter writer = null;

		BufferedImage image = ImageIO.read(arquivoAtual);

		try {

			Iterator it = ImageIO.getImageWritersByFormatName("TIF");

			if (it.hasNext()) {

				writer = (ImageWriter) it.next();

			} else {

				return false;

			}

			// setup writer
			ios = ImageIO.createImageOutputStream(arquivoAtual);

			writer.setOutput(ios);

			TIFFImageWriteParam writeParam = new TIFFImageWriteParam(Locale.ENGLISH);

			writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);

			writeParam.setCompressionType("PackBits");

			IIOImage iioImage = new IIOImage(image, null, null);

			writer.write(null, iioImage, writeParam);

			ios.close();

		} catch (IOException e) {

			e.printStackTrace();

			return false;
		}
		return true;

	}

	public void renameFile(String pathFile) {

		File oldFile = new File(pathFile);

		File novoFile = new File(pathFile.replace(".tif", ".tif"));

		if (oldFile.exists()) {

			if (!novoFile.exists()) {

				if (oldFile.renameTo(new File(novoFile.getAbsolutePath()))) {

					System.out.println(" Arquivo remeado com sucesso de "+oldFile.getName()+" Para " + novoFile.getName());

				} else {

					System.out.println(" Nao foi possivel renomear o arquivo ");
				}

			}
		}
	}

}
