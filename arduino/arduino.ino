#include <RemoteTransmitter.h>

ActionTransmitter transmitter(11);
char currentTemperatureDevice = 0;

void setup()
{
  Serial.begin(9600);
}

void sprayScent(int pin)
{
  // spray scent connected on pin
}

void setDeviceState(char c, boolean state)
{
  transmitter.sendSignal(1, c, state);
}

void loop()
{
  if (Serial.available())
  {
    int value = Serial.read();
    
    int opcode = value >> 4;
    int param = value & 0xF;
    
    switch (opcode)
    {
      case 0x0:
      {
        Serial.write(0);
        break;
      }
      
      case 0x1:
      {
        sprayScent(param);
        break; 
      }
      
      case 0x2:
      {
        setDeviceState(param + 'A', false);
        break;
      }
      
      case 0x3:
      {
        setDeviceState(param + 'A', true);
        break;
      }
    }
  }
}
