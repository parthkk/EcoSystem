/* [DisplayGrid.java]
 * A Small program for Display a 2D String Array graphically
 * @author Mangat
 */

// Graphics Imports
import javax.swing.*;
import java.awt.*;


class DisplayGrid { 
  
  private JFrame frame;
  private int maxX,maxY, GridToScreenRatio;
  private LivingThings[][] world;
  private int totalSheep = 0;
  private int totalWolves = 0;
  private int totalPlants = 0;
  private int turns = 0;
  
  DisplayGrid(LivingThings[][] w) { 
    this.world = w;
    maxX = Toolkit.getDefaultToolkit().getScreenSize().width;
    maxY = (Toolkit.getDefaultToolkit().getScreenSize().height-50);
    GridToScreenRatio = maxY / (world.length+1);  //ratio to fit in screen as square map
    
    System.out.println("Map size: "+world.length+" by "+world[0].length + "\nScreen size: "+ maxX +"x"+maxY+ " Ratio: " + GridToScreenRatio);
    
    this.frame = new JFrame("Map of World");
    GridAreaPanel worldPanel = new GridAreaPanel();
    
    
    
    
    frame.getContentPane().add(BorderLayout.CENTER, worldPanel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    frame.setVisible(true);
  }
  
  
  public void refresh() { 
    frame.repaint();
    turns++;
  }
  
  class GridAreaPanel extends JPanel {
    public void paintComponent(Graphics g) {
      Image sheep = Toolkit.getDefaultToolkit().getImage("sheep.png");
      Image wolf = Toolkit.getDefaultToolkit().getImage("wolf.png");
      Image empty = Toolkit.getDefaultToolkit().getImage("empty.png");
      Image ground = Toolkit.getDefaultToolkit().getImage("ground.png");
      Image babysheep = Toolkit.getDefaultToolkit().getImage("babysheep.png");
      Image plant0 = Toolkit.getDefaultToolkit().getImage("plant0.png");
      Image plant1 = Toolkit.getDefaultToolkit().getImage("plant1.png");
      Image plant2 = Toolkit.getDefaultToolkit().getImage("plant2.png");
      Image plant3 = Toolkit.getDefaultToolkit().getImage("plant3.png");
      Image plant4 = Toolkit.getDefaultToolkit().getImage("plant4.png");
      Image plant5 = Toolkit.getDefaultToolkit().getImage("plant5.png");
      super.repaint();
      g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
      setDoubleBuffered(true); 
      g.setColor(Color.BLACK);
      for(int i = 0; i < world.length;i++){ 
        for(int j = 0; j<world[0].length;j++){
          //if ((j == 0) || (j == world.length-1) || (i == world[i].length-1)){
            findTotals(world,totalSheep,totalWolves,totalPlants,turns, g);
            g.drawImage(ground,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            if (world[i][j] instanceof Sheep){    //This block can be changed to match character-color pairs
              if (world[i][j].getAge() > 17){
                g.drawImage(sheep,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
              }else{
                g.drawImage(babysheep,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
              }
            }else if (world[i][j] instanceof Wolf){
              g.drawImage(wolf,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            }else if (world[i][j] instanceof Plant){
              if (world[i][j].getAge() == 0){
                g.drawImage(plant0,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
              }else if (world[i][j].getAge() == 1){
                g.drawImage(plant1,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
              }else if (world[i][j].getAge() == 2){
                g.drawImage(plant2,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
              }else if (world[i][j].getAge() == 3){
                g.drawImage(plant3,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
              }else if (world[i][j].getAge() == 4){
                g.drawImage(plant4,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
              }else{
                g.drawImage(plant5,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
              }
            }else{ 
              g.drawImage(empty,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            }
            //g.fillRect(j*GridToScreenRatio, i*GridToScreenRatio, GridToScreenRatio, GridToScreenRatio);
            g.drawRect(j*GridToScreenRatio, i*GridToScreenRatio, GridToScreenRatio, GridToScreenRatio);
          //}
        }
      }
    }
    //Method to find and print the total number of each object and the turn number
    public void findTotals(LivingThings[][] map, int totalSheep, int totalWolves, int totalPlants, int turns, Graphics g){
      for(int i = 0; i<map.length;i++){
        for(int j = 0; j<map[0].length;j++){
          if (map[i][j] instanceof Sheep){
            totalSheep++;   
          }else if (map[i][j] instanceof Wolf){
            totalWolves++;
          }else if (map[i][j] instanceof Plant){
            totalPlants++;
          }
        }
      }
      g.drawString("Total Turns: " + turns, 700, 200);
      g.drawString("Total Sheep: " + totalSheep, 700, 300);
      g.drawString("Total Wolves: " + totalWolves, 700, 400);
      g.drawString("Total Plants: " + totalPlants, 700, 500);
    }
    
  }//end of GridAreaPanel
  
} //end of DisplayGrid

