package processors;

import objects.Magnet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class MagnetProcessor {
    int[][] values;
    BufferedImage image;
    int xDIM; //magnets on the X axis
    int yDIM; //magnets on the Y axis

    Magnet[][] magnetStrengths;

    public MagnetProcessor(BufferedImage image, int xDIM, int yDIM) {
        this.image = image;
        this.xDIM = xDIM;
        this.yDIM = yDIM;
        this.calculate();
    }

    private void calculate() {
        values = new int[this.image.getWidth()][this.image.getHeight()];
        this.magnetStrengths = new Magnet[this.xDIM][this.yDIM];

        final double baseX = (double)(this.image.getWidth())/(this.xDIM+1);
        final double baseY = (double)(this.image.getHeight())/(this.yDIM+1);

        for (int i = 0; i < this.xDIM; i++) {
            for (int j = 0; j < this.yDIM; j++) {
                //foreach magnet

                this.magnetStrengths[i][j] = new Magnet(1, (int)(baseX + (i*(this.image.getWidth()/this.xDIM))-baseX/2), (int)(baseY + (j*(this.image.getHeight()/this.yDIM))-baseY/2));

            }
        }

        //change magnets size so that it gets closer to the picture
        this.refreshMagnets();

        double bestScore = this.score();

        for (int i = 0; i < 5; i++) {

            for(Magnet[] ma : this.magnetStrengths){
                for (Magnet m : ma){

                    if(!isCorrect(m.getX(), m.getY(), true)){
                        double currBest = m.getStrength();
                        for (int j = 0; j < 20; j++) {
                            m.setStrength(j);
                            this.refreshMagnets();
                            double s = this.score();
                            if(s > bestScore){
                                currBest = j;
                                bestScore = s;
                            }
                        }
                        m.setStrength(currBest);
                    }else{
                        m.setStrength(0);
                    }

                }
            }

        }

        for (int i = 0; i < 5; i++) {

            for(Magnet[] ma : this.magnetStrengths){
                for (Magnet m : ma){

                    if(!isCorrect(m.getX(), m.getY(), true)){
                        double currBest = m.getStrength();
                        for (double j = currBest-1; j < currBest+1; j+=0.1) {
                            m.setStrength(j);
                            this.refreshMagnets();
                            double s = this.score();
                            if(s > bestScore){
                                currBest = j;
                                bestScore = s;
                            }
                        }
                        m.setStrength(currBest);


                    }else{
                        m.setStrength(0);
                    }

                }
            }

        }

        for (int i = 0; i < 5; i++) {

            for(Magnet[] ma : this.magnetStrengths){
                for (Magnet m : ma){

                    if(!isCorrect(m.getX(), m.getY(), true)){
                        double currBest = m.getStrength();
                        for (double j = currBest-0.1; j < currBest+0.1; j+=0.01) {
                            m.setStrength(j);
                            this.refreshMagnets();
                            double s = this.score();
                            if(s > bestScore){
                                currBest = j;
                                bestScore = s;
                            }
                        }
                        m.setStrength(currBest);


                    }else{
                        m.setStrength(0);
                    }

                }
            }

        }



    }

    private void refreshMagnets(){
        for (int i = 0; i < this.values.length; i++) {
            for (int j = 0; j < this.values[0].length; j++) {


                double dist = 0;
                for(Magnet[] ma : this.magnetStrengths){
                    for(Magnet m : ma){
                        double d = distance(i, j, (int) (m.getX()+.5*m.getStrength()), (int) (m.getY()+.5*m.getStrength()));
                        dist+=1000*m.getStrength()/d;
                    }
                }

                int d = (int)dist;

                int cc = sigmoid(d) < .7f ? 1 : 0;
                this.values[i][j] = cc;
            }
        }
    }

    private double score(){
        int count = 0;
        for (int i = 0; i < this.image.getWidth(); i++) {
            for (int j = 0; j < this.image.getHeight(); j++) {
                if(isCorrect(i,j,this.values[i][j]==1)){
                    count++;
                }
            }
        }
        return (double) count / (this.image.getWidth() * this.image.getHeight());
    }

    private boolean isCorrect(int x, int y, boolean one){
        int clr = this.image.getRGB(x, y);
        int red = (clr & 0x00ff0000) >> 16;
        int green = (clr & 0x0000ff00) >> 8;
        int blue = clr & 0x000000ff;

        return (red == 255 && green == 255 && blue == 255) == one;
    }

    private float sigmoid(float x){
        x/=1000;
        return (float) (1/(1+(Math.pow(Math.E,-x))));
    }
    private double distance(int x, int y, int x2, int y2){
        return Math.sqrt(Math.pow(y2-y,2)+Math.pow(x2-x,2));
    }


    public Magnet[][] getMagnetStrengths(){
        return this.magnetStrengths;
    }

    public BufferedImage visualize(){
        BufferedImage b = copyImage(this.image);
        for (int i = 0; i < this.values.length; i++) {
            for (int j = 0; j < this.values[0].length; j++) {
                int c = this.values[i][j] * 255;
                b.setRGB(i, j, new Color(c,c,c).getRGB());
            }
        }

        return b;
    }

    private BufferedImage copyImage(BufferedImage b) {
        ColorModel cm = b.getColorModel();
        return new BufferedImage(cm, b.copyData(null), cm.isAlphaPremultiplied(), null);
    }

}
