//Wolf Object
class Wolf extends LivingThings implements Comparable<Wolf>{
  
  Wolf(double health, int age, int x, int y, boolean visited){
    super(health, age, x, y, visited);  
  }
  
  //Method for wolves to detect plants
  public String findSheep(LivingThings[][]map, int i, int j, int wolfVision){
    wolfVision = wolfVision + 1;
    String direction = "none";
    for (int vRadius = 1; vRadius<(wolfVision); vRadius++){
      for(int hRadius = 1; hRadius<(wolfVision); hRadius++){
        if ((i>(-1+vRadius)) && (map[i-vRadius][j] instanceof Sheep)){
          direction = "up";
          vRadius = wolfVision;
          hRadius = wolfVision;
        }else if ((i<map.length-vRadius) && (map[i+vRadius][j] instanceof Sheep)){
          direction = "down";
          vRadius = wolfVision;
          hRadius = wolfVision;
        }else if ((j>(-1+hRadius)) && (map[i][j-hRadius] instanceof Sheep)){
          direction = "left";
          vRadius = wolfVision;
          hRadius = wolfVision;
        }else if ((j<map[0].length-hRadius) && (map[i][j+hRadius] instanceof Sheep)){
          direction = "right";
          vRadius = wolfVision;
          hRadius = wolfVision;
        }else if ((i<map.length-vRadius) && (j<map[0].length-hRadius) && (map[i+vRadius][j+hRadius] instanceof Sheep)){
          direction = "down";
          vRadius = wolfVision;
          hRadius = wolfVision;
        }else if ((i<map.length-vRadius) && (j>(-1+hRadius)) && (map[i+vRadius][j-hRadius] instanceof Sheep)){
          direction = "left";
          vRadius = wolfVision;
          hRadius = wolfVision;
        }else if ((i>(-1+vRadius)) && (j>(-1+hRadius)) && (map[i-vRadius][j-hRadius] instanceof Sheep)){
          direction = "up";
          vRadius = wolfVision;
          hRadius = wolfVision;
        }else if ((i>(-1+vRadius)) && (j<map.length-hRadius) && (map[i-vRadius][j+hRadius] instanceof Sheep)){
          direction = "right";
          vRadius = wolfVision;
          hRadius = wolfVision;
        }
      }
    }
      return direction;
  }
  
  //Method for comparing wolves to see who takes damage
    public int compareTo(Wolf wolf){
    if (this.getHealth() > wolf.getHealth()){
      return 1;
    }else if (this.getHealth() < wolf.getHealth()){
      return -1;
    }else{
      return 0;
    }
  }
}
