#include <SoftwareSerial.h>

// Swap RX/TX connections on bluetooth chip
//   Pin 10 --> Bluetooth TX
//   Pin 11 --> Bluetooth RX
SoftwareSerial mySerial(2, 3); // RX, TX

void setup()
{
  Serial.begin(115200);
  while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB port only
  }

  Serial.println("Init complete!");
  mySerial.begin(115200);
  Serial.write("AT+BOUD4"); // set to 9600
   
  mySerial.write("AT+BOUD4"); // make sure it is set to 9600
  delay(1000);
  while (mySerial.available()) {
      Serial.write(mySerial.read());
   }
}
void loop() {}
