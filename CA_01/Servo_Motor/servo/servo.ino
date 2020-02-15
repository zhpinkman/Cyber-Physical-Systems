#include <Servo.h>

Servo myservo;  // create servo object to control a servo
// twelve servo objects can be created on most boards

int pos = 0;    // variable to store the servo position

// constants won't change. They're used here to set pin numbers:
const int incDegreeButtonPin = 2;     // the number of the pushbutton pin
const int decDegreeButtonPin = 3;     // the number of the pushbutton pin

const int motorSignalPin = 10;     // the number of the pushbutton pin
const int ledPin =  13;      // the number of the LED pin

// variables will change:
int buttonState = 0;         // variable for reading the pushbutton status
int incDegreeButtonState = 0;
int decDegreeButtonState = 0;

void setup() {
  myservo.attach(motorSignalPin, 1000, 2000);  // attaches the servo on pin 10 to the servo object (1ms to 2ms PWM is 0 to 180)
  // initialize the LED pin as an output:
  pinMode(ledPin, OUTPUT);
  pinMode(incDegreeButtonPin, INPUT);
  pinMode(decDegreeButtonPin, INPUT);
  Serial.begin(9600);
}
  
void loop() {
  // read the state of the pushbutton value:
  incDegreeButtonState = digitalRead(incDegreeButtonPin);
  decDegreeButtonState = digitalRead(decDegreeButtonPin);

  // check if the pushbutton is pressed. If it is, the buttonState is LOW:
  if (incDegreeButtonState == LOW) {
    Serial.println("inc");
    while(digitalRead(incDegreeButtonPin) == LOW){}
    pos = pos + 10;
    if(pos > 180)
      pos = 180;
    myservo.write(pos);
    Serial.println(pos);
  }
  if (decDegreeButtonState == LOW) {
    Serial.println("dec");
    while(digitalRead(decDegreeButtonPin) == LOW){}
    pos = pos - 10;
    if(pos < 0)
      pos = 0;
    myservo.write(pos);
    Serial.println(pos);
  }

}
