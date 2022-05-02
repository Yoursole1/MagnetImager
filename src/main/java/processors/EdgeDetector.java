package processors;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EdgeDetector {
    private final BufferedImage image;

    public EdgeDetector(BufferedImage image){
        this.image = image;
        detect();
    }

    public BufferedImage getImage(){
        return this.image;
    }

    private void detect(){
        for (int x = 0; x < this.image.getWidth(); x++) {
            for (int y = 0; y < this.image.getHeight(); y++) {
                final int clr = this.image.getRGB(x, y);
                int red = (clr & 0x00ff0000) >> 16;
                int green = (clr & 0x0000ff00) >> 8;
                int blue = clr & 0x000000ff;

                final int av = (red+green+blue)/3;
                if(av > 255/2f){
                   red = 255;
                   green = 255;
                   blue = 255;
                }else{
                    red = 0;
                    green = 0;
                    blue = 0;
                }

                this.image.setRGB(x, y, new Color(red,green,blue).getRGB());
            }
        }
    }
}
