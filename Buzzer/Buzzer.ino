char cache;
String inputString="";
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
digitalWrite(9,HIGH);  
}
else if(inputString == "b")
{
digitalWrite(9,LOW);
}
inputString = "";
}
}
