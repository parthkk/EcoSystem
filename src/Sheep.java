//Sheep Object
class Sheep extends LivingThings{
  
  Sheep(double health, int age, int x, int y, boolean visited){
    super(health, age, x, y, visited);
  }
  
  //Method for sheep to detect plants
  public String findPlant(LivingThings[][]map, int i, int j, int sheepVision){
    sheepVision = sheepVision + 1;
    String direction = "none";
    for (int vRadius = 1; vRadius<(sheepVision); vRadius++){
      for(int hRadius = 1; hRadius<(sheepVision); hRadius++){
        if ((i>(-1+vRadius)) && (map[i-vRadius][j] instanceof Plant)){
          direction = "up";
          vRadius = sheepVision;
          hRadius = sheepVision;
        }else if ((i<map.length-vRadius) && (map[i+vRadius][j] instanceof Plant)){
          direction = "down";
          vRadius = sheepVision;
          hRadius = sheepVision;
        }else if ((j>(-1+hRadius)) && (map[i][j-hRadius] instanceof Plant)){
          direction = "left";
          vRadius = sheepVision;
          hRadius = sheepVision;
        }else if ((j<map[0].length-hRadius) && (map[i][j+hRadius] instanceof Plant)){
          direction = "right";
          vRadius = sheepVision;
          hRadius = sheepVision;
        }else if ((i<map.length-vRadius) && (j<map[0].length-hRadius) && (map[i+vRadius][j+hRadius] instanceof Plant)){
          direction = "down";
          vRadius = sheepVision;
          hRadius = sheepVision;
        }else if ((i<map.length-vRadius) && (j>(-1+hRadius)) && (map[i+vRadius][j-hRadius] instanceof Plant)){
          direction = "left";
          vRadius = sheepVision;
          hRadius = sheepVision;
        }else if ((i>(-1+vRadius)) && (j>(-1+hRadius)) && (map[i-vRadius][j-hRadius] instanceof Plant)){
          direction = "up";
          vRadius = sheepVision;
          hRadius = sheepVision;
        }else if ((i>(-1+vRadius)) && (j<map.length-hRadius) && (map[i-vRadius][j+hRadius] instanceof Plant)){
          direction = "right";
          vRadius = sheepVision;
          hRadius = sheepVision;
        }
      }
    }
      return direction;
  }
  
  //Method for sheep to find other sheep
  public String findAnotherSheep(LivingThings[][]map, int i, int j, int sheepVision, int sheepHealth){
    sheepVision = sheepVision + 1;
    String direction = "none";
    for (int vRadius = 1; vRadius<(sheepVision); vRadius++){
      for(int hRadius = 1; hRadius<(sheepVision); hRadius++){
        if ((i>(-1+vRadius)) && (map[i-vRadius][j] instanceof Sheep) && (map[i][j].getAge() > 17) && (map[i-vRadius][j].getAge() > 17) && (map[i][j].getHealth() > sheepHealth) && (map[i-vRadius][j].getHealth() > sheepHealth)){
          direction = "up";
          vRadius = sheepVision;
          hRadius = sheepVision;
        }else if ((i<map.length-vRadius) && (map[i+vRadius][j] instanceof Sheep) && (map[i][j].getAge() > 17) && (map[i+vRadius][j].getAge() > 17) && (map[i][j].getHealth() > sheepHealth) && (map[i+vRadius][j].getHealth() > sheepHealth)){
          direction = "down";
          vRadius = sheepVision;
          hRadius = sheepVision;
        }else if ((j>(-1+hRadius)) && (map[i][j-hRadius] instanceof Sheep) && (map[i][j].getAge() > 17) && (map[i][j-hRadius].getAge() > 17) && (map[i][j].getHealth() > sheepHealth) && (map[i][j-hRadius].getHealth() > sheepHealth)){
          direction = "left";
          vRadius = sheepVision;
          hRadius = sheepVision;
        }else if ((j<map[0].length-hRadius) && (map[i][j+hRadius] instanceof Sheep) && (map[i][j].getAge() > 17) && (map[i][j+hRadius].getAge() > 17) && (map[i][j].getHealth() > sheepHealth) && (map[i][j+hRadius].getHealth() > sheepHealth)){
          direction = "right";
          vRadius = sheepVision;
          hRadius = sheepVision;
        }else if ((i<map.length-vRadius) && (j<map[0].length-hRadius) && (map[i+vRadius][j+hRadius] instanceof Sheep) && (map[i][j].getAge() > 17) && (map[i+vRadius][j+hRadius].getAge() > 17) && (map[i][j].getHealth() > sheepHealth) && (map[i+vRadius][j+hRadius].getHealth() > sheepHealth)){ 
          direction = "down";
          vRadius = sheepVision;
          hRadius = sheepVision;
        }else if ((i<map.length-vRadius) && (j>(-1+hRadius)) && (map[i+vRadius][j-hRadius] instanceof Sheep) && (map[i][j].getAge() > 17) && (map[i+vRadius][j-hRadius].getAge() > 17) && (map[i][j].getHealth() > sheepHealth) && (map[i+vRadius][j-hRadius].getHealth() > sheepHealth)){
          direction = "left";
          vRadius = sheepVision;
          hRadius = sheepVision;
        }else if ((i>(-1+vRadius)) && (j>(-1+hRadius)) && (map[i-vRadius][j-hRadius] instanceof Sheep) && (map[i][j].getAge() > 17) && (map[i-vRadius][j-hRadius].getAge() > 17) && (map[i][j].getHealth() > sheepHealth) && (map[i-vRadius][j-hRadius].getHealth() > sheepHealth)){
          direction = "up";
          vRadius = sheepVision;
          hRadius = sheepVision;
        }else if ((i>(-1+vRadius)) && (j<map.length-hRadius) && (map[i-vRadius][j+hRadius] instanceof Sheep) && (map[i][j].getAge() > 17) && (map[i-vRadius][j+hRadius].getAge() > 17) && (map[i][j].getHealth() > sheepHealth) && (map[i-vRadius][j+hRadius].getHealth() > sheepHealth)){
          direction = "right";
          vRadius = sheepVision;
          hRadius = sheepVision;
        }
      }
    }
      return direction;
  }

  //Method for sheep reproducing
  public void makeBaby(LivingThings[][]map, int i, int j, int sheepVision){
    if ((i>0) && ((map[i-1][j] == null) || (map[i-1][j] instanceof Plant))){
      map[i-1][j] = new Sheep(map[i][j].getHealth(), 1, i-1, j, true);
      map[i][j] = new Sheep(map[i][j].getHealth()/2, map[i][j].getAge(), i, j, true);
    }else if ((i<map.length-1) && ((map[i+1][j] == null) || (map[i+1][j] instanceof Plant))){
      map[i+1][j] = new Sheep(map[i][j].getHealth(), 1, i+1, j, true);
      map[i][j] = new Sheep(map[i][j].getHealth()/2, map[i][j].getAge(), i, j, true);
    }else if ((j>0) && ((map[i][j-1] == null) || (map[i][j-1] instanceof Plant))){
      map[i][j-1] = new Sheep(map[i][j].getHealth(), 1, i, j-1, true);
      map[i][j] = new Sheep(map[i][j].getHealth()/2, map[i][j].getAge(), i, j, true);
    }else if ((j<map.length-1) && ((map[i][j+1] == null) || (map[i][j+1] instanceof Plant))){
      map[i][j+1] = new Sheep(map[i][j].getHealth(), 1, i, j+1, true);
      map[i][j] = new Sheep(map[i][j].getHealth()/2, map[i][j].getAge(), i, j, true);
    }
  }
  
}
    