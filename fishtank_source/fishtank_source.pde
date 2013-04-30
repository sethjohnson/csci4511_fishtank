void setup() {
   size(400, 400);
}

class Ball {
  PVector pos;
  float size;
  
  void draw(){
    ellipse(pos.x,pos.y,size,size);
  }
}
void draw() {
  background(0,0,0,0);
  
}
