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
    }

}
*/
