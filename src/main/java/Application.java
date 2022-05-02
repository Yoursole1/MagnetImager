import objects.Magnet;
import processors.EdgeDetector;
import processors.MagnetProcessor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;


public class Application {
    public static void main(String[] args) throws IOException {

        final File file = new File("landscape.jpg");
        final BufferedImage image = ImageIO.read(file);

        final BufferedImage edgeDetected = new EdgeDetector(image).getImage();
        File outputfile = new File("edgeDetected.jpg");
        ImageIO.write(edgeDetected, "jpg", outputfile);

        MagnetProcessor magnetProcessor = new MagnetProcessor(edgeDetected, 15, 25);
        BufferedImage magnetVisualization = magnetProcessor.visualize();
        outputfile = new File("magnetVisualization.jpg");
        ImageIO.write(magnetVisualization, "jpg", outputfile);


        for(Magnet[] ma : magnetProcessor.getMagnetStrengths()){
            for(Magnet m : ma){
                System.out.print("["+m.getStrength()+"],");
            }
            System.out.println();
        }



    }
}
