#include <ButtonDebounce.h>

ButtonDebounce b1(4, 100);
ButtonDebounce b2(5, 100);
ButtonDebounce b3(6, 100);

bool red = false;
bool green = false;

const int r1 = 6;
const int r2 = 12;
const int g1 = 2;
const int g2 = 13;


void b1Callback(int state)
{
  if(state)
  {
    Serial.println("Button 1"); 
    green = !green;
  }
}

void b2Callback(int state)
{
  if(state)
  {
    Serial.println("Button 2"); 
  	red = !red;
  }
}

void b3Callback(int state)
{
  if(state)
  {
    Serial.println("Button 3"); 
  }
}

void setup() 
{
  Serial.begin(9600);

  b1.setCallback(b1Callback);
  b2.setCallback(b2Callback);
  b3.setCallback(b3Callback);


  pinMode(r1, OUTPUT);
  pinMode(r2, OUTPUT);
  pinMode(g1, OUTPUT);
  pinMode(g2, OUTPUT);

}

void loop() 
{
  b1.update();
  b2.update();
  b3.update();

  digitalWrite(r1, red);
  digitalWrite(r2, red);
  digitalWrite(g1, green);
  digitalWrite(g2, green);
   
}
