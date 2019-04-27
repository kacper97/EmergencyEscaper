
int buzzerPin=9; //buzzer is on pin 9
int sensorPin=7; // sensor 
boolean val =0; // boolean set to off

void setup(){
  pinMode(buzzerPin, OUTPUT);
  pinMode(sensorPin, INPUT);
  Serial.begin (9600);
}
  
void loop (){
  val =digitalRead(sensorPin);
  Serial.println (val);
  // when the sensor detects a signal above the threshold value, Buzzer sounds
  if (val==HIGH) {
      tone(buzzerPin, 1000, 10000000);
  }
  else {
   noTone(buzzerPin);
  }
}
