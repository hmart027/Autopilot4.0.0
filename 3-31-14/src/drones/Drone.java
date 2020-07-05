package drones;

import mavlink.MAVLinkTools;
import mavlink.MAVLinkEnum.*;

public class Drone extends MAVLinkTools{
	// Intrinsic Values
	private int systemID = 1;
	private int componentID = 0;
	private MAV_AUTOPILOT autopilot;
	private MAV_TYPE type;
	private MAV_MODE_FLAG modeFlag;
	private MAV_STATE status;
	private boolean updated=false;
	private boolean connected=false;

	// System sensors
		// Accelerometer
	private int xacc = 0;
	private int yacc = 0;
	private int zacc = 0;
		// Gyroscope
	private int xgyro = 0;
	private int ygyro = 0;
	private int zgyro = 0;
		// Compass
	private int xmag = 0;
	private int ymag = 0;
	private int zmag = 0;

	// Attitude
	private float roll = 0;
	private float pitch = 0;
	private float yaw = 0;
	private float rollspeed = 0;
	private float pitchspeed = 0;
	private float yawspeed = 0;
	
	// VFR_HUD values
	private float airspeed	  = 0;	//  Current airspeed in m/s
	private float groundspeed = 0;	//	Current ground speed in m/s
	private float alt		  = 0;	//	Current altitude (MSL), in meters
	private float climb		  = 0;	//	Current climb rate in meters/second
	private float heading	  = 0;	//	Current heading in degrees, in compass units (0..360, 0=north)
	private float throttle	  = 0;	//  Current throttle setting in integer percent, 0 to 100
	
	// PowerSupply
		//Levels
	private int batteryLvl=0;
	private int fiveLvl=0;
	private int twelveLvl=0;
	private int current=0;
	private int chargeLvl=0;
	private byte systemsStat=0;
		//Thresholds
	private int lvl1TH=0; 
	private int lvl2TH=0; 
	private int lvl3TH=0; 
	private int lvl4TH=0; 
	private int lvl5TH=0; 
	private int lvl6TH=0; 
	private int lvl7TH=0;

	// Constructor
	public Drone() {
		this.systemID = 1;
		this.componentID = 1;
		new ConnectionCheck().start();
	}
	public Drone(int sID, int cID) {
		this.systemID = sID;
		this.componentID = cID;
		new ConnectionCheck().start();
	}

	// ID values
	public int getSystemID() {
		return this.systemID;
	}
	public int getComponentID() {
		return this.componentID;
	}

	// Name
	public MAV_AUTOPILOT getAutopilot() {
		return this.autopilot;
	}

	// Type
	public MAV_TYPE getType() {
		return this.type;
	}

	// Flags
	public MAV_MODE_FLAG getModeFlag() {
		return this.modeFlag;
	}

	// Status
	public MAV_STATE getStatus() {
		return this.status;
	}
	
	public boolean isConnected(){
		return connected;
	}

	// Sensors
		// Accelerometer
	public int getXacc(){
		return this.xacc;
	}
	public int getYacc(){
		return this.yacc;
	}
	public int getZacc(){
		return this.zacc;
	}
		// Gyroscope
	public int getXgyro(){
		return this.xgyro;
	}
	public int getYgyro(){
		return this.ygyro;
	}
	public int getZgyro(){
		return this.zgyro;
	}
		// Compass
	public int getXmag(){
		return this.xmag;
	}
	public int getYmag(){
		return this.ymag;
	}
	public int getZmag(){
		return this.zmag;
	}

	// Attitude
		// Roll
	public float getRoll(){
		return this.roll;
	}
	public float getRollSpeed(){
		return this.rollspeed;
	}
		// Pitch
	public float getPitch(){
		return this.pitch;
	}
	public float getPitchSpeed(){
		return this.pitchspeed;
	}
		// Yaw
	public float getYaw(){
		return this.yaw;
	}
	public float getYawSpeed(){
		return this.yawspeed;
	}
	
	// VFR_HUD values
	/**
	 * Current airspeed in m/s
	 */
	public float getAirspeed(){
		return airspeed;
	}
	/**
	 * Current ground speed in m/s
	 */
	public float getGroundspeed(){
		return groundspeed;
	}
	/**
	 * Current altitude (MSL), in meters
	 */
	public float getAltitude(){
		return alt;
	}
	/**
	 * Current climb rate in meters/second
	 */
	public float getClimbRate(){
		return climb;
	}
	/**
	 * Current heading in degrees, in compass units (0..360, 0=north)
	 */
	public float getHeading(){
		return this.heading;
	}
	/**
	 * Current throttle setting in integer percent, 0 to 100
	 */
	public float getThrottle(){
		return throttle;
	}
	
	// PowerSupply
	public float getBatteryLvl(){
		return (((float)batteryLvl)*20/255);
	}
	public float getFiveLvl(){
		return (((float)fiveLvl)*10/255);
	}
	public float getTwelveLvl(){
		return (((float)twelveLvl)*20/255);
	}
	public float getCurrent(){
		if((systemsStat&0x01)!=0)
			return (-1)*(current*20/255);
		return (current*20/255);
	}
	public int getChargeLvl(){
		return chargeLvl*100/255;
	}
	public boolean isSystemON(int systemNumber){
		byte mask=(byte)Math.pow(2, systemNumber);
		if(systemNumber==0)return false;
		if((systemsStat&mask)!=0)
			return true;
		return false;
	}
	public int getPowerThresholds(int lvl){
		switch(lvl){
		case 1:
			return lvl1TH*100/255;
		case 2:
			return lvl2TH*100/255;
		case 3:
			return lvl3TH*100/255;
		case 4:
			return lvl4TH*100/255;
		case 5:
			return lvl5TH*100/255;
		case 6:
			return lvl6TH*100/255;
		case 7:
			return lvl7TH*100/255;
		default:
			return -1;
		}
	}
	
	/**
	 * The Array must contain first the data rate, the required stream, srart/stop.
	 * @param param
	 * @return
	 */
	public byte[] requestDataStream(byte[] param){
//		req_message_rate	uint16_t	The requested interval between two messages of this type
//		target_system		uint8_t		The target requested to send the message stream.
//		target_component	uint8_t		The target requested to send the message stream.
//		req_stream_id		uint8_t		The ID of the requested data stream
//		start_stop			uint8_t		1 to start sending, 0 to stop sending.
		return new byte[]{ (byte) (param[0]), (byte) (param[0]>>8), (byte) this.systemID, (byte) this.componentID, param[1], param[2]};
	}
	
	// Command to send
	public byte[] sendCommand(int cmdID,byte[] parameters){
		switch(cmdID){
		case 66:
			System.out.println("Sent stream request");
			return requestDataStream(parameters);
		case 69:
			System.out.println();
			return MANUAL_CONTROL(parameters);
		case 182:
			return requestTH();
		case 183:
			return setTH(parameters);
		case 185:
			return setCompID(parameters);
		default:
			return null;
		}
	}
	
	// Commands to frame
	public void receivedCommand(int cmdID,byte[] data){
		switch (cmdID) {
		case 0:
			HEARTBEAT(data);
			break;
		case 1:
			SYS_STATUS(data);
			break;
		case 2:
			SYSTEM_TIME(data);
			break;
		case 24:
			GPS_RAW_INT(data);
			break;
		case 27:
			RAW_IMU(data);
			break;
		case 29:
			SCALED_PRESSURE(data);
			break;
		case 30:
			ATTITUDE(data);
			break;
		case 33:
			GLOBAL_POSITION_INT(data);
			break;
		case 35:
			RC_CHANNELS_RAW(data);
			break;
		case 36:
			SERVO_OUTPUT_RAW(data);
			break;
		case 42:
			MISSION_CURRENT(data);
			break;
		case 62:
			NAV_CONTROLLER_OUTPUT(data);
			break;
		case 74:
			VFR_HUD(data);
			break;
		case 181:
			powerSupply(data);
			break;
		case 184:
			powerThresholds(data);
			break;	
		default:
			//System.out.println("CMDID: " + cmdID);
			return;
		}
	}
	
/**
 * Methods to extract the data from the payloads according to the msgID.
 */
	
	/** ID#0
	 * 	The heartbeat message shows that a system is present and responding. The type of the MAV and Autopilot hardware 
	 * 	allow the receiving system to treat further messages from this system appropriate (e.g. by laying out the user 
	 * 	interface based on the autopilot).
	 */
	private void HEARTBEAT(byte[]data){
		System.out.println("Heartbeat");
		this.updated=true;
		int index = 0;
		index+=4;
		this.type		= MAV_TYPE.get(data[index++]&0x00FF);
		this.autopilot	= MAV_AUTOPILOT.get(data[index++]&0x00FF);
		this.modeFlag	= MAV_MODE_FLAG.get(data[index++]&0x00FF);
		this.status		= MAV_STATE.get(data[index++]&0x00FF);		
	}

	/** ID#1
	 * The general system state. If the system is following the MAVLink standard, the system state is mainly defined by three 
	 * orthogonal states/modes: The system mode, which is either 
	 * LOCKED (motors shut down and locked), 
	 * MANUAL (system under RC control), 
	 * GUIDED (system with autonomous position control, position setpoint controlled manually) or 
	 * AUTO (system guided by path/waypoint planner). 
	 * 
	 * The NAV_MODE defined the current flight state: 
	 * LIFTOFF (often an open-loop maneuver), 
	 * LANDING, 
	 * WAYPOINTS or 
	 * VECTOR. 
	 * 
	 * This represents the internal navigation state machine. The system status shows wether the system is currently active 
	 * or not and if an emergency occured. During the CRITICAL and EMERGENCY states the MAV is still considered to be active, 
	 * but should start emergency procedures autonomously. After a failure occured it should first move from active to critical 
	 * to allow manual intervention and then move to emergency after a certain timeout.
	 */
	private void SYS_STATUS(byte[] data){
	//	onboard_control_sensors_present		uint32_t	Bitmask showing which onboard controllers and sensors are present. Value of 0: not present. Value of 1: present. Indices defined by ENUM MAV_SYS_STATUS_SENSOR
	//	onboard_control_sensors_enabled		uint32_t	Bitmask showing which onboard controllers and sensors are enabled: Value of 0: not enabled. Value of 1: enabled. Indices defined by ENUM MAV_SYS_STATUS_SENSOR
	//	onboard_control_sensors_health		uint32_t	Bitmask showing which onboard controllers and sensors are operational or have an error: Value of 0: not enabled. Value of 1: enabled. Indices defined by ENUM MAV_SYS_STATUS_SENSOR
	//	load								uint16_t	Maximum usage in percent of the mainloop time, (0%: 0, 100%: 1000) should be always below 1000
	//	voltage_battery						uint16_t	Battery voltage, in millivolts (1 = 1 millivolt)
	//	current_battery						int16_t		Battery current, in 10*milliamperes (1 = 10 milliampere), -1: autopilot does not measure the current
	//	battery_remaining					int8_t		Remaining battery energy: (0%: 0, 100%: 100), -1: autopilot estimate the remaining battery
	//	drop_rate_comm						uint16_t	Communication drops in percent, (0%: 0, 100%: 10'000), (UART, I2C, SPI, CAN), dropped packets on all links (packets that were corrupted on reception on the MAV)
	//	errors_comm							uint16_t	Communication errors (UART, I2C, SPI, CAN), dropped packets on all links (packets that were corrupted on reception on the MAV)
	//	errors_count1						uint16_t	Autopilot-specific errors
	//	errors_count2						uint16_t	Autopilot-specific errors
	//	errors_count3						uint16_t	Autopilot-specific errors
	//	errors_count4						uint16_t	Autopilot-specific errors
	}

	/** ID#2
	 * 	The system time is the time of the master clock, typically the computer clock of the main onboard computer.
	 */
	private void SYSTEM_TIME(byte[] data){
	//	time_unix_usec	uint64_t	Timestamp of the master clock in microseconds since UNIX epoch.
	//	time_boot_ms	uint32_t	Timestamp of the component clock since boot time in milliseconds.
	}

	/** ID#24
	 * 	The global position, as returned by the Global Positioning System (GPS). 
	 *  This is NOT the global position estimate of the sytem, but rather a RAW sensor value. 
	 *  See message GLOBAL_POSITION for the global position estimate. Coordinate frame is right-handed, Z-axis up (GPS frame).
	 */
	private void GPS_RAW_INT(byte[] data){
	//	time_usec			uint64_t	Timestamp (microseconds since UNIX epoch or microseconds since system boot)
	//	fix_type			uint8_t		0-1: no fix, 2: 2D fix, 3: 3D fix. Some applications will not use the value of this field unless it is at least two, so always correctly fill in the fix.
	//	lat					int32_t		Latitude (WGS84), in degrees * 1E7
	//	lon					int32_t		Longitude (WGS84), in degrees * 1E7
	//	alt					int32_t		Altitude (WGS84), in meters * 1000 (positive for up)
	//	eph					uint16_t	GPS HDOP horizontal dilution of position in cm (m*100). If unknown, set to: UINT16_MAX
	//	epv					uint16_t	GPS VDOP vertical dilution of position in cm (m*100). If unknown, set to: UINT16_MAX
	//	vel					uint16_t	GPS ground speed (m/s * 100). If unknown, set to: UINT16_MAX
	//	cog					uint16_t	Course over ground (NOT heading, but direction of movement) in degrees * 100, 0.0..359.99 degrees. If unknown, set to: UINT16_MAX
	//	satellites_visible	uint8_t	Number of satellites visible. If unknown, set to 255
	}

	/** ID#27
	 * 	The RAW IMU readings for the usual 9DOF sensor setup. 
	 * 	This message should always contain the true raw values without any scaling to allow data capture and system debugging.
	 */
	private void RAW_IMU(byte[] data) {
		int index = 0;
		index+=8;
		xacc=toInteger(data,index, index+2, true);
		index+=2;
		yacc=toInteger(data,index, index+2, true);
		index+=2;
		zacc=toInteger(data,index, index+2, true);
		index+=2;
		xgyro=toInteger(data,index, index+2, true);
		index+=2;
		ygyro=toInteger(data,index, index+2, true);
		index+=2;
		zgyro=toInteger(data,index, index+2, true);
		index+=2;
		xmag=toInteger(data,index, index+2, true);
		index+=2;
		ymag=toInteger(data,index, index+2, true);
		index+=2;
		zmag=toInteger(data,index, index+2, true);
	}

	/** ID#29
	 * 	The pressure readings for the typical setup of one absolute and differential pressure sensor. 
	 * 	The units are as specified in each field.
	 */
	private void SCALED_PRESSURE(byte[] data){
	//	time_boot_ms	uint32_t	Timestamp (milliseconds since system boot)
	//	press_abs		float		Absolute pressure (hectopascal)
	//	press_diff		float		Differential pressure 1 (hectopascal)
	//	temperature		int16_t		Temperature measurement (0.01 degrees celsius)
	}

	/** ID#30
	 *	The attitude in the aeronautical frame (right-handed, Z-down, X-front, Y-right).
	 */
	private void ATTITUDE(byte[] data) {
		int index = 0;
		index+=4;
		roll= -toFloat(index, data);
		index+=4;
		pitch=toFloat(index, data);
		index+=4;
		yaw=toFloat(index, data);
		index+=4;
		rollspeed=toFloat(index, data);
		index+=4;
		pitchspeed=toFloat(index, data);
		index+=4;
		yawspeed=toFloat(index, data);
	}
	
	/** ID#33
	 *	The filtered global position (e.g. fused GPS and accelerometers). The position is in GPS-frame (right-handed, Z-up). 
	 *	It is designed as scaled integer message since the resolution of float is not sufficient.
	 */
	private void GLOBAL_POSITION_INT(byte[] data){
//		time_boot_ms	uint32_t	Timestamp (milliseconds since system boot)
//		lat				int32_t	Latitude, expressed as * 1E7
//		lon				int32_t	Longitude, expressed as * 1E7
//		alt				int32_t	Altitude in meters, expressed as * 1000 (millimeters), above MSL
//		relative_alt	int32_t	Altitude above ground in meters, expressed as * 1000 (millimeters)
//		vx				int16_t	Ground X Speed (Latitude), expressed as m/s * 100
//		vy				int16_t	Ground Y Speed (Longitude), expressed as m/s * 100
//		vz				int16_t	Ground Z Speed (Altitude), expressed as m/s * 100
//		hdg				uint16_t	Compass heading in degrees * 100, 0.0..359.99 degrees. If unknown, set to: UINT16_MAX

//		int index = 4;
//			System.out.print((double)(toInteger(data, index, index + 4)) / 10000000.0 + " , ");
//		index += 4;
		//.00663 -> Longitude Offset to compensate for GPS (3DR GPS MTK V2.0)
//			System.out.println(((double)(toInteger(data, index, index + 4)) / 10000000.0) + .00663);
//		index += 4;
//			System.out.println("alt: " + (float)(toInteger(data, index, index + 4)) / 1000f);
//		index += 4;
//			System.out.println("rel_alt: " + (float)(toInteger(data, index, index + 4)) / 1000f);
//		index += 4;
//			System.out.println("GroundX: " + (toInteger(data, index, index + 2) / 100));
//		index += 2;
//			System.out.println("GroundY: " + (toInteger(data, index, index + 2) / 100));
//		index += 2;
//			System.out.println("GroundZ: " + (toInteger(data, index, index + 2) / 100));
//		index += 2;
	}

	/** ID#35
	 *	The RAW values of the RC channels received. The standard PPM modulation is as follows: 
	 *	1000 microseconds: 0%, 
	 *	2000 microseconds: 100%. 
	 *	Individual receivers/transmitters might violate this specification.
	 */
	private void RC_CHANNELS_RAW(byte[] data){
	//	time_boot_ms	uint32_t	Timestamp (milliseconds since system boot)
	//	port			uint8_t		Servo output port (set of 8 outputs = 1 port). Most MAVs will just use one, but this allows for more than 8 servos.
	//	chan1_raw		uint16_t	RC channel 1 value, in microseconds. A value of UINT16_MAX implies the channel is unused.
	//	chan2_raw		uint16_t	RC channel 2 value, in microseconds. A value of UINT16_MAX implies the channel is unused.
	//	chan3_raw		uint16_t	RC channel 3 value, in microseconds. A value of UINT16_MAX implies the channel is unused.
	//	chan4_raw		uint16_t	RC channel 4 value, in microseconds. A value of UINT16_MAX implies the channel is unused.
	//	chan5_raw		uint16_t	RC channel 5 value, in microseconds. A value of UINT16_MAX implies the channel is unused.
	//	chan6_raw		uint16_t	RC channel 6 value, in microseconds. A value of UINT16_MAX implies the channel is unused.
	//	chan7_raw		uint16_t	RC channel 7 value, in microseconds. A value of UINT16_MAX implies the channel is unused.
	//	chan8_raw		uint16_t	RC channel 8 value, in microseconds. A value of UINT16_MAX implies the channel is unused.
	//	rssi			uint8_t		Receive signal strength indicator, 0: 0%, 100: 100%, 255: invalid/unknown.
	}

	/** ID#36
	 *	The RAW values of the servo outputs (for RC input from the remote, use the RC_CHANNELS messages). 
	 *	The standard PPM modulation is as follows: 1000 microseconds: 0%, 2000 microseconds: 100%.
	 */
	private void SERVO_OUTPUT_RAW(byte[] data){
	//	time_usec	uint32_t	Timestamp (microseconds since system boot)
	//	port		uint8_t		Servo output port (set of 8 outputs = 1 port). Most MAVs will just use one, but this allows to encode more than 8 servos.
	//	servo1_raw	uint16_t	Servo output 1 value, in microseconds
	//	servo2_raw	uint16_t	Servo output 2 value, in microseconds
	//	servo3_raw	uint16_t	Servo output 3 value, in microseconds
	//	servo4_raw	uint16_t	Servo output 4 value, in microseconds
	//	servo5_raw	uint16_t	Servo output 5 value, in microseconds
	//	servo6_raw	uint16_t	Servo output 6 value, in microseconds
	//	servo7_raw	uint16_t	Servo output 7 value, in microseconds
	//	servo8_raw	uint16_t	Servo output 8 value, in microseconds
	}

	/** ID#42
	 *	Message that announces the sequence number of the current active mission item. The MAV will fly towards this mission item.
	 * @param data
	 */
	private void MISSION_CURRENT(byte[] data){
	//	seq	uint16_t	Sequence
	}

	/** ID#62
	 * 	Outputs of the APM navigation controller. The primary use of this message is to check the response and signs of the 
	 *	controller before actual flight and to assist with tuning controller parameters.
	 * @param data
	 */
	private void NAV_CONTROLLER_OUTPUT(byte[] data){
	//	nav_roll		float		Current desired roll in degrees
	//	nav_pitch		float		Current desired pitch in degrees
	//	nav_bearing		int16_t		Current desired heading in degrees
	//	target_bearing	int16_t		Bearing to current MISSION/target in degrees
	//	wp_dist			uint16_t	Distance to active MISSION in meters
	//	alt_error		float		Current altitude error in meters
	//	aspd_error		float		Current airspeed error in meters/second
	//	xtrack_error	float		Current crosstrack error on x-y plane in meters
	}
	
	/** ID#69
	 *	This message provides an API for manually controlling the vehicle using standard joystick axes nomenclature, 
	 *  along with a joystick-like input device. Unused axes can be disabled an buttons are also transmit as boolean values of their
	 */
	private byte[] MANUAL_CONTROL(byte[] data)
	{
	/*	x				int16_t		X-axis, normalized to the range [-1000,1000]. A value of INT16_MAX indicates that this axis is invalid. Generally corresponds to forward(1000)-backward(-1000) movement on a joystick and the pitch of a vehicle.
		y				int16_t		Y-axis, normalized to the range [-1000,1000]. A value of INT16_MAX indicates that this axis is invalid. Generally corresponds to left(-1000)-right(1000) movement on a joystick and the roll of a vehicle.
		z				int16_t		Z-axis, normalized to the range [-1000,1000]. A value of INT16_MAX indicates that this axis is invalid. Generally corresponds to a separate slider movement with maximum being 1000 and minimum being -1000 on a joystick and the thrust of a vehicle.
		r				int16_t		R-axis, normalized to the range [-1000,1000]. A value of INT16_MAX indicates that this axis is invalid. Generally corresponds to a twisting of the joystick, with counter-clockwise being 1000 and clockwise being -1000, and the yaw of a vehicle.
		buttons			uint16_t	A bitfield corresponding to the joystick buttons' current state, 1 for pressed, 0 for released. The lowest bit corresponds to Button 1.
		target			uint8_t		The system to be controlled.
	*/
		//Get Pitch [-1000, 1000]
		int x = 0;
		//Get Roll [-1000, 1000]
		int y = 0;
		//Get Thrust [-1000, 1000]
		int z = 0;
		//Get Yaw [-1000, 1000]
		int r = 0;
		
		//Get Button Presses [B16, B15... B2, B1]
		int buttons = 0;
		return new byte[]{(byte) (x>>8), (byte) x, (byte) (y>>8), (byte) y, (byte) (z>>8), (byte) z,
				(byte) (r>>8), (byte) r, (byte) (buttons>>8), (byte) buttons,  (byte) this.systemID};
	}
	
	/** ID#74
	 * 	Metrics typically displayed on a HUD for fixed wing aircraft
	 * @param data
	 */
	private void VFR_HUD(byte[] data){
//		airspeed		float		Current airspeed in m/s
//		groundspeed		float		Current ground speed in m/s
//		alt				float		Current altitude (MSL), in meters
//		climb			float		Current climb rate in meters/second
//		heading			int16_t		Current heading in degrees, in compass units (0..360, 0=north)
//		throttle		uint16_t	Current throttle setting in integer percent, 0 to 100
		int index = 0;
		airspeed 	= toFloat(index, data);
		index += 4;
		groundspeed = toFloat(index, data);
		index += 4;
		alt 		= toFloat(index, data);
		index += 4;
		climb 		= toFloat(index, data);
		index += 4;
		heading 	= ((int)data[index+1])<<8 | (int)(data[index] & 0x0FF);
		index += 2;
		throttle	= ((int)(data[index+1] & 0x0FF))<<8 | (int)(data[index] & 0x0FF);
	}
	
	// ID#181	PowerHearbeat		L=6
	private void powerSupply(byte[] data){
		this.updated=true;
		int index=0;
		batteryLvl=data[index++]&0x00FF;
		fiveLvl=data[index++]&0x00FF;
		twelveLvl=data[index++]&0x00FF;
		current=data[index++]&0x00FF;
		chargeLvl=data[index++]&0x00FF;
		systemsStat=data[index++];
	}
		
	// ID#184	Thresholds Levels	L=7
	private void powerThresholds(byte[] data){
		int index=0;
		lvl1TH=data[index++]&0x00FF;
		lvl2TH=data[index++]&0x00FF;
		lvl3TH=data[index++]&0x00FF;
		lvl4TH=data[index++]&0x00FF;
		lvl5TH=data[index++]&0x00FF;
		lvl6TH=data[index++]&0x00FF;
		lvl7TH=data[index++]&0x00FF;
	}

	// ID#182    Request Thresholds	L=2
	private byte[] requestTH(){
		byte[] out={(byte) systemID,(byte) componentID};
		return out;
	}

	// ID#183    Set Thresholds		L=9
	private byte[] setTH(byte[] parameters){
		byte[] out=new byte[9];
		out[0]=	(byte) systemID;
		out[1]= (byte) componentID;
		out[2]= parameters[0];
		out[3]= parameters[1];
		out[4]= parameters[2];
		out[5]= parameters[3];
		out[6]= parameters[4];
		out[7]= parameters[5];
		out[8]= parameters[6];
		return out;
	}

	// ID#185    Set Component ID	L=3
	private byte[] setCompID(byte[] parameters){
		byte[] out={(byte) systemID,(byte) componentID,parameters[0]};
		return out;
	}
	
	
	private class ConnectionCheck extends Thread{
		public void run(){
			while(true){
				if(updated)connected=true;
				else connected=false;
				updated=false;
				try {
					sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}