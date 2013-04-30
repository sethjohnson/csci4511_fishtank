void clear() {
  background(192, 64, 0);
}
Agent a;

class Agent {
  color c;
  float xpos;
  float ypos;
  float xspeed;
  

  // The Constructor is defined with arguments.
  Agent(color tempC, float tempXpos, float tempYpos, float tempXspeed) { 
    c = tempC;
    xpos = tempXpos;
    ypos = tempYpos;
    xspeed = tempXspeed;
  }
  void draw() {
    ellipse(xpos, ypos, 55, 55);
  }
  
}

void setup() {
  size(400, 400);
  stroke(255);
  a = new Agent(color(255,0,0), 100, 100, 100);
  m = millis();
}
int m;
void draw() {
  int temp_m = millis();
  a.xpos+=cos(atan( (a.ypos-mouseY)/(a.xpos-mouseX)))*(temp_m-m)/1000.0*a.xspeed;
  a.ypos+=sin(atan( (a.ypos-mouseY)/(a.xpos-mouseX)))*(temp_m-m)/1000.0*a.xspeed;

  clear();
  line(150, 25, mouseX, mouseY);
  a.draw();
  m = temp_m;
}
