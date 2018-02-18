void setup() {
  // put your setup code here, to run once:
  pinMode(7, INPUT);
  pinMode(8, INPUT);

  Serial.begin(9600);
}

bool last7 = true;
bool last8 = true;

void loop() {

  bool current7 = digitalRead(7);
  bool current8 = digitalRead(8);

  if(last7 != current7)
  {
    Serial.println("7");
  }
  if(last8 != current8)
  {
    Serial.println("8");
  }

  last8 = current8;
  last7 = current7;
  
}
