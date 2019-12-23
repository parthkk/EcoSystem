abstract class LivingThings{
  private double health;
  private int age;
  private int x,y;
  private boolean visited;
  
  LivingThings(double health, int age, int x, int y, boolean visited){
    this.health = health;
    this.age = age;
    this.x = x;
    this.y = y;
    this.visited = visited;
  }
    
  public double getHealth(){
    return this.health;
  }
  
  public void setHealth(double health){
    this.health = health;
  }
    
  public int getAge(){
    return this.age;
  }
  
  public void setAge(int age){
    this.age = age;
  }
  
  public int getX(){
    return this.x;
  }
  
  public int getY(){
    return this.y;
  }
  
  public boolean getVisited(){
    return this.visited;
  }
  
  public void setVisited(boolean visited){
    this.visited = visited;
  }
  
}
  
  
  
  
    