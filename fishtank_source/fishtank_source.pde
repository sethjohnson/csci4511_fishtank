float clamp(float input, float bound_a, float bound_b) {
  if(input < min(bound_a, bound_b)) return min(bound_a, bound_b);
  else if(input >max(bound_a, bound_b)) return max(bound_a, bound_b);
  else return input;
}

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

class Entity {
  Entity(PVector p) {
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


class CircleEntity extends Entity {
  CircleEntity(PVector p, float r) {
    super(p);
    dimension = new PVector(r,r);
  }
    void draw() {
     stroke(0);
    //ellipse(0,0, 2*dimension.x,2*dimension.y);
    if (is_overlapped) fill(255,0,0);
      ellipse(0,0, dimension.x,dimension.y);
        fill(255, 255, 255);
        line(0,0,dimension.x, 0);

  }
    
  
    void update(float d_t) {
//    PVector mouse = new PVector(mouseX, mouseY);
//    if(mousePressed){
//                // Make the direction slowly change until object is facing target
//          direction.add (PVector.mult(PVector.fromAngle((PVector.sub(mouse,position).heading())),turn_speed));
//          
//          if(direction.mag() > max_velocity) {
//            direction.setMag(max_velocity);
//          }
//         
//    }
//    position.add(PVector.mult(direction, d_t));
//    is_overlapped = false;
//    for (int i = 0; i < zones.size(); i++) {
//      PVector distance = PVector.sub(position, ((RectangleEntity)zones.get(i)).point_nearest_point(position));
//      if(PVector.dot(distance, distance) < dimension.x*dimension.x){
//        is_overlapped = true;
//        break;
//      }
//    }
//    //position.x = mouseX;
//    //position.y = mouseY;
  }
}


class RectangleEntity extends Entity {
  RectangleEntity(PVector p, PVector d, float a) {
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
                  clamp(rot.y, position.y+dimension.y/2, position.y-dimension.y/2)
                );
    closest.sub(position);
    closest.rotate(direction.heading());
    closest.add(position);
    return closest;
  }
  void update() {
  }
}

class Zone extends RectangleEntity {
  ArrayList needLinks;
  Zone(PVector p, PVector d, float a) {
    super(p,d,a);
    needLinks = new ArrayList();
  }
    
}

class Agent extends CircleEntity {
  ArrayList needs;
  Agent(PVector p, float r) {
    super(p, r);
    needs = new ArrayList();
  }
  void addNeed(Need need) {
    needs.add(need);
  }
  
  void update(float d_t) {
    PVector mouse = ((Need)needs.get(0)).link.zone.point_nearest_point(position);
    if(true){
                // Make the direction slowly change until object is facing target
          direction.add (PVector.mult(PVector.fromAngle((PVector.sub(mouse,position).heading())),turn_speed));
          
          if(direction.mag() > max_velocity) {
            direction.setMag(max_velocity);
          }
         
    }
    position.add(PVector.mult(direction, d_t));
    is_overlapped = false;
    for (int i = 0; i < zones.size(); i++) {
      PVector distance = PVector.sub(position, ((RectangleEntity)zones.get(i)).point_nearest_point(position));
      if(PVector.dot(distance, distance) < dimension.x*dimension.x){
        is_overlapped = true;
        break;
      }
    }
    //position.x = mouseX;
    //position.y = mouseY;
  }
  
  
  void render() {
    pushMatrix();
    translate(position.x, position.y);
    rotate(direction.heading());
    draw();
    popMatrix();
  }
  void draw() {
     stroke(0);
    //ellipse(0,0, 2*dimension.x,2*dimension.y);
    if (is_overlapped) fill(255,0,0);
      ellipse(0,0, 2*dimension.x,2*dimension.y);
        fill(255, 255, 255);
        line(0,0,dimension.x, 0);
    for(int i = 0; i < needs.size(); i++){
      println(((Need)needs.get(i)).name);
    }

  }
}

class Need {
  String name;
  int value;
  int threshold;
  NeedZoneLink link;
  void decrement(){
   value--; 
  }

 Need(String name, int value, int threshold,Zone z){
  this.name = name;
  this.value = value;
  this.threshold = threshold; 
  link = new NeedZoneLink(z,this);
 }
}

class NeedZoneLink {
  Zone zone;
  Need need;
  float fill_rate;
  NeedZoneLink(Zone z, Need n) {
    zone = z;
    need = n;
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
  
  Agent c= new Agent(new PVector(100,200), 20);;
  Agent d = new Agent(new PVector(150,200), 15);; 
   
  Zone zone = new Zone(new PVector(250,300), new PVector(500,60),0);
  Zone zone2 = new Zone(new PVector(250,25), new PVector(50,50),45);
  
  c.addNeed(new Need("neediness", 100, 5, zone));
  d.addNeed(new Need("neediness2", 100, 5, zone));

  agents = new ArrayList();
  zones = new ArrayList();
  zones.add(zone);
  zones.add(zone2);
  agents.add(c);
  agents.add(d);

}



int tick=0;

void draw() {
  clear();
  int temp_tick = millis();
  
  for(int i = 0; i < zones.size(); i++) {
    Entity a = (Entity) zones.get(i);
    a.update((temp_tick-tick)/1000.0);
    a.render();
  }
  for(int i = 0; i < agents.size(); i++) {
    Entity a = (Entity) agents.get(i);
    a.update((temp_tick-tick)/1000.0);
    a.render();
  }

  tick = temp_tick;
}
