// Class definitions

class Agent {
  Agent(PVector p) {
    position = p;
    dimension = new PVector(10,10);
    direction = new PVector(1,0);
    velocity = 0;
  }
  PVector position;
  PVector direction;
  
  PVector dimension;
  float velocity;
  void update() {
  
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
  
    void update() {
    PVector mouse = new PVector(mouseX, mouseY);
    if(mousePressed){
          direction = PVector.fromAngle((PVector.sub(mouse,position).heading()));
    }
    position.add(direction);
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

void setup() {
  size(500,500);
  
  CircleAgent c;
  Agent p; // Demonstrate polymorphic qualities
  
  c = new CircleAgent(new PVector(100,200), 20);
  p = new CircleAgent(new PVector(200,200), 10);
  agents = new ArrayList();
  agents.add(c);
  agents.add(p);
  agents.add(new RectangleAgent(new PVector(50,50), new PVector(20, 100), 45));
}

void draw() {
  clear();
  for(int i = 0; i < agents.size(); i++) {
    Agent a = (Agent) agents.get(i);
    a.update();
    a.render();
  }
}
