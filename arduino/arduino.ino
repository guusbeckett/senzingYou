void sendTelegram(unsigned long data, unsigned short pin)
{
  unsigned int periodusec = (unsigned long)data >> 23;
  unsigned short repeats = 1 << (((unsigned long)data >> 20) & B111);
  data = data & 0xfffff; //truncate to 20 bit
  
  //Convert the base3-code to base4, to avoid lengthy calculations when transmitting.. Messes op timings.
  unsigned long dataBase4 = 0;
  
  for (unsigned short i=0; i<12; i++)
  {
    dataBase4<<=2;
    dataBase4|=(data%3);
    data/=3;
  }
  
  for (unsigned short int j=0;j<repeats;j++)
  {		
  //Sent one telegram		
  
  //Use data-var as working var
  data=dataBase4;
  for (unsigned short i=0; i<12; i++)
  {
    switch (data & B11)
    {
    case 0:
      digitalWrite(pin, HIGH);
      delayMicroseconds(periodusec);
      digitalWrite(pin, LOW);
      delayMicroseconds(periodusec*3);
      digitalWrite(pin, HIGH);
      delayMicroseconds(periodusec);
      digitalWrite(pin, LOW);
      delayMicroseconds(periodusec*3);
    break;
    case 1:
      digitalWrite(pin, HIGH);
      delayMicroseconds(periodusec*3);
      digitalWrite(pin, LOW);
      delayMicroseconds(periodusec);
      digitalWrite(pin, HIGH);
      delayMicroseconds(periodusec*3);
      digitalWrite(pin, LOW);
      delayMicroseconds(periodusec);
    break;
    case 2: //AKA: X or float
      digitalWrite(pin, HIGH);
      delayMicroseconds(periodusec);
      digitalWrite(pin, LOW);
      delayMicroseconds(periodusec*3);
      digitalWrite(pin, HIGH);
      delayMicroseconds(periodusec*3);
      digitalWrite(pin, LOW);
      delayMicroseconds(periodusec);
    break;
    }
    //Next trit
    data>>=2;
  }
  
  //Send termination/synchronisation-signal. Total length: 32 periods
  digitalWrite(pin, HIGH);
  delayMicroseconds(periodusec);
  digitalWrite(pin, LOW);
  delayMicroseconds(periodusec*31);
  }
}

unsigned long encodeTelegram(unsigned short trits[])
{
  unsigned long data = 0;
  
  //Encode data
  for (unsigned short i=0;i<12;i++)
  {
    data*=3;
    data+=trits[i];
  }
  
  //Encode period duration
  data |= (unsigned long)190 << 23;
  
  //Encode repeats
  data |= (unsigned long)3 << 20;
  
  return data;
}

unsigned long getTelegram(char device, boolean state)
{
  unsigned short systemCode = 1;
  unsigned short trits[12];
  
  device-=65;
  
  for (unsigned short i=0; i<5; i++)
  {
    //bits 0-4 contain address (2^5=32 addresses)
    trits[i]=(systemCode & 1)?1:2;          
    systemCode>>=1;
    
    //bits 5-9 contain device. Only one trit has value 0, others have 2 (float)!
    trits[i+5]=(i==device?0:2);
  }
  
  //switch on or off
  trits[10]=(!state?0:2);
  trits[11]=(state?0:2);
  
  return encodeTelegram(trits);
}

void setDeviceState(unsigned short pin, char device, boolean state)
{
  sendTelegram(getTelegram(device, state), pin);
}

void setup()
{
  Serial.begin(9600);
  pinMode(11, OUTPUT);
}

void loop()
{
  Serial.print("test");
  if (Serial.available())
  {
    int value = Serial.read();
    
    if (value == 'M')
    {
      setDeviceState(11, 'A', true);
      digitalWrite(8, HIGH);
    }
  }
}
