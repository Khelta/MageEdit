package edit;

import java.awt.image.BufferedImage;
import java.util.Scanner;

public class CLI {
	public static void main(String[] args) {	
		System.out.print("Enter the absolute filepath: ");
		Scanner in = new Scanner(System.in);
		String s = in.nextLine();
		
		BufferedImage img = Edit.readImg(s);
		if (img != null) {
		Edit.duplicate_background_edit(img, 2500, 2500);
		}
		
		in.close();
	}
}
