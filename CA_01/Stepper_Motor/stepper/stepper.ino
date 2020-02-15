#include <Stepper.h>

const int stepsPerRevolution = 110;  // change this to fit the number of steps per revolution
// for your motor


// initialize the stepper library on pins 8 through 11:
Stepper myStepper(stepsPerRevolution, 8, 9, 10, 11);

int stepCount = 0;  // number of steps the motor has taken


// constants won't change. They're used here to set pin numbers:
const int clockwiseButtonPin = 2;     // the number of the pushbutton pin
const int counterClockwiseButtonPin = 3;     // the number of the pushbutton pin
const int stopButtonPin = 4;     // the number of the pushbutton pin

// variables will change:
int clockwiseButtonState = 0;
int counterClockwiseButtonState = 0;
int stopButtonState = 0;
int rotationDirection = -1; //CLOCKWISE
bool start = true;
void setup() {
  pinMode(clockwiseButtonPin, INPUT);
  pinMode(counterClockwiseButtonPin, INPUT);
  pinMode(stopButtonPin, INPUT);
  Serial.begin(9600);
}
  
void loop() {
  if(start){
    myStepper.setSpeed(100); // 0-100
    myStepper.step(rotationDirection * (stepsPerRevolution / 100));
  }
  
  // read the state of the pushbutton value:
  clockwiseButtonState = digitalRead(clockwiseButtonPin);
  counterClockwiseButtonState = digitalRead(counterClockwiseButtonPin);
  stopButtonState = digitalRead(stopButtonPin);

  // check if the pushbutton is pressed. If it is, the buttonState is LOW:
  if (clockwiseButtonState == LOW) {
    Serial.println("Clockwise");
    while(digitalRead(clockwiseButtonPin) == LOW){}
    rotationDirection = +1; //CLOCKWISE
    start = true;
    Serial.println(rotationDirection);
  }
  if (counterClockwiseButtonState == LOW) {
    Serial.println("Clockwise");
    while(digitalRead(counterClockwiseButtonPin) == LOW){}
    rotationDirection = -1; //CLOCKWISE
    start = true;
    Serial.println(rotationDirection);
  }
  if (stopButtonState == LOW) {
    Serial.println("Stop");
    while(digitalRead(stopButtonPin) == LOW){}
    start = false;
    Serial.println(start);
  }
}
