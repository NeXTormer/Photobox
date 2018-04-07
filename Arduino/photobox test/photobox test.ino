#include <ButtonDebounce.h>

ButtonDebounce b1(3, 100);
ButtonDebounce b2(4, 100);
ButtonDebounce b3(5, 100);

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
    Serial.print("t"); 
  }
}

void b2Callback(int state)
{
  if(state)
  {
    Serial.print("y"); 
    red = !red;
  }
}

void b3Callback(int state)
{
  if(state)
  {
    Serial.print("n");
    green = !green;
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
