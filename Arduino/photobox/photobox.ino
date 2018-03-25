#include <ButtonDebounce.h>

ButtonDebounce b1(3, 100);
ButtonDebounce b2(4, 100);
ButtonDebounce b3(5, 100);

const int red = 6;
const int green = 2;

bool greenl = false;
bool redl = false;


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
    greenl = !greenl;
  }
}

void b3Callback(int state)
{
  if(state)
  {
    Serial.print("n");
    redl = !redl; 
  }
}

void setup() 
{
  Serial.begin(9600);

  b1.setCallback(b1Callback);
  b2.setCallback(b2Callback);
  b3.setCallback(b3Callback);

  pinMode(red, OUTPUT);
  pinMode(green, OUTPUT);
}

void loop() 
{
  b1.update();
  b2.update();
  b3.update();

  digitalWrite(red, redl);
  digitalWrite(green, greenl);
}
