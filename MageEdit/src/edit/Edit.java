package edit;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Edit {
	private static final double height_to_width_ratio = 1.2;
	
	// parameters to position the foreground
	private static final double foreground_width_percent  = 0.75;

	// Crop parameters to get the image of a polaroid without the border
	private static final double crop_width_percent  = 0.075;
	private static final double crop_top_percent    = 0.075;
	private static final double crop_bottom_percent = 0.22;
	
	public static BufferedImage readImg(String filepath) {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(filepath));
		} catch (IOException e) {
			System.out.println("Null!");
		}
		return img;
	}
	
	private static BufferedImage resize(BufferedImage img, int newWidth, int newHeight){
		BufferedImage resizedImg = new BufferedImage(
		        newWidth,
		        newHeight,
		        img.getType()
		);
		
		Graphics2D g2d = resizedImg.createGraphics();
		
		g2d.drawImage(
		        img, 
		        0, 
		        0, 
		        newWidth, 
		        newHeight, 
		        null
		);
		
		g2d.dispose();
		
		String formatName = "jpg";
		try {
		ImageIO.write(
		        resizedImg, 
		        formatName, 
		        new File("test.jpg")
		);
		}
		catch(IOException e){
			
		}
		
		return resizedImg;
	}
	
	private static int[] crop_param(BufferedImage img) {
		// Given an image calculates the parameters to cropout the white borders of a polaroid
		int width = img.getWidth();
		int height = img.getHeight();
		
		int x = (int)(width * crop_width_percent);
		int y = (int)(height * crop_top_percent);
		int newWidth = width - 2 * x;
		int newHeight = (int)(height - y - height * crop_bottom_percent);
		
		int[] result = {x, y, newWidth, newHeight};
		return result;
	}
	
	private static int[] calculate_foreground_measures(int resultWidth, int resultHeight) {
		
		int newWidth = (int)(resultWidth * foreground_width_percent);
		int newHeight = (int)(resultHeight * foreground_width_percent * height_to_width_ratio);
		int x = (resultWidth - newWidth) / 2;
		int y = (resultHeight - newHeight) / 2;
		
		int[] result = {x, y, newWidth, newHeight};
		return result;
	}
	
	public static BufferedImage duplicate_background_edit(BufferedImage img, int resultWidth, int resultHeight) {
		BufferedImage resultImg = new BufferedImage(
				resultWidth,
				resultHeight,
		        img.getType()
		);
		
		Graphics2D g2d = resultImg.createGraphics();
		
		
		int[] cropParam = crop_param(img);
		int x = cropParam[0];
		int y = cropParam[1];
		int w = cropParam[2];
		int h = cropParam[3];
		
		BufferedImage background = img.getSubimage(x, y, w, h);
		g2d.drawImage(background, 0, 0, resultWidth, resultHeight, null);
		
		int[] foregroundParam = calculate_foreground_measures(resultWidth, resultHeight);
		x = foregroundParam[0];
		y = foregroundParam[1];
		w = foregroundParam[2];
		h = foregroundParam[3];
		
		g2d.drawImage(img, x, y, w, h, null);
		
		g2d.dispose();
		
		String formatName = "jpg";
		try {
		ImageIO.write(
		        resultImg, 
		        formatName, 
		        new File("test.jpg")
		);
		}
		catch(IOException e){
			
		}
		
		return resultImg;
	}
}
