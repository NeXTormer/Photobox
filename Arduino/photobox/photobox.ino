#include <ButtonDebounce.h>

const int redLED[] = { 6, 12 };
const int greenLED[] = { 2, 13 };

ButtonDebounce b1(3, 100);
ButtonDebounce b2(4, 100);
ButtonDebounce b3(5, 100);


bool ready = false;

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

  pinMode(redLED[0], OUTPUT);
  pinMode(redLED[1], OUTPUT);
  pinMode(greenLED[0], OUTPUT);
  pinMode(greenLED[1], OUTPUT);

}

void loop() 
{
  b1.update();
  b2.update();
  b3.update();

  digitalWrite(redLED[0], !ready);
  digitalWrite(redLED[1], !ready);
  digitalWrite(greenLED[0], ready);
  digitalWrite(greenLED[1], ready);

  if(Serial.available())
  {
    int data = Serial.read();
    ready = data;
  }
}
