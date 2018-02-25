#define buttoncount 3

const int buttons[3] = { 4, 5, 6 };
const int ledPin = 7;


unsigned long lastDebounceTime[buttoncount];
unsigned long debounceDelay = 50;

int ledState = HIGH;

int buttonState[buttoncount];
int lastButtonState[buttoncount];

void setup() 
{
  Serial.begin(9600);

  for(int i = 0; i < buttoncount; i++)
  {
        lastButtonState[i] = LOW;
        lastDebounceTime[i] = 0;

        pinMode(buttons[i], INPUT);
  }
  
  pinMode(ledPin, OUTPUT);

  digitalWrite(ledPin, ledState);
}

void loop() 
{
  for(int i = 0; i < buttoncount; i++)
  {
    int reading = digitalRead(buttons[i]);
    if (reading != lastButtonState[i]) 
    {
      lastDebounceTime[i] = millis();
    }

    if ((millis() - lastDebounceTime[i]) > debounceDelay) 
    {
      if (reading != buttonState[i]) 
      {
        buttonState[i] = reading;
        if (buttonState[i] == HIGH) 
        {
          //button pressed
          Serial.print(i);
          ledState = !ledState;
        }
      }
    }
    
    digitalWrite(ledPin, ledState);
    
    lastButtonState[i] = reading;
  }
}
