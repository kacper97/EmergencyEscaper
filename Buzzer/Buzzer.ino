char cache;
String inputString="";
//Specify digital pin on the Arduino that the positive lead of piezo buzzer is attached.
int piezoPin = 9;
void setup()
{
Serial.begin(9600);
pinMode(9,OUTPUT);
}

void loop()
{
if(Serial.available())
{
while(Serial.available())
{
char inChar = (char)Serial.read();
inputString += inChar;
}
while (Serial.available()>0)
{
cache = Serial.read();
}
if(inputString == "f")
{
  tone(piezoPin, 5000, 500);
}
else if(inputString == "b")
{
  noTone(piezoPin);
}
inputString = "";
}
}
