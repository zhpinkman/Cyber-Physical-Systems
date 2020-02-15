// constants won't change. They're used here to set pin numbers:
const int startStopButtonPin = 2;     // the number of the pushbutton pin
const int incSpeedButtonPin = 3;     // the number of the pushbutton pin
const int decSpeedButtonPin = 4;     // the number of the pushbutton pin
const int chDirectionButtonPin = 5;     // the number of the pushbutton pin

const int motorPin1 = 10;     // the number of the pushbutton pin
const int motorPin2 = 11;     // the number of the pushbutton pin

const int ledPin =  13;      // the number of the LED pin

// variables will change:
int buttonState = 0;         // variable for reading the pushbutton status

void setup() {
  // initialize the LED pin as an output:
  pinMode(ledPin, OUTPUT);
  // initialize the pushbutton pin as an input:
  pinMode(startStopButtonPin, INPUT);
  pinMode(incSpeedButtonPin, INPUT);
  pinMode(decSpeedButtonPin, INPUT);
  pinMode(chDirectionButtonPin, INPUT);
  Serial.begin(9600);
}

int pwm = 255;
int pwmCounter = 0;
const int pwmMax = 255;
bool clockwise = true;
int startStopButtonState = 0;
int incSpeedButtonState = 0;
int decSpeedButtonState = 0;
int chDirectionButtonState = 0;
  
void loop() {
  pwmCounter = (pwmCounter + 1) % pwmMax;
//  Serial.println(pwmCounter);

  // read the state of the pushbutton value:
  startStopButtonState = digitalRead(startStopButtonPin);
  incSpeedButtonState = digitalRead(incSpeedButtonPin);
  decSpeedButtonState = digitalRead(decSpeedButtonPin);
  chDirectionButtonState = digitalRead(chDirectionButtonPin);
  Serial.println(startStopButtonState);
  // check if the pushbutton is pressed. If it is, the buttonState is HIGH:
  if (startStopButtonState == HIGH) {
    digitalWrite(ledPin, HIGH);
    if (pwmCounter < pwm) {
      if(clockwise){
        digitalWrite(motorPin1, HIGH);
        digitalWrite(motorPin2, LOW);
      }else{
        digitalWrite(motorPin1, LOW);
        digitalWrite(motorPin2, HIGH);
      }
    } else {
      digitalWrite(motorPin1, LOW);
      digitalWrite(motorPin2, LOW);
    }
  } else {
    digitalWrite(ledPin, LOW);
  }

  if (incSpeedButtonState == HIGH) {
    pwm++;
    if(pwm > pwmMax)
      pwm = pwmMax;
  }
  if (decSpeedButtonState == HIGH) {
    pwm--;
    if(pwm < 0)
      pwm = 0;
  }
  if (chDirectionButtonState == HIGH) {
    clockwise = true;
  }else{
    clockwise = false;
  }

}
