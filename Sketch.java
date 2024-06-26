/**
 * Description: creating images and animations
 * @author EdricLai
*/

import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;

public class Sketch extends PApplet {
  /**
   * obstacle object
   * @author Edric
  */
  class Obstacle {
    // variables
    PImage imgObstacle;
    float fltPosX;
    float fltPosY;
    float fltDiameter;

    /**
     * initialize new objects
     * @param image image display
     * @param posX x-coordinate
     * @param posY y-coordinate
     * @param diameter diameter
     * @author EdricLai
    */
    public Obstacle (PImage image, float posX, float posY, int diameter) {
      this.imgObstacle = image;
      this.fltPosX = posX;
      this.fltPosY = posY;
      this.fltDiameter = diameter;
    }
  }

  /*
   * data structures
   * and global variables
   */
  ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
  PImage imgBackground;
  float fltScale;
  float fltSpeed;
  float fltIncNum;
  float fltPlayerPosX;
  float fltPlayerPosY;
  float fltPlayerDiameter;

  /**
   * initial settings
   * @author EdricLai
   */
  public void settings() {
    // screen size
    size(1000, 700);
  }

  /** 
   * initial setup values
   * @author EdricLai
   */
  public void setup() {
    // initialize variables
    imgBackground = loadImage("images/space.png");
    fltScale = width * height;
    fltSpeed = fltScale / 40000;
    fltIncNum = 0;
    fltPlayerPosX = width / 2;
    fltPlayerPosY = height / 2;
    fltPlayerDiameter = fltScale / 20000;

    // settings
    frameRate(60);
    imgBackground.resize(width, height);
    background(imgBackground);

    // create obstacles
    createObstacles((int) random(5, 10));
  }
  
  /**
   * called repeatedly
   * @author EdricLai
   */
  public void draw() {
    // settings
    disintegrate(); // disable to permanently see background

    // player
    player();

    // obstacles
    obstacles();
  }

  /**
   * disintegration effect
   * @author EdricLai
  */
  public void disintegrate() {
    // settings
    stroke(0, 0, 0);
    strokeWeight(fltScale / 100000);

    // random lines
    for (int i = 0; i <= 40; i++) {
      line(random(width), random(height), random(width), random(height));
    }
  }

  /**
   * player code
   * @author EdricLai
  */
  public void player() {
    // visual
    noStroke();
    fill(0, 255, 255);
    ellipse(fltPlayerPosX, fltPlayerPosY, fltPlayerDiameter, fltPlayerDiameter);

    // movement
    if (keyPressed) {
      if (key == 'w' && fltPlayerPosY > 0) {
        fltPlayerPosY -= fltSpeed;
      }
      else if (key == 's' && fltPlayerPosY < height) {
        fltPlayerPosY += fltSpeed;
      }
      else if (key == 'a' && fltPlayerPosX > 0) {
        fltPlayerPosX -= fltSpeed;
      }
      else if (key == 'd' && fltPlayerPosX < width) {
        fltPlayerPosX += fltSpeed;
      }
    }
  }

  /**
   * obstacle code
   * @author EdricLai
  */
  public void obstacles() {
    // execute at all objects
    for (int i = 0; i < obstacles.size(); i++) {
      Obstacle obj = obstacles.get(i);
      // visual
      image(obj.imgObstacle, obj.fltPosX - obj.imgObstacle.width / 2, obj.fltPosY - obj.imgObstacle.height / 2);

      // collision
      if (dist(fltPlayerPosX, fltPlayerPosY, obj.fltPosX, obj.fltPosY) < fltPlayerDiameter / 2 + obj.fltDiameter / 2) {
        if (fltPlayerPosX > obj.fltPosX) {
          fltPlayerPosX += fltSpeed;
        }
        else if (fltPlayerPosX < obj.fltPosX) {
          fltPlayerPosX -= fltSpeed;
        }
        if (fltPlayerPosY > obj.fltPosY) {
          fltPlayerPosY += fltSpeed;
        }
        else if (fltPlayerPosY < obj.fltPosY) {
          fltPlayerPosY -= fltSpeed;
        }
      }

      // movement
      // random
      // obj.fltPosX += (random(-fltSpeed, fltSpeed)) / 5;
      // obj.fltPosY += (random(-fltSpeed, fltSpeed)) / 5;
      
      // sinusoidal
      // obj.fltPosX += fltSpeed / 10;
      // obj.fltPosY += (float) Math.sin(radians(obj.fltPosX));
      
      // parabolic
      // fltIncNum += 0.01;
      // obj.fltPosX += fltSpeed / 10;
      // obj.fltPosY += Math.pow(fltIncNum, 2);
      
      // circular
      // fltIncNum += 0.1f;
      // obj.fltPosY += (cos(radians(fltIncNum)) - cos(radians(fltIncNum - 0.1f))) * 10 * obj.fltDiameter;
      // obj.fltPosX += (sin(radians(fltIncNum)) - sin(radians(fltIncNum - 0.1f))) * 10 * obj.fltDiameter;
    }
  }
  
  /**
   * create obstacle
   * @param numObj number of obstacles
   * @author EdricLai
  */
  public void createObstacles(int numObj) {
    for (int i = 0; i <= numObj; i++) {
      // create object with random properties
      int diameter = (int) (fltScale / random(1000, 20000));
      obstacles.add(new Obstacle(loadImage("images/asteroid.png"), random(width), random(height), diameter));

      // extends properties to image size
      Obstacle obj = obstacles.get(i);
      obj.imgObstacle.resize(diameter, diameter);
    }
  }
}