#include <Arduino.h>
#include <LiquidCrystal.h>
#include <AltSoftSerial.h>

#define BLUETOOTH_TEMPERATURE_START_CHAR '#'
#define BLUETOOTH_HUMIDITY_START_CHAR '*'
#define LUX_BOARD_BAUD_RATE 9600
#define TH_BOARD_BAUD_RATE 9600
enum BluetoothState {Waiting = 0, GetTemperature = 1, GetHumidity = 2};
// initialize the library by associating any needed LCD interface pin
// with the arduino pin number it is connected to
const int rs = 12, en = 11, d4 = 5, d5 = 4, d6 = 3, d7 = 2;
LiquidCrystal lcd(rs, en, d4, d5, d6, d7);
AltSoftSerial luxAltSoftSerial;
// AltSoftSerial always uses these pins:
//
// Board          Transmit  Receive   PWM Unusable
// -----          --------  -------   ------------
// Arduino Uno        9         8         10
// Arduino Leonardo   5        13       (none)
// Arduino Mega      46        48       44, 45

void handleSerialBluetooth();
void handleLux();
void printLCD();

float temperature, humidity, lux;
int bluetoothState = 0;
bool changeInSensors = false;

void setup() {
  lcd.begin(20, 4);            // set up the LCD's number of columns and rows:
  Serial.begin(TH_BOARD_BAUD_RATE);            // start serial communication at 9600bps for bluetooth
  luxAltSoftSerial.begin(LUX_BOARD_BAUD_RATE); // start serial communication at 9600bps for lux sensor board
}

void loop() {
  // put your main code here, to run repeatedly:
  // reply only when you receive data:
  if (Serial.available() >= 5) {  // First Char + 4 Bytes Float = 5 Bytes
    handleSerialBluetooth();
  }
  if(luxAltSoftSerial.available() >= 4){  // 4 Bytes Float
    Serial.println("s");
    handleLux();
  }
  if(changeInSensors){
    printLCD();
    changeInSensors = false;
  }
}

void handleSerialBluetooth(){
  if(bluetoothState == Waiting){
    // read the incoming byte:
    char incomingByteChar = Serial.read();
    if(incomingByteChar == BLUETOOTH_TEMPERATURE_START_CHAR)
      bluetoothState = GetTemperature;
    if(incomingByteChar == BLUETOOTH_HUMIDITY_START_CHAR)
      bluetoothState = GetHumidity;
  }
  if(bluetoothState == GetTemperature){
    temperature = Serial.parseFloat();
    bluetoothState = Waiting;
    changeInSensors = true;
  }
  if(bluetoothState == GetHumidity){
    humidity = Serial.parseFloat();
    bluetoothState = Waiting;
    changeInSensors = true;
  }
}

void handleLux(){
  lux = luxAltSoftSerial.parseFloat();
  Serial.println(lux);
  changeInSensors = true;
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
  lcd.setCursor(0, 1);
  lcd.println(String(lux).c_str());
}