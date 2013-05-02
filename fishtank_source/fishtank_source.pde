Agent zone;


boolean circle_overlap_rect(PVector circle_center, float radius, PVector rect_center, PVector rect_dim, PVector orientation){
  // clamp(value, min, max) - limits value to the range min..max
  PVector rot = new PVector(circle_center.x, circle_center.y);
  rot.sub(rect_center);
  rot.rotate(-orientation.heading());
  rot.add(rect_center);
  // Find the closest point to the circle within the rectangle
  float closestX = clamp(rot.x, rect_center.x-rect_dim.x/2, rect_center.x+rect_dim.x/2);
  float closestY = clamp(rot.y, rect_center.y+rect_dim.y/2, rect_center.x-rect_dim.y/2);

  // Calculate the distance between the circle's center and this closest point
  float distanceX = rot.x - closestX;
  float distanceY = rot.y - closestY;

  // If the distance is less than the circle's radius, an intersection occurs
  float distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);
  return distanceSquared < (radius * radius);
}

// Class definitions

class Agent {
  Agent(PVector p) {
    position = p;
    dimension = new PVector(10,10);
    direction = new PVector(1,0);
    velocity = 0;
    turn_speed = 1;
    max_velocity = 50;
    is_overlapped = false;
    
    
  }
  PVector position;
  PVector direction;
  
  PVector dimension;
  float velocity;
  float max_velocity;
  float turn_speed;
  boolean is_overlapped;

  void update(float d_t) {
  }
  
  void render() {
    pushMatrix();
    translate(position.x, position.y);
    rotate(direction.heading());
    draw();
    popMatrix();
  }
  void draw(){
  }
}


class CircleAgent extends Agent {
  CircleAgent(PVector p, float r) {
    super(p);
    dimension = new PVector(r,r);
  }
    void draw() {
     stroke(0);
    //ellipse(0,0, 2*dimension.x,2*dimension.y);
    if (is_overlapped) fill(255,0,0);
      ellipse(0,0, 2*dimension.x,2*dimension.y);
        fill(255, 255, 255);
        line(0,0,dimension.x, 0);

  }
    
  
    void update(float d_t) {
    PVector mouse = new PVector(mouseX, mouseY);
    if(mousePressed){
                // Make the direction slowly change until object is facing target
          direction.add (PVector.mult(PVector.fromAngle((PVector.sub(mouse,position).heading())),turn_speed));
          
          if(direction.mag() > max_velocity) {
            direction.setMag(max_velocity);
          }
         
    }
    position.add(PVector.mult(direction, d_t));
    is_overlapped = false;
    for (int i = 0; i < zones.size(); i++) {
      PVector distance = PVector.sub(position, ((RectangleAgent)zones.get(i)).point_nearest_point(position));
      if(PVector.dot(distance, distance) < dimension.x*dimension.x){
        is_overlapped = true;
        break;
      }
    }
    //position.x = mouseX;
    //position.y = mouseY;
  }
}


class RectangleAgent extends Agent {
  RectangleAgent(PVector p, PVector d, float a) {
    super(p);
    dimension = d;
    velocity = 0;
    direction = PVector.fromAngle(radians(a));
  }
    void draw() {
     stroke(0);
    rect(-dimension.x/2, -dimension.y/2, dimension.x, dimension.y);
    //line(0,0,size, 0);
  }
  PVector point_nearest_point(PVector target) {
    PVector rot = new PVector(target.x, target.y);
    rot.sub(position);
    rot.rotate(-direction.heading());
    rot.add(position);
    // Find the closest point to the circle within the rectangle
    PVector closest = new PVector(
                  clamp(rot.x, position.x-dimension.x/2, position.x+dimension.x/2),
                  clamp(rot.y, position.y+dimension.y/2, position.x-dimension.y/2)
                );
    closest.sub(position);
    closest.rotate(direction.heading());
    closest.add(position);
    return closest;
  }
  void update() {
  }
}


class Need {
  String name;
  int value;
  int threshold;
  
  void decrement(){
   value--; 
  }
 Need(){} 
 Need(String name, int value, int threshold){
  this.name = name;
  this.value = value;
  this.threshold = threshold; 
 }
}

// Global Initializations


ArrayList agents;
ArrayList zones;
// Program functions

void clear() {
  background(128);
}



void setup() {
  size(500,500);
  
  CircleAgent c;
  Agent p; // Demonstrate polymorphic qualities
  
  c = new CircleAgent(new PVector(100,200), 20);
  p = new CircleAgent(new PVector(200,200), 10);
  zone = new RectangleAgent(new PVector(250,250), new PVector(100,60),45);
  agents = new ArrayList();
  zones = new ArrayList();
  zones.add(zone);
  zones.add(new RectangleAgent(new PVector(50,50), new PVector(20, 100), 45));
  agents.add(zone);
  agents.add(c);
  agents.add(p);
  agents.add(new RectangleAgent(new PVector(50,50), new PVector(20, 100), 45));
}
int tick=0;
void draw() {
  clear();
  int temp_tick = millis();
  for(int i = 0; i < agents.size(); i++) {
    Agent a = (Agent) agents.get(i);
    a.update((temp_tick-tick)/1000.0);
    a.render();
  }
  tick = temp_tick;
}
