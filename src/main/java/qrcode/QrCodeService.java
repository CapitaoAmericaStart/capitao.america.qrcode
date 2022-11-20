package qrcode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.Writer;

public class QrCodeService {
	
	private static final int WIDTH_IMAGE = 200;
	private static final int HEIGHT_IMAGE = 200;
	private static final String IMAGE_TYPE = "png";
	
	public byte[] image(String text) throws WriterException, IOException {
		Writer writer = new QRCodeWriter();
		
		BitMatrix matrix = writer.encode(text, com.google.zxing.BarcodeFormat.QR_CODE, WIDTH_IMAGE, HEIGHT_IMAGE);

		int width = matrix.getWidth(); 
		int height = matrix.getHeight(); 

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		image.createGraphics();
		
		Graphics2D graphics = (Graphics2D) image.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, width, height);
		graphics.setColor(Color.BLACK);

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (matrix.get(i, j)) {
					graphics.fillRect(i, j, 1, 1);
				}
			}
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, IMAGE_TYPE, baos);
		baos.flush();
		baos.close();
		byte[] imageInBytes = baos.toByteArray();

		return imageInBytes;
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException, WriterException {
		try(OutputStream os = new FileOutputStream("qrcode/imagem.png")){
			String texto = "https://www.youtube.com/watch?v=F0F0B33Un9M&list=PL62G310vn6nFIsOCC0H-C2infYgwm8SWW&index=111";
			
			byte[] bytesDaImagem = new QrCodeService().image(texto) ;
			
			os.write(bytesDaImagem);
		}
	}
}
