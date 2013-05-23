void setup()
{
  Serial.begin(9600);
  pinMode(8, OUTPUT);
}

void loop()
{
  if (Serial.available())
  {
    int value = Serial.read();
    
    if (value == 'M')
      digitalWrite(8, HIGH);
  }
}
