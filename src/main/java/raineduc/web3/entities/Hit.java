package raineduc.web3.entities;

public class Hit {
    private double xCoordinate;
    private double yCoordinate;
    private int radius;
    private boolean hit;

    public Hit() {};

    public Hit(double xCoordinate, double yCoordinate, int radius, boolean result) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.radius = radius;
        this.hit = result;
    }

    public double getxCoordinate() {
        return xCoordinate;
    }

    public double getyCoordinate() {
        return yCoordinate;
    }

    public int getRadius() {
        return radius;
    }

    public boolean isHit() {
        return hit;
    }

    public void setxCoordinate(double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public void setyCoordinate(double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }
}
