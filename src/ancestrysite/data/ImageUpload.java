package ancestrysite.data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUpload {

	public void saveImage(String savePath, User userLogin, String path) throws IOException{
		
		BufferedImage image = null;
		File filePic = new File(path);
        image = ImageIO.read(filePic);
        ImageIO.write(image, "png",new File(savePath+"image\\"+userLogin.getUserId()+".png"));
	}

	public void saveGroupImage(String savePath, FamilyGroup group, String path) throws IOException{
		
		BufferedImage image = null;
		File filePic = new File(path);
        image = ImageIO.read(filePic);
        ImageIO.write(image, "png",new File(savePath+"image\\"+group.getGroupId()+".png"));
	}
}
