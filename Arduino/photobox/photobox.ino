#include <ButtonDebounce.h>

ButtonDebounce b1(2, 100);
ButtonDebounce b2(3, 100);
ButtonDebounce b3(4, 100);


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
  }
}

void b3Callback(int state)
{
  if(state)
  {
    Serial.print("n"); 
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
