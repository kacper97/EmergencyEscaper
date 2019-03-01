package org.wit.emergencyescape.arduino;
//Src
//http://www.drdobbs.com/jvm/control-an-arduino-from-java/240163864
/*
public class Arduino {

    private static final String PORT_NAMES[] = {
            "/dev/tty.usbmodem", // Mac OS X
            "/dev/usbdev", // Linux
            "/dev/tty", // Linux
            "/dev/serial", // Linux
            "COM3", // Windows
    };

    // Enumerate system ports and try connecting to Arduino over each
while (portId == null && portEnum.hasMoreElements()) {
        CommPortIdentifier currPortId =
                (CommPortIdentifier) portEnum.nextElement();
        for (String portName : PORT_NAMES) {
            if ( currPortId.getName().equals(portName)
                    || currPortId.getName().startsWith(portName))
            {
                // Try to connect to the Arduino on this port
                serialPort = (SerialPort)currPortId.open(appName, TIME_OUT);
                portId = currPortId;
                break;
            }
        }


    //If it finds a match, it will break out of the for and while loops,
   // and then connect on that port and configure it:
// set port parameters
serialPort.setSerialPortParams(
                DATA_RATE, // 9600 baud
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);


                The last step in initialization is to add an event listener (more on this later) to receive events from the Arduino,
                and tell it to call us back when there's data available:
// add event listeners
serialPort.addEventListener(this);
serialPort.notifyOnDataAvailable(true);



int buzzer = 13; // LED connected to digital pin 13
int soundsensor = 2;  // Powertail Switch 2 connected to digital pin 2
int bluetooth = 10; // BLuetooth module connected to pin 10
int recv = 0; // byte received on the serial port

void setup() {
  // initialize onboard Buzzer (buzzer), Soundsensor (soundsensor) and serial port
  pinMode(buzzer, OUTPUT);
  pinMode(soundsensor, OUTPUT);
   pinMode(bluetooth, OUTPUT);
  Serial.begin(9600);
}

void loop()
{
  // if serial port is available, read incoming bytes
  if (Serial.available() > 0) {
    recv = Serial.read();

    // if 'y' (decimal 121) is received, turn buzzer/soundsensor/bluetooth on
    // anything other than 121 is received, turn buzzer/soundsensor/bluetooth off
    if (recv == 121){
      digitalWrite(buzzer, HIGH);
      digitalWrite(soundsensor,LOW);
      digitalWrite(bluetooth, HIGH);
    } else {
      digitalWrite(buzzer, LOW);
      digitalWrite(soundsensor,HIGH);
      digitalWrite(bluetooth, LOW);
    }

    // confirm values received in serial monitor window
    Serial.print("--Arduino received: ");
    Serial.println(recv);
      }
    }
    }

String data = "y";
output = serialPort.getOutputStream();
output.write( data.getBytes() );


//The following code receives and processes these and other events (this method is part of the
// RXTX SerialPortEventListener interface, which is provided as a listener in the initialization code):
public synchronized void serialEvent(SerialPortEvent oEvent) {
    try {
        switch (oEvent.getEventType() ) {
            case SerialPortEvent.DATA_AVAILABLE:
                if ( input == null ) {
                    input = new BufferedReader(
                        new InputStreamReader(
                                serialPort.getInputStream()));
                }
                String inputLine = input.readLine();
                System.out.println(inputLine);
                break;

            default:
                break;
        }
    }
    catch (Exception e) {
        System.err.println(e.toString());
    }
}

if ( serialPort != null ) {
    serialPort.removeEventListener();
    serialPort.close();
}



}
*/
