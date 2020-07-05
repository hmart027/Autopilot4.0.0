package autopilot;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import gnu.io.*;

public class Comm {

	private boolean state = false;
	private SerialPort serialPort = null; // Serial port object
	private TextArea sp = new TextArea();
	public boolean dataRdy = false;

	private byte[] buffer = new byte[300];
	private int byteNum = 0;
	private String bufferS = null;

	public Comm() {
		sp.addText("Log:\n No Connection \n");
	}

	public String getPort() {
		String portName = null;
		if (serialPort != null) {
			portName = serialPort.getName();
			portName = portName.substring(4, portName.length());
		}
		return portName;
	}

	// Get list of serial ports
	@SuppressWarnings("rawtypes")
	public ArrayList<String> serialportList() {

		// Array containing the name of the ports
		ArrayList<String> list = new ArrayList<String>();

		// List of all ports
		Enumeration portList = CommPortIdentifier.getPortIdentifiers();

		while (portList.hasMoreElements()) {

			CommPortIdentifier portId = (CommPortIdentifier) portList
					.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				list.add(portId.getName()); // If the port is a serial port add
											// to list
			}
		}
		return list;
	}

	// Returns the value of Status
	public boolean getState() {
		return state;
	}

	// Disposes the opened port
	public void closeComm() {
		state = false;
		if (serialPort != null)
			serialPort.close();
		sp.addText("\n No Connection \n");
	}

	// Without a explicit baudrate
	public boolean getComm(String port) {

		if (serialPort != null)
			serialPort.close();

		CommPortIdentifier portId; // Identifier for ports

		try {

			// Get the serial port ID
			portId = CommPortIdentifier.getPortIdentifier(port);

			// Open and get ownership of the port
			serialPort = (SerialPort) portId.open("SimpleWriteApp", 1000);

			// Setting Parameters
			serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			state = true;
			sp.addText("\n Connected to: " + getPort() + "\n");
		} catch (NoSuchPortException e1) {
			e1.printStackTrace();

		} catch (PortInUseException e) {
			System.out.println("Port in use");

		} catch (UnsupportedCommOperationException e) {
			e.printStackTrace();
		}
		return state;
	}

	// With a explicit baudrate
	public boolean getComm(String port, int baudrate) {

		if (serialPort != null)
			serialPort.close();

		CommPortIdentifier portId; // Identifier for ports

		try {

			// Get the serial port ID
			portId = CommPortIdentifier.getPortIdentifier(port);

			// Open and get ownership of the port
			serialPort = (SerialPort) portId.open("Autopilot", 1000);

			// Setting Parameters
			serialPort.setSerialPortParams(baudrate, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			state = true;
			sp.addText("\n Connected to: " + getPort() + "\n");

		} catch (NoSuchPortException e1) {
			e1.printStackTrace();

		} catch (PortInUseException e) {
			System.out.println("Port in use");

		} catch (UnsupportedCommOperationException e) {
			e.printStackTrace();
		}
		return state;
	}

	// Method to send information to client
	public void sendInfo(int cmd) {

		// Dont send if there is no connection
		if (state) {
			try {

				// Create the output streams
				OutputStream out = serialPort.getOutputStream();

				// Sending txt to client
				out.write(cmd);

			} catch (IOException e) {
				System.out.println("IO Exception");
			}
		}
	}

	public boolean sendByteArray(byte[] data) {
		Sleeping sleep=new Sleeping();
		// Dont send if there is no connection
		if (state) {
			try {

				// Create the output streams
				OutputStream out = serialPort.getOutputStream();

				// Sending txt to client
				for(byte i: data){
					out.write(i);
					sleep.sleepFor(30); //Allow for processing time
					}
				return true;
			} catch (IOException e) {
				System.out.println("IO Exception");
			}
		}
		return false;
	}
	
	private class Sleeping extends Thread{
		public void sleepFor(int mili){
			try {
				sleep(mili);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean sendString(String data) {
		sp.addText(data + "\n");
		if (state) {
			try {
				// Create the output streams
				OutputStream output = serialPort.getOutputStream();

				// Sending txt to client
				output.write(data.getBytes());
				return true;
			} catch (IOException e) {
				System.out.println("IO Exception");
			}
		}
		return false;
	}

	// Method to receive information from client
	public String getStringInfo() {
		if (dataRdy) {
			return bufferS;
		}
		return null;
	}

	// Method to receive information from client
	public byte[] getByteInfo() {
		byte[] out = new byte[byteNum];
		if (dataRdy) {
			for (int i = 0; i < byteNum; i++) {
				out[i] = buffer[i];
			}
			return out;
		}
		return null;
	}

	public TextArea getTextArea() {
		return sp;
	}

	public InputStream getInputStream() {
		try {
			return serialPort.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}