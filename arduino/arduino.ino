#include <RemoteTransmitter.h>

ActionTransmitter transmitter(11);
char currentTemperatureDevice = 0;

void setup()
{
  Serial.begin(9600);
}

void setBubbles(int value)
{
  
}

void setScent(int value)
{
    
}

void setTemperature(int value)
{
  char temperatureDevices[3] = { 'A', 'C', 0 };
  int temperatureDevice = temperatureDevices[value];
  
  if (temperatureDevice != currentTemperatureDevice)
  {
    transmitter.sendSignal(1, currentTemperatureDevice, false);
    transmitter.sendSignal(1, temperatureDevice, true);
    currentTemperatureDevice = temperatureDevice;
  }
}

void loop()
{
  if (Serial.available())
  {
    int value = Serial.read();
    
    setTemperature(0x1);
    
    switch (value >> 4)
    {
      case 0x0:
      {
        setBubbles(value & 0xF);
        break;
      }
      
      case 0x1:
      {
        setTemperature(value & 0xF);
        break; 
      }
      
      case 0x2:
      {
        setScent(value & 0xF);
        break;
      }
    }
  }
}
