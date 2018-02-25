#include <ButtonDebounce.h>

ButtonDebounce b1(4, 100);
ButtonDebounce b2(5, 100);
ButtonDebounce b3(6, 100);


void b1Callback(int state)
{
  if(state)
  {
    Serial.println("Button 1"); 
  }
}

void b2Callback(int state)
{
  if(state)
  {
    Serial.println("Button 2"); 
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
}

void loop() 
{
  b1.update();
  b2.update();
  b3.update();
}
