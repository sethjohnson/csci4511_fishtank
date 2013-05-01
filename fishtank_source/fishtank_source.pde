float clamp(float input, float bound_a, float bound_b) {
  if(input < min(bound_a, bound_b)) return min(bound_a, bound_b);
  else if(input >max(bound_a, bound_b)) return max(bound_a, bound_b);
  else return input;
}

boolean circle_overlap_rect(PVector circle_center, float radius, PVector 

// Class definitions

class Agent {
  Agent(PVector p) {
    position = p;
    dimension = new PVector(10,10);
    direction = new PVector(1,0);
    velocity = 0;
    turn_speed = 1;
    max_velocity = 50;
    
  }
  PVector position;
  PVector direction;
  
  PVector dimension;
  float velocity;
  float max_velocity;
  float turn_speed;
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
    ellipse(0,0, 2*dimension.x,2*dimension.y);
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
  
  void update() {
  }
}



// Global Initializations


ArrayList agents;
// Program functions

void clear() {
  background(128);
}

Agent zone;

void setup() {
  size(500,500);
  
  CircleAgent c;
  Agent p; // Demonstrate polymorphic qualities
  
  c = new CircleAgent(new PVector(100,200), 20);
  p = new CircleAgent(new PVector(200,200), 10);
  zone = new RectangleAgent(new PVector(250,250), new PVector(100,60),0);
  agents = new ArrayList();
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
