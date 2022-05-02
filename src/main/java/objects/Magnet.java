package objects;

public class Magnet {
    private double strength;
    private int x;
    private int y;

    public Magnet(double strength, int x, int y){
        this.strength = strength;
        this.x = x;
        this.y = y;
    }

    public double getStrength(){
        return this.strength;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public void setStrength(double strength){
        this.strength = strength;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }



}

