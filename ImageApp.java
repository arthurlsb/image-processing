
import java.awt.*;
import java.awt.image.*;
import java.net.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ImageApp   {

	// Leitura da imagem
	public static BufferedImage loadImage(String surl) {
		BufferedImage bimg = null;
		try {
			URL url = new URL(surl);
			bimg = ImageIO.read(url);
			//bimg = ImageIO.read(new File("D:/Temp/mundo.jpg"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bimg;
	}

	public void apresentaImagem(JFrame frame, BufferedImage img) {
		frame.setBounds(0, 0, img.getWidth(), img.getHeight());
		JImagePanel panel = new JImagePanel(img, 0, 0);
		frame.add(panel);
		frame.setVisible(true);
	}

	public static BufferedImage criaImagemRGB() {
		BufferedImage img = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);

		WritableRaster raster = img.getRaster();

		for(int h=0;h<img.getHeight();h++) //Percorre a horizontal
			for(int w=0;w<img.getWidth();w++) {//Percorre a vertical
				raster.setSample(w,h,0,220); // Componente Vermelho
				raster.setSample(w,h,1,219); // Componente Verde
				raster.setSample(w,h,2,97);  // Componente Azul
			}
		return img;
	}

	public static BufferedImage criaImagemCinza(BufferedImage imgCubo) {
		int width = imgCubo.getWidth();
		int height = imgCubo.getHeight();
		BufferedImage imgCinza = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster raster = imgCinza.getRaster();

		for(int h=0;h < height; h++) { //Percorre a horizontal
			for (int w = 0; w < width; w++) {//Percorre a vertical
				int rgb = imgCubo.getRGB(w, h);
				int r = (int)((rgb&0x00FF0000)>>>16);
				int g = (int)((rgb&0x0000FF00)>>>8);
				int b = (int)(rgb&0x000000FF);
				raster.setSample(w, h, 0, r * 0.3 + g * 0.59 + b * 0.11);
			}
		}
		return imgCinza;
	}

	public static BufferedImage criaImagemBinaria(BufferedImage imgCinza) {
		int height = imgCinza.getHeight();
		int width = imgCinza.getWidth();

		BufferedImage imgBinaria = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);

		WritableRaster rasterCinza = imgCinza.getRaster();
		WritableRaster rasterBinaria = imgBinaria.getRaster();

		for(int h=0;h < height; h++) {
			for (int w = 0; w < width; w++) {
				int y = rasterCinza.getSample(w, h, 0);
				if (y >= 127)
					rasterBinaria.setSample(w, h, 0, 1); //BRANCO
				else
					rasterBinaria.setSample(w, h, 0, 0); //PRETO
			}
		}
		return imgBinaria;
	}
	// Imprime valores dos pixeis de imagem RGB
	public static void  imprimePixeis(BufferedImage bufferedImage) {
		for(int h=0;h<bufferedImage.getHeight();h++) //Percorre a horizontal
			for(int w=0;w<bufferedImage.getWidth();w++) {//Percorre a vertical
				int rgb = bufferedImage.getRGB(w,h);
				int r = (int)((rgb&0x00FF0000)>>>16); // componente vermelho
				int g = (int)((rgb&0x0000FF00)>>>8); // componente verde
				int b = (int)(rgb&0x000000FF); //componente azul
				System.out.print("at ("+w+","+h+"): ");
				System.out.println(r+","+g+","+b);
			}
	}

	public static BufferedImage reduzirImagem(BufferedImage imgCubo) {
		int width = imgCubo.getWidth();
		int height = imgCubo.getHeight();
		BufferedImage imgReduzida = new BufferedImage(width / 2, height / 2, BufferedImage.TYPE_INT_RGB);

		WritableRaster rasterCubo = imgCubo.getRaster();
		WritableRaster rasterImgReduzida = imgReduzida.getRaster();

		int w1, w, h, h1 = 0;
		for (h = 0; h < height; h = h + 2){
			w1 = 0;
			for (w = 0; w < width; w = w + 2){
				rasterImgReduzida.setSample(w1, h1, 0 , rasterCubo.getSample(w, h,0));
				rasterImgReduzida.setSample(w1, h1, 1 , rasterCubo.getSample(w, h,1));
				rasterImgReduzida.setSample(w1, h1, 2 , rasterCubo.getSample(w, h,2));
				w1++;
			}
			h1++;
		}
		return imgReduzida;
	}

	public static BufferedImage splitVermelho(BufferedImage imgCubo){
		int height = imgCubo.getHeight();
		int width = imgCubo.getWidth();

		BufferedImage imgVermelha = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

		WritableRaster raster = imgCubo.getRaster();
		WritableRaster rasterVermelha = imgVermelha.getRaster();

		for(int h=0;h < height; h++) { //Percorre a horizontal
			for (int w = 0; w < width; w++) {//Percorre a vertical
				int rgb = imgCubo.getRGB(w, h);
				int r = (int)((rgb&0x00FF0000)>>>16);
				int g = (int)((rgb&0x0000FF00)>>>8);
				int b = (int)(rgb&0x000000FF);
				rasterVermelha.setSample(w, h, 0, r);
			}
		}
		return imgVermelha;
	}

	public static BufferedImage splitVerde(BufferedImage imgCubo){
		int height = imgCubo.getHeight();
		int width = imgCubo.getWidth();

		BufferedImage imgVerde = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

		WritableRaster raster = imgCubo.getRaster();
		WritableRaster rasterVerde = imgVerde.getRaster();

		for(int h=0;h < height; h++) { //Percorre a horizontal
			for (int w = 0; w < width; w++) {//Percorre a vertical
				int rgb = imgCubo.getRGB(w, h);
				int r = (int)((rgb&0x00FF0000)>>>16);
				int g = (int)((rgb&0x0000FF00)>>>8);
				int b = (int)(rgb&0x000000FF);
				rasterVerde.setSample(w, h, 0, g);
			}
		}
		return imgVerde;
	}

	public static BufferedImage splitAzul(BufferedImage imgCubo){
		int height = imgCubo.getHeight();
		int width = imgCubo.getWidth();

		BufferedImage imgAzul = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

		WritableRaster raster = imgCubo.getRaster();
		WritableRaster rasterAzul = imgAzul.getRaster();

		for(int h=0;h < height; h++) { //Percorre a horizontal
			for (int w = 0; w < width; w++) {//Percorre a vertical
				int rgb = imgCubo.getRGB(w, h);
				int r = (int)((rgb&0x00FF0000)>>>16);
				int g = (int)((rgb&0x0000FF00)>>>8);
				int b = (int)(rgb&0x000000FF);
				rasterAzul.setSample(w, h, 0, b);
			}
		}
		return imgAzul;
	}

	public static void main(String[] args) {
		ImageApp ia = new ImageApp();
		BufferedImage imgJPEG = loadImage("http://www.inf.ufsc.br/~willrich/INE5431/RGB.jpg");
		BufferedImage imgRGB = criaImagemRGB();

		//Questão 1
		BufferedImage imgReduzida = reduzirImagem(imgJPEG);

		//Questão 2
		BufferedImage imgCinza = criaImagemCinza(imgJPEG);

		//Questão 3
		BufferedImage imgBinaria = criaImagemBinaria(imgCinza);

		//Questão 4
		BufferedImage imgVermelha = splitVermelho(imgJPEG);
		BufferedImage imgVerde = splitVerde(imgJPEG);
		BufferedImage imgAzul = splitAzul(imgJPEG);

		ia.apresentaImagem(new JFrame("imgReduzida"), imgReduzida);
		ia.apresentaImagem(new JFrame("imgCinza"), imgCinza);
		ia.apresentaImagem(new JFrame("imgBinaria"), imgBinaria);
		ia.apresentaImagem(new JFrame("imgVermelho"), imgVermelha);
		ia.apresentaImagem(new JFrame("imgVerde"), imgVerde);
		ia.apresentaImagem(new JFrame("imgAzul"), imgAzul);

		imprimePixeis(imgJPEG);
	}
}