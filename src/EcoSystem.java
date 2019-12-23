/* EcoSystem.java
 * Version 1.0
 * 11/22/2018
 * Parth Kotecha
 * Code for Eco System Simulater
 * Things added:
 * 1. Sheep have a radius they can see around
 * 2. Sheep find other plants within the radius
 * 3. If sheep health is above the inital starting health and age is above 17, sheep look for other sheep
 * 4. Wolf have a radius they can see around
 * 5. Wolf find sheep within the radius
 * 6. Spawning sheep will be near parents
 * 7. Sheep look different when they turn 18
 * 8. Plants grow, different nutritional value while they are growing.
 */

//////////////////////////////////////////////////////////////
//Vision means that the sheep can see in one 
//direction a certain number of spots. For example
//if the vision was set to 3, the sheep could see 3 
//to the left, 3 up, 3 to the right, 3 down, and anything in
//between. Example: 2 up and 1 left, 1 down  3 right, etc.
////////////////////////////////////////////////////////////////


import java.util.Scanner;

class EcoSystem{ 
  
  
  public static void main(String [] args) {
    
    //Variables:
    Scanner input = new Scanner(System.in);
    boolean visited = false;
    int age = 0;
    int width = 0;
    int length = 0;
    int plantRate = 0;
    int plantNutritionalValue = 0;
    int sheepHealth = 0;
    int wolfHealth = 0;
    int startingSheep = 0;
    int startingWolf = 0;
    int sheepVision = 0;
    int wolfVision = 0;    
//    int totalSheep = 0;
//    int totalWolves = 0;
//    int totalPlants = 0;
    int turns = 0;
    //User Input:
    System.out.println("Hello! Welcome to EcoSystem Simulation.");
    System.out.println("Choose your setting: Default/Custom");
    String settings = "";
    while ((!settings.toLowerCase().equals("default")) && (!settings.toLowerCase().equals("custom"))){
      settings = input.nextLine();
      if (settings.toLowerCase().equals("default")){
        width = 20;
        length = 20;
        plantRate = 4;
        plantNutritionalValue = 10;
        sheepHealth = 75;
        wolfHealth = 75;
        startingSheep = 100;
        startingWolf = 3;
        sheepVision = 5;
        wolfVision = 7;
      }else if (settings.toLowerCase().equals("custom")){
        System.out.println("What is the width of the board: ");
        width = input.nextInt();
        System.out.println("What is the length of the board: ");
        length = input.nextInt();
        System.out.println("What is the percent of the chance of a plant spawning (recommended 3 - 7): ");
        plantRate = input.nextInt();
        System.out.println("What is the nutritional value for plants: ");
        plantNutritionalValue = input.nextInt();
        System.out.println("What is the health for sheep: ");
        sheepHealth = input.nextInt();
        System.out.println("What is the health for wolves: ");
        wolfHealth = input.nextInt();
        System.out.println("What is the starting number of sheep: ");
        startingSheep = input.nextInt();
        System.out.println("What is the sheep's vision: ");
        sheepVision = input.nextInt();
        System.out.println("What is the starting number of wolves: ");
        startingWolf = input.nextInt();
        System.out.println("What is the wolf's vision: ");
        wolfVision = input.nextInt();
      }else{
        System.out.println("That is not an option! Choose your setting: Default/Custom");
      }
    }
    
    //----------------------------------------------------------------//
    //Setup:
    LivingThings map[][] = new LivingThings[width][length];
    moveItemsOnGrid(map, visited, sheepVision, wolfVision, sheepHealth);
    DisplayGrid grid = new DisplayGrid(map);
    setupGrid(map, width, length, startingSheep, startingWolf, sheepHealth, wolfHealth, age, visited);
    spawnPlant(map, plantNutritionalValue, age, plantRate, visited);
    turns = 0;
    while ((endGame(map)) == false){
      turns++;
      loseHealth(map);
      setPlantValue(map, plantNutritionalValue);
      
      //Spawns plants throughout simulation    
      spawnPlant(map, plantNutritionalValue, age, plantRate, visited);
      
      //Small delay
      try{ Thread.sleep(200); }catch(Exception e) {};
      
      
      System.out.println("Turn: " + turns);
      updateVisitAndAge(map);
      //Makes changes to map
      moveItemsOnGrid(map, visited, sheepVision, wolfVision, sheepHealth);
      //Display the grid on a Panel
      grid.refresh();
    }
    System.out.println("Simulation has ended.");
    input.close();
  }
  
  // Method to simulate grid movement
  public static void moveItemsOnGrid(LivingThings[][] map, boolean visited, int sheepVision, int wolfVision, int sheepHealth){
    for(int i = 0; i<map.length;i++){
      for(int j = 0; j<map[0].length;j++){
        int random = (int) Math.floor(Math.random() * 4);
        if ((map[i][j]!= null) &&(map[i][j].getVisited() == false)){
          if (map[i][j] instanceof Sheep){
            //If the health conditions and age conditions are met, the sheep look for other sheep  
            if (((Sheep)map[i][j]).findAnotherSheep(map,i,j,sheepVision, sheepHealth).equals("up") && (i>0) && (!(map[i-1][j] instanceof Wolf))){
              if (map[i-1][j] instanceof Sheep){
                ((Sheep)map[i][j]).makeBaby(map,i,j,sheepVision);
                map[i-1][j] = new Sheep(map[i][j].getHealth()/2, map[i][j].getAge(), i-1, j, true);
              }else if (map[i-1][j] instanceof Plant){
                map[i-1][j] = new Sheep(map[i][j].getHealth() + map[i-1][j].getHealth(), map[i][j].getAge(), i-1, j, true);
                map[i][j] = null;
              }else{
                map[i-1][j] = new Sheep(map[i][j].getHealth(), map[i][j].getAge(), i-1, j, true);
                map[i][j] = null;                 
              }
            }else if (((Sheep)map[i][j]).findAnotherSheep(map,i,j,sheepVision, sheepHealth).equals("down") && (i<map.length-1) && (!(map[i+1][j] instanceof Wolf))){
              if (map[i+1][j] instanceof Sheep){
                ((Sheep)map[i][j]).makeBaby(map,i,j,sheepVision);
                map[i+1][j] = new Sheep(map[i][j].getHealth()/2, map[i][j].getAge(), i+1, j, true);
              }else if (map[i+1][j] instanceof Plant){
                map[i+1][j] = new Sheep(map[i][j].getHealth() + map[i+1][j].getHealth(), map[i][j].getAge(), i+1, j, true);
                map[i][j] = null;  
              }else{
                map[i+1][j] = new Sheep(map[i][j].getHealth(), map[i][j].getAge(), i+1, j, true);
                map[i][j] = null;
              }
            }else if (((Sheep)map[i][j]).findAnotherSheep(map,i,j,sheepVision, sheepHealth).equals("left") && (j>0) && (!(map[i][j-1] instanceof Wolf))){
              if (map[i][j-1] instanceof Sheep){
                ((Sheep)map[i][j]).makeBaby(map,i,j,sheepVision);
                map[i][j-1] = new Sheep(map[i][j].getHealth()/2, map[i][j].getAge(), i, j-1, true);
              }else if (map[i][j-1] instanceof Plant){
                map[i][j-1] = new Sheep(map[i][j].getHealth() + map[i][j-1].getHealth(), map[i][j].getAge(), i, j-1, true);
                map[i][j] = null;
              }else{
                map[i][j-1] = new Sheep(map[i][j].getHealth(), map[i][j].getAge(), i, j-1, true);
                map[i][j] = null;
              }
            }else if (((Sheep)map[i][j]).findAnotherSheep(map,i,j,sheepVision, sheepHealth).equals("right") && (j<map.length-1) && (!(map[i][j+1] instanceof Wolf))){
              if (map[i][j+1] instanceof Sheep){
                ((Sheep)map[i][j]).makeBaby(map,i,j,sheepVision);
                map[i][j+1] = new Sheep(map[i][j].getHealth()/2, map[i][j].getAge(), i, j+1, true);
              }else if (map[i][j+1] instanceof Plant){
                map[i][j+1] = new Sheep(map[i][j].getHealth() + map[i][j+1].getHealth(), map[i][j].getAge(), i, j-1, true);
                map[i][j] = null;
              }else{
                map[i][j+1] = new Sheep(map[i][j].getHealth(), map[i][j].getAge(), i, j+1, true);
                map[i][j] = null;
              }
              //If conditions for finding another sheep aren't met, the sheep look for plants to eat  
            }else if (((Sheep)map[i][j]).findPlant(map,i,j,sheepVision).equals("up") && (i>0) && (!(map[i-1][j] instanceof Wolf)) && (!(map[i-1][j] instanceof Sheep))){
              if (map[i-1][j] instanceof Plant){
                map[i-1][j] = new Sheep(map[i][j].getHealth() + map[i-1][j].getHealth(), map[i][j].getAge(), i-1, j, true);
                map[i][j] = null;
              }else{
                map[i-1][j] = new Sheep(map[i][j].getHealth(), map[i][j].getAge(), i-1, j, true);
                map[i][j] = null;                 
              }
            }else if (((Sheep)map[i][j]).findPlant(map,i,j,sheepVision).equals("down") && (i<map.length-1) && (!(map[i+1][j] instanceof Wolf)) && (!(map[i+1][j] instanceof Sheep))){
              if (map[i+1][j] instanceof Plant){
                map[i+1][j] = new Sheep(map[i][j].getHealth() + map[i+1][j].getHealth(), map[i][j].getAge(), i-1, j, true);
                map[i][j] = null;
              }else{
                map[i+1][j] = new Sheep(map[i][j].getHealth(), map[i][j].getAge(), i-1, j, true);
                map[i][j] = null;
              }
            }else if (((Sheep)map[i][j]).findPlant(map,i,j,sheepVision).equals("left") && (j>0) && (!(map[i][j-1] instanceof Wolf)) && (!(map[i][j-1] instanceof Sheep))){
              if (map[i][j-1] instanceof Plant){
                map[i][j-1] = new Sheep(map[i][j].getHealth() + map[i][j-1].getHealth(), map[i][j].getAge(), i, j-1, true);
                map[i][j] = null;
              }else{
                map[i][j-1] = new Sheep(map[i][j].getHealth(), map[i][j].getAge(), i, j-1, true);
                map[i][j] = null;
              }
            }else if (((Sheep)map[i][j]).findPlant(map,i,j,sheepVision).equals("right") && (j<map.length-1) && (!(map[i][j+1] instanceof Wolf)) && (!(map[i][j+1] instanceof Sheep))){
              if (map[i][j+1] instanceof Plant){
                map[i][j+1] = new Sheep(map[i][j].getHealth() + map[i][j+1].getHealth(), map[i][j].getAge(), i, j+1, true);
                map[i][j] = null;
              }else{
                map[i][j+1] = new Sheep(map[i][j].getHealth(), map[i][j].getAge(), i, j+1, true);
                map[i][j] = null;
              }
            }else{
              //If no plants or sheep are found, the sheep randomly move.
              if ((random == 0) && (i>0)){
                if (map[i-1][j] == null){
                  map[i-1][j] = new Sheep(map[i][j].getHealth(), map[i][j].getAge(), i-1, j, true);
                  map[i][j] = null;
                }else if (map[i-1][j] instanceof Plant){
                  map[i-1][j] = new Sheep(map[i][j].getHealth() + map[i-1][j].getHealth(), map[i][j].getAge(), i-1, j, true);
                  map[i][j] = null;
                }else{
                  map[i][j] = map[i][j];
                }
              }else if ((random == 1) && (i<map.length-1)){
                if (map[i+1][j] == null){
                  map[i+1][j] = new Sheep(map[i][j].getHealth(), map[i][j].getAge(), i+1, j, true);
                  map[i][j] = null;
                }else if (map[i+1][j] instanceof Plant){
                  map[i+1][j] = new Sheep(map[i][j].getHealth() + map[i+1][j].getHealth(), map[i][j].getAge(), i-1, j, true);
                  map[i][j] = null;
                }else{
                  map[i][j] = map[i][j];
                }
              }else if ((random == 2) && (j>0)){
                if (map[i][j-1] == null){
                  map[i][j-1] = new Sheep(map[i][j].getHealth(), map[i][j].getAge(), i, j-1, true);
                  map[i][j] = null;
                }else if (map[i][j-1] instanceof Plant){
                  map[i][j-1] = new Sheep(map[i][j].getHealth() + map[i][j-1].getHealth(), map[i][j].getAge(), i, j-1, true);
                  map[i][j] = null;  
                }else{
                  map[i][j] = map[i][j];
                }
              }else if ((random == 3) && (j<map.length-1)){
                if (map[i][j+1] == null){
                  map[i][j+1] = new Sheep(map[i][j].getHealth(), map[i][j].getAge(), i, j+1, true);
                  map[i][j] = null;
                }else if (map[i][j+1] instanceof Plant){
                  map[i][j+1] = new Sheep(map[i][j].getHealth() + map[i][j+1].getHealth(), map[i][j].getAge(), i, j+1, true);
                  map[i][j] = null;
                }else{
                  map[i][j] = map[i][j];
                }
              }
            }
          }else if (map[i][j] instanceof Wolf){
            //If no sheep are in the vision of the wolf, it randomly moves
            //If the wolf happens to hit another wolf, damage is dealt to the weaker wolf
            if (((Wolf)map[i][j]).findSheep(map,i,j,wolfVision).equals("none")){
              if ((random == 0) && (i>0) && (!(map[i-1][j] instanceof Sheep))){
                if (map[i-1][j] instanceof Wolf){
                  if (((Wolf)map[i][j]).compareTo((Wolf)map[i-1][j]) == 1){
                    map[i-1][j].setHealth(map[i-1][j].getHealth() * 0.8);
                  }else if (((Wolf)map[i][j]).compareTo((Wolf)map[i-1][j]) == -1){
                    map[i][j].setHealth(map[i][j].getHealth() * 0.8);
                  }else{
                    map[i][j].setHealth(map[i][j].getHealth() * 0.9);
                    map[i-1][j].setHealth(map[i-1][j].getHealth() * 0.9);
                  }
                }else{
                  map[i-1][j] = new Wolf(map[i][j].getHealth(), map[i][j].getAge(), i-1, j, true);
                  map[i][j] = null;
                }
              }else if ((random == 1) && (i<map.length-1) && (!(map[i+1][j] instanceof Sheep))){
                if (map[i+1][j] instanceof Wolf){
                  if (((Wolf)map[i][j]).compareTo((Wolf)map[i+1][j]) == 1){
                    map[i+1][j].setHealth(map[i+1][j].getHealth() * 0.8);
                  }else if (((Wolf)map[i][j]).compareTo((Wolf)map[i+1][j]) == -1){
                    map[i][j].setHealth(map[i][j].getHealth() * 0.8);
                  }else{
                    map[i][j].setHealth(map[i][j].getHealth() * 0.9);
                    map[i+1][j].setHealth(map[i+1][j].getHealth() * 0.9);
                  }
                }else{
                  map[i+1][j] = new Wolf(map[i][j].getHealth(), map[i][j].getAge(), i+1, j, true);
                  map[i][j] = null;
                }
              }else if ((random == 2) && (j>0) && (!(map[i][j-1] instanceof Sheep))){
                if (map[i][j-1] instanceof Wolf){
                  if (((Wolf)map[i][j]).compareTo((Wolf)map[i][j-1]) == 1){
                    map[i][j-1].setHealth(map[i][j-1].getHealth() * 0.8);
                  }else if (((Wolf)map[i][j]).compareTo((Wolf)map[i][j-1]) == -1){
                    map[i][j].setHealth(map[i][j].getHealth() * 0.8);
                  }else{
                    map[i][j].setHealth(map[i][j].getHealth() * 0.9);
                    map[i][j-1].setHealth(map[i][j-1].getHealth() * 0.9);
                  }
                }else{
                  map[i][j-1] = new Wolf(map[i][j].getHealth(), map[i][j].getAge(), i, j-1, true);
                  map[i][j] = null;
                }
              }else if ((random == 3) && (j<map.length-1) && (!(map[i][j+1] instanceof Wolf)) && (!(map[i][j+1] instanceof Sheep))){
                if (map[i][j+1] instanceof Wolf){
                  if (((Wolf)map[i][j]).compareTo((Wolf)map[i][j+1]) == 1){
                    map[i][j+1].setHealth(map[i][j+1].getHealth() * 0.8);
                  }else if (((Wolf)map[i][j]).compareTo((Wolf)map[i][j+1]) == -1){
                    map[i][j].setHealth(map[i][j].getHealth() * 0.8);
                  }else{
                    map[i][j].setHealth(map[i][j].getHealth() * 0.9);
                    map[i][j+1].setHealth(map[i][j+1].getHealth() * 0.9);
                  }
                }else{
                  map[i][j+1] = new Wolf(map[i][j].getHealth(), map[i][j].getAge(), i, j+1, true);
                  map[i][j] = null;
                }
              }
              //When a wolf can see a sheep, it goes towards it  
            }else if (((Wolf)map[i][j]).findSheep(map,i,j,wolfVision).equals("up") && (i>0)){
              if (map[i-1][j] instanceof Sheep){
                map[i-1][j] = new Wolf(map[i][j].getHealth() + map[i-1][j].getHealth(), map[i][j].getAge(), i-1, j, true);
                map[i][j] = null;
              }else if (map[i-1][j] instanceof Wolf){
                if (((Wolf)map[i][j]).compareTo((Wolf)map[i-1][j]) == 1){
                  map[i-1][j].setHealth(map[i-1][j].getHealth() * 0.8);
                }else if (((Wolf)map[i][j]).compareTo((Wolf)map[i-1][j]) == -1){
                  map[i][j].setHealth(map[i][j].getHealth() * 0.8);
                }else{
                  map[i][j].setHealth(map[i][j].getHealth() * 0.9);
                  map[i-1][j].setHealth(map[i-1][j].getHealth() * 0.9);
                }
              }else{
                map[i-1][j] = new Wolf(map[i][j].getHealth(), map[i][j].getAge(), i-1, j, true);
                map[i][j] = null;
              }
            }else if (((Wolf)map[i][j]).findSheep(map,i,j,wolfVision).equals("down") && (i<map.length-1)){
              if (map[i+1][j] instanceof Sheep){
                map[i+1][j] = new Wolf(map[i][j].getHealth() + map[i+1][j].getHealth(), map[i][j].getAge(), i+1, j, true);
                map[i][j] = null;
              }else if (map[i+1][j] instanceof Wolf){
                if (((Wolf)map[i][j]).compareTo((Wolf)map[i+1][j]) == 1){
                  map[i+1][j].setHealth(map[i+1][j].getHealth() * 0.8);
                }else if (((Wolf)map[i][j]).compareTo((Wolf)map[i+1][j]) == -1){
                  map[i][j].setHealth(map[i][j].getHealth() * 0.8);
                }else{
                  map[i][j].setHealth(map[i][j].getHealth() * 0.9);
                  map[i+1][j].setHealth(map[i+1][j].getHealth() * 0.9);
                }
              }else{
                map[i+1][j] = new Wolf(map[i][j].getHealth(), map[i][j].getAge(), i+1, j, true);
                map[i][j] = null;
              }
            }else if (((Wolf)map[i][j]).findSheep(map,i,j,wolfVision).equals("left") && (j>0)){
              if (map[i][j-1] instanceof Sheep){
                map[i][j-1] = new Wolf(map[i][j].getHealth() + map[i][j-1].getHealth(), map[i][j].getAge(), i, j-1, true);
                map[i][j] = null;
              }else if (map[i][j-1] instanceof Wolf){
                if (((Wolf)map[i][j]).compareTo((Wolf)map[i][j-1]) == 1){
                  map[i][j-1].setHealth(map[i][j-1].getHealth() * 0.8);
                }else if (((Wolf)map[i][j]).compareTo((Wolf)map[i][j-1]) == -1){
                  map[i][j].setHealth(map[i][j].getHealth() * 0.8);
                }else{
                  map[i][j].setHealth(map[i][j].getHealth() * 0.9);
                  map[i][j-1].setHealth(map[i][j-1].getHealth() * 0.9);
                }
              }else{
                map[i][j-1] = new Wolf(map[i][j].getHealth(), map[i][j].getAge(), i, j-1, true);
                map[i][j] = null;
              }
            }else if (((Wolf)map[i][j]).findSheep(map,i,j,wolfVision).equals("right") && (j<map.length-1) && (!(map[i][j+1] instanceof Wolf))){
              if (map[i][j+1] instanceof Sheep){
                map[i][j+1] = new Wolf(map[i][j].getHealth() + map[i][j+1].getHealth(), map[i][j].getAge(), i, j+1, true);
                map[i][j] = null;
              }else if (map[i][j+1] instanceof Wolf){
                if (((Wolf)map[i][j]).compareTo((Wolf)map[i][j+1]) == 1){
                  map[i][j+1].setHealth(map[i][j+1].getHealth() * 0.8);
                }else if (((Wolf)map[i][j]).compareTo((Wolf)map[i][j+1]) == -1){
                  map[i][j].setHealth(map[i][j].getHealth() * 0.8);
                }else{
                  map[i][j].setHealth(map[i][j].getHealth() * 0.9);
                  map[i][j+1].setHealth(map[i][j+1].getHealth() * 0.9);
                }
              }else{
                map[i][j+1] = new Wolf(map[i][j].getHealth(), map[i][j].getAge(), i, j+1, true);
                map[i][j] = null;
              }
            }
          }
        }
      }
    }
  }
  //Resets visited and updates age
  public static void updateVisitAndAge(LivingThings[][]map){
    for(int i = 0; i<map.length;i++){
      for(int j = 0; j<map[0].length;j++){
        if (map[i][j] != null){
          map[i][j].setVisited(false);
          map[i][j].setAge(map[i][j].getAge()+1);
        }
      }
    }
  }
  //Every turn all animals lose 1 health
  public static void loseHealth(LivingThings[][]map){
    for(int i = 0; i<map.length;i++){
      for(int j = 0; j<map[0].length;j++){
        if ((map[i][j] instanceof Sheep) || (map[i][j] instanceof Wolf)){
          map[i][j].setHealth(map[i][j].getHealth()-1);
        }
        if ((map[i][j] != null) && (map[i][j].getHealth() <= 0)){
          map[i][j] = null; 
        }
      }
    }
  }
  
  //Boolean for ending the simulation
  public static boolean endGame(LivingThings[][]map){
    int totalSheep = 0;
    int totalWolves = 0;
    int totalPlants = 0;
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
    if ((totalSheep == 0) || (totalWolves == 0) || (totalPlants == 0)){
      return true;
    }else{
      return false;
    }
  }
  
  //Method for spawning plants throughout simulation    
  public static void spawnPlant(LivingThings[][]map, int plantNutritionalValue, int age, int plantRate, boolean visited){
    for (int i = 0; i < map.length; i++){
      for (int j = 0; j < map[0].length; j++){
        int number = (int) Math.floor(Math.random() * 100);
        if ((map[i][j] == null) && (number < plantRate)){
          map[i][j] = new Plant(plantNutritionalValue,age,i,j,visited);
        }
      }
    }
  }
  
  //Set the plants nutritional value based on the age
  public static void setPlantValue(LivingThings[][]map, int plantNutritionalValue){
    for (int i = 0; i < map.length; i++){
      for (int j = 0; j < map[0].length; j++){
        if (map[i][j] instanceof Plant){
          if (map[i][j].getAge() == 0){
            map[i][j].setHealth(plantNutritionalValue * 0.5);
          }else if (map[i][j].getAge() == 1){
            map[i][j].setHealth(plantNutritionalValue * 0.6);
          }else if (map[i][j].getAge() == 2){
            map[i][j].setHealth(plantNutritionalValue * 0.7);
          }else if (map[i][j].getAge() == 3){
            map[i][j].setHealth(plantNutritionalValue * 0.8);
          }else if (map[i][j].getAge() == 4){
            map[i][j].setHealth(plantNutritionalValue * 0.9);
          }else{
            map[i][j].setHealth(plantNutritionalValue);
          }
        }
      }
    }
  }
  
  
  
  public static void setupGrid(LivingThings[][] map, int width, int length, int startingSheep, int startingWolf, int sheepHealth, int wolfHealth, int age, boolean visited){ 
    while ((startingSheep>0) || (startingWolf>0)){
      int x = (int) Math.floor(Math.random() * width);
      int y = (int) Math.floor(Math.random() * length);
      
      if ((map[x][y] == null) && (startingSheep>0)){
        map[x][y] = new Sheep(sheepHealth,age,x,y,visited);
        startingSheep = startingSheep - 1;
//        System.out.println("There is a sheep at (" + x + ", " + y + ")");
      }
      
      if ((map[x][y] == null) && (startingWolf>0)){
        map[x][y] = new Wolf(wolfHealth,age,x,y,visited);
        startingWolf = startingWolf - 1;
//        System.out.println("There is a wolf at (" + x + ", " + y + ")");
      }
    }
  }
  //method to display grid a text for debugging
  public static void DisplayGridOnConsole(LivingThings[][] map) { 
    for(int i = 0; i<map.length;i++){        
      for(int j = 0; j<map[0].length;j++)
        System.out.print(map[i][j]+" ");
      System.out.println("");
    }
  }
}