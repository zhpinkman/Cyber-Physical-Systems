#include <Arduino.h>
#include <LiquidCrystal.h>

#define BLUETOOTH_TEMPERATURE_START_CHAR '#'
#define BLUETOOTH_HUMIDITY_START_CHAR '*'
enum BluetoothState {Waiting = 0, GetTemperature = 1, GetHumidity = 2};

void handleSerialBluetooth();
void printLCD();

// initialize the library by associating any needed LCD interface pin
// with the arduino pin number it is connected to
const int rs = 12, en = 11, d4 = 5, d5 = 4, d6 = 3, d7 = 2;
LiquidCrystal lcd(rs, en, d4, d5, d6, d7);

float temperature, humidity;
int bluetoothState = 0;

void setup() {
  lcd.begin(20, 4);            // set up the LCD's number of columns and rows:
  Serial.begin(9600);          // start serial communication at 9600bps for bluetooth
}

void loop() {
  // put your main code here, to run repeatedly:
  // reply only when you receive data:
  if (Serial.available() > 0) {
    handleSerialBluetooth();
  }
  printLCD();
}

void handleSerialBluetooth(){
  if(bluetoothState == Waiting){
    // read the incoming byte:
    char incomingByteChar = Serial.read();
    if(incomingByteChar == BLUETOOTH_TEMPERATURE_START_CHAR)
      bluetoothState = GetTemperature;
    if(incomingByteChar == BLUETOOTH_HUMIDITY_START_CHAR)
      bluetoothState = GetHumidity;
  }else if(bluetoothState == GetTemperature){
    temperature = Serial.parseFloat();
    bluetoothState = Waiting;
  }else if(bluetoothState == GetHumidity){
    humidity = Serial.parseFloat();
    bluetoothState = Waiting;
  }
}

/*
  The circuit:
 * LCD RS pin to digital pin 12
 * LCD Enable pin to digital pin 11
 * LCD D4 pin to digital pin 5
 * LCD D5 pin to digital pin 4
 * LCD D6 pin to digital pin 3
 * LCD D7 pin to digital pin 2
 * LCD R/W pin to ground
 * LCD VSS pin to ground
 * LCD VCC pin to 5V
 * 10K resistor:
 * ends to +5V and ground
 * wiper to LCD VO pin (pin 3)
 * 
 * */
void printLCD(){
  lcd.setCursor(0, 0);
  lcd.println(String(temperature).c_str());
  lcd.println(String(humidity).c_str());
}