package mavlink;

/**
 * This is the list of enumerations such as packages lenghts and CRCs. This is compatible with 
 * version 3 of MAVLINK commands.
 * @author Harold
 * @version 3.0
 */
public class MAVLinkEnum {
	
	public static final String MAVLINK_VERSION  = "3.0";
	
	public static final int MAVLINK_MIN_MSG_LENGTH = 8;
	
	public static final char[] MAVLINK_MESSAGE_LENGTHS = { 
		//    0    1    2    3    4    5    6    7    8    9   10   11   12   13   14   15
			  9,  31,  12,   0,  14,  28,   3,  32,   0,   0,   0,   6,   0,   0,   0,   0,	 	// 15
			  0,   0,   0,   0,  20,   2,  25,  23,  30, 101,  22,  26,  16,  14,  28,  32,		// 31
			 28,  28,  22,  22,  21,   6,   6,  37,   4,   4,   2,   2,   4,   2,   2,   3,     // 47
			 13,  12,  19,  17,  15,  15,  27,  25,  18,  18,  20,  20,   9,  34,  26,  46,  	// 63 
			 36,   0,   6,   4,   0,  11,  18,   0,   0,   0,  20,   0,  33,   3,   0,   0, 	// 79
			 20,  22,   0,   0,   0,   0,   0,   0,   0,  28,  56,  42,  33,   0,   0,   0, 	// 95
			  0,   0,   0,   0,  26,  32,  32,  20,  32,  62,   0,   0,   0,   0, 254, 249,		// 111
			  9,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,	  0,   0,   0,   0, 	// 127
			  0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0, 	// 143
			  0,   0,   0,  16,  33,  25,  42,   8,   4,  12,  15,  13,   6,  15,  14,   0,		// 159
			 12,   3,   8,  28,  44,   3,   9,  22,  12,  18,  34,  66,  98,   0,   0,   0, 	// 175
			  0,   0,   0,   0,   0,   6,   2,   9,   7,   3,   0,   0,   0,   0,   0,   0, 	// 191
			  0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0, 	// 207
			  0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,		// 223
			  0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0, 	// 239
			  0,   0,   0,   0,	  0,   0,   0,   0,   0,  36,  30,  18,  18,  51,   9,   0 };	// 255						// 244-255

	public static final char[] MAVLINK_MESSAGE_CRCS = {
		//    0    1    2    3    4    5    6    7    8    9   10   11   12   13   14   15
			 50, 124, 137,   0, 237, 217, 104, 119,   0,   0,   0,  89,   0,   0,   0,   0,		// 15
			  0,   0,   0,   0, 214, 159, 220, 168,  24,  23, 170, 144,  67, 115,  39, 246,		// 31
			185, 104, 237, 244, 222, 212,   9, 254, 230,  28,  28, 132, 221, 232,  11, 153, 	// 47
			 41,  39, 214, 223, 141,  33,  15,   3, 100,  24, 239, 238,  30, 240, 183, 130,		// 63
			130,   0, 148,  21,   0, 243, 124,   0,   0,   0,  20,   0, 152, 143,   0,   0, 	// 79
			127, 106,   0,   0,   0,   0,   0,   0,   0, 231, 183,  63,  54,   0,   0,   0,		// 95
			  0,   0,   0,   0, 175, 102, 158, 208,  56,  93, 211, 108,  32, 185, 235,  93,		// 111
			124, 124, 119,   4,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0, 	// 127
			  0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0, 	// 143
			  0,   0,   0, 177, 241,  15, 241,   0,   0,   0,   0,   0,   0,   0,   0,   0,		// 159
			  0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0, 	// 175
			  0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0, 	// 191
			  0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,		// 207
			  0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,		// 223
			  0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,		// 239
			  0,   0,   0,   0,   0,   0,   0,   0,   0, 204,  49, 170,  44,  83,  46,   0};	// 255

	public static enum MAV_AUTOPILOT{
		GENERIC(0),											// Generic autopilot, full support for everything
		PIXHAWK(1),											// PIXHAWK autopilot, http://pixhawk.ethz.ch
		SLUGS(2),											// SLUGS autopilot, http://slugsuav.soe.ucsc.edu
		ARDUPILOTMEGA(3),									// ArduPilotMega / ArduCopter, http://diydrones.com
		OPENPILOT(4),										// OpenPilot, http://openpilot.org
		GENERIC_WAYPOINTS_ONLY(5),							// Generic autopilot only supporting simple waypoints
		GENERIC_WAYPOINTS_AND_SIMPLE_NAVIGATION_ONLY(6),	// Generic autopilot supporting waypoints and other simple navigation commands
		GENERIC_MISSION_FULL(7),							// Generic autopilot supporting the full mission command set
		INVALID(8),											// No valid autopilot, e.g. a GCS or other MAVLink component
		PPZ(9),												// PPZ UAV - http://nongnu.org/paparazzi
		UDB(10),											// UAV Dev Board
		FP(11),												// FlexiPilot
		PX4(12),											// PX4 Autopilot - http://pixhawk.ethz.ch/px4/
		SMACCMPILOT(13),									// SMACCMPilot - http://smaccmpilot.org
		AUTOQUAD(14),										// AutoQuad -- http://autoquad.org
		ARMAZILA(15),										// Armazila -- http://armazila.com
		AEROB(16);											// Aerob -- http://aerob.ru
		
		public final int value;
		
		private MAV_AUTOPILOT(int value){
			this.value = value;
		}
		
		public static MAV_AUTOPILOT get(int value){
			for(MAV_AUTOPILOT m : MAV_AUTOPILOT.values())
				if(m.value==value)
					return m;
			return null;
		}
	}
		
	public static enum MAV_TYPE{
		GENERIC(0),				// Generic micro air vehicle.
		FIXED_WING(1),			// Fixed wing aircraft.
		QUADROTOR(2),			// Quadrotor
		COAXIAL(3),				// Coaxial helicopter
		HELICOPTER(4),			// Normal helicopter with tail rotor.
		ANTENNA_TRACKER(5),		// Ground installation
		GCS(6),					// Operator control unit / ground control station
		AIRSHIP(7),				// Airship, controlled
		FREE_BALLOON(8),		// Free balloon, uncontrolled
		ROCKET(9),				// Rocket
		GROUND_ROVER(10),		// Ground rover
		SURFACE_BOAT(11),		// Surface vessel, boat, ship
		SUBMARINE(12),			// Submarine
		HEXAROTOR(13),			// Hexarotor
		OCTOROTOR(14),			// Octorotor
		TRICOPTER(15),			// Octorotor
		FLAPPING_WING(16),		// Flapping wing
		KITE(17);				// Flapping wing

		public final int value;
		
		private MAV_TYPE(int value){
			this.value = value;
		}

		public static MAV_TYPE get(int value) {
			for(MAV_TYPE m : MAV_TYPE.values())
				if(m.value==value)
					return m;
			return null;
		}		
	}

	public static enum MAV_MODE_FLAG{
		SAFETY_ARMED(128),			// 0b10000000 MAV safety set to armed. Motors are enabled / running / can start. Ready to fly.
		MANUAL_INPUT_ENABLED(64),	// 0b01000000 remote control input is enabled.
		HIL_ENABLED(32),			// 0b00100000 hardware in the loop simulation. All motors / actuators are blocked, but internal software is full operational.
		STABILIZE_ENABLED(16),		// 0b00010000 system stabilizes electronically its attitude (and optionally position). It needs however further control inputs to move around.
		GUIDED_ENABLED(8),			// 0b00001000 guided mode enabled, system flies MISSIONs / mission items.
		AUTO_ENABLED(4),			// 0b00000100 autonomous mode enabled, system finds its own goal positions. Guided flag can be set or not, depends on the actual implementation.
		TEST_ENABLED(2),			// 0b00000010 system has a test mode enabled. This flag is intended for temporary system tests and should not be used for stable implementations.
		CUSTOM_MODE_ENABLED(1);		// 0b00000001 Reserved for future use.
		
		public final int value;
		
		private MAV_MODE_FLAG(int value){
			this.value = value;
		}

		public static MAV_MODE_FLAG get(int value) {
			for(MAV_MODE_FLAG m : MAV_MODE_FLAG.values())
				if(m.value==value)
					return m;
			return null;
		}
	}
	
	public static enum MAV_GOTO{
		DO_HOLD(0),						// Hold at the current position.
		DO_CONTINUE(1),					// Continue with the next item in mission execution.
		HOLD_AT_CURRENT_POSITION(2),	// Hold at the current position of the system
		HOLD_AT_SPECIFIED_POSITION(3);	// Hold at the position specified in the parameters of the DO_HOLD action

		public final int value;
		
		private MAV_GOTO(int value){
			this.value = value;
		}
		
		public static MAV_GOTO get(int value) {
			for(MAV_GOTO m : MAV_GOTO.values())
				if(m.value==value)
					return m;
			return null;
		}
	}
	
	public static enum MAV_MODE{
		PREFLIGHT(0),			// System is not ready to fly, booting, calibrating, etc. No flag is set.
		STABILIZE_DISARMED(80),	// System is allowed to be active, under assisted RC control.
		STABILIZE_ARMED(208),	// System is allowed to be active, under assisted RC control.
		MANUAL_DISARMED(64),	// System is allowed to be active, under manual (RC) control, no stabilization
		MANUAL_ARMED(192),		// System is allowed to be active, under manual (RC) control, no stabilization
		GUIDED_DISARMED(88),	// System is allowed to be active, under autonomous control, manual setpoint
		GUIDED_ARMED(216),		// System is allowed to be active, under autonomous control, manual setpoint
		AUTO_DISARMED(92),		// System is allowed to be active, under autonomous control and navigation (the trajectory is decided onboard and not pre-programmed by MISSIONs)
		AUTO_ARMED(220),		// System is allowed to be active, under autonomous control and navigation (the trajectory is decided onboard and not pre-programmed by MISSIONs)
		TEST_DISARMED(66),		// UNDEFINED mode. This solely depends on the autopilot - use with caution, intended for developers only.
		TEST_ARMED(194);		// UNDEFINED mode. This solely depends on the autopilot - use with caution, intended for developers only.
		
		public final int value;
		
		private MAV_MODE(int value){
			this.value = value;
		}
		
		public static MAV_MODE get(int value) {
			for(MAV_MODE m : MAV_MODE.values())
				if(m.value==value)
					return m;
			return null;
		}
	}
	
	public static enum MAV_STATE{
		UNINIT(0), 			// Uninitialized system, state is unknown.
		BOOT(1), 			// System is booting up.
		CALIBRATING(2), 	// System is calibrating and not flight-ready.
		STANDBY(3), 		// System is grounded and on standby. It can be launched any time.
		ACTIVE(4), 			// System is active and might be already airborne. Motors are engaged.
		CRITICAL(5), 		// System is in a non-normal flight mode. It can however still navigate.
		EMERGENCY(6), 		// System is in a non-normal flight mode. It lost control over parts or over the whole airframe. It is in mayday and going down.
		POWEROFF(7); 		// System just initialized its power-down sequence, will shut down now.
		
		public final int value;
		
		private MAV_STATE(int value){
			this.value = value;
		}
		
		public static MAV_STATE get(int value) {
			for(MAV_STATE m : MAV_STATE.values())
				if(m.value==value)
					return m;
			return null;
		}
	}
	
	public static enum MAV_COMPONENT{
		ID_ALL(0), 				//
		ID_CAMERA(100), 		//
		ID_SERVO1(140), 		//
		ID_SERVO2(141), 		//
		ID_SERVO3(142), 		//
		ID_SERVO4(143), 		//
		ID_SERVO5(144), 		//
		ID_SERVO6(145), 		//
		ID_SERVO7(146), 		//
		ID_SERVO8(147), 		//
		ID_SERVO9(148), 		//
		ID_SERVO10(149), 		//
		ID_SERVO11(150), 		//
		ID_SERVO12(151), 		//
		ID_SERVO13(152), 		//
		ID_SERVO14(153), 		//
		ID_MAPPER(180), 		//
		ID_MISSIONPLANNER(190), //
		ID_PATHPLANNER(195), 	//
		ID_IMU(200), 			//
		ID_IMU_2(201), 			//
		ID_IMU_3(202), 			//
		ID_GPS(220), 			//
		ID_UDP_BRIDGE(240), 	//
		ID_UART_BRIDGE(241), 	//
		ID_SYSTEM_CONTROL(250), //
		ENUM_END(251); 			//
		
		public final int value;
		
		private MAV_COMPONENT(int value){
			this.value = value;
		}
		
		public static MAV_COMPONENT get(int value) {
			for(MAV_COMPONENT m : MAV_COMPONENT.values())
				if(m.value==value)
					return m;
			return null;
		}
	}
	
	public static enum MAV_FRAME{
		GLOBAL(0), 				// Global coordinate frame, WGS84 coordinate system. First value / x: latitude, second value / y: longitude, third value / z: positive altitude over mean sea level (MSL) | */
		LOCAL_NED(1), 			// Local coordinate frame, Z-up (x: north, y: east, z: down). | */
		MISSION(2), 			// NOT a coordinate frame, indicates a mission command. | */
		GLOBAL_RELATIVE_ALT(3), // Global coordinate frame, WGS84 coordinate system, relative altitude over ground with respect to the home position. First value / x: latitude, second value / y: longitude, third value / z: positive altitude with 0 being at the altitude of the home location. | */
		LOCAL_ENU(4); 			// Local coordinate frame, Z-down (x: east, y: north, z: up) | */

		public final int value;
		
		private MAV_FRAME(int val){
			this.value = val;
		}
		
		public static MAV_FRAME get(int value){
			for(MAV_FRAME m : MAV_FRAME.values())
				if(m.value==value)
					return m;
			return null;
		}
	}
	
	public static enum MAV_DATA_STREAM {
		ALL(0), 				// Enable all data streams
		RAW_SENSORS(1), 		// Enable IMU_RAW, GPS_RAW, GPS_STATUS packets.
		EXTENDED_STATUS(2), 	// Enable GPS_STATUS, CONTROL_STATUS, AUX_STATUS
		RC_CHANNELS(3),			// Enable RC_CHANNELS_SCALED, RC_CHANNELS_RAW, SERVO_OUTPUT_RAW
		RAW_CONTROLLER(4), 		// Enable ATTITUDE_CONTROLLER_OUTPUT, POSITION_CONTROLLER_OUTPUT, NAV_CONTROLLER_OUTPUT
		POSITION(6), 			// Enable LOCAL_POSITION, GLOBAL_POSITION/GLOBAL_POSITION_INT messages || CMD #33
		EXTRA1(10), 			// ArduPilot 2.5 Gives Attitude || CMD #30
		EXTRA2(11), 			// ArduPilot 2.5 Gives VFR_HUD [Includes Heading] || CMD #74
		EXTRA3(12);				// Dependent on the autopilot
		
		public final int value;
		
		private MAV_DATA_STREAM(int val){
			this.value = val;
		}
		
		public static MAV_DATA_STREAM get(int value){
			for(MAV_DATA_STREAM m : MAV_DATA_STREAM.values())
				if(m.value==value)
					return m;
			return null;
		}	
	}
	
	public static enum MAV_ROI{
		NONE(0), 		// No region of interest.
		WPNEXT(1), 		// Point toward next MISSION.
		WPINDEX(2), 	// Point toward given MISSION.
		LOCATION(3), 	// Point toward fixed location.
		TARGET(4); 		// Point toward of given id.

		public final int value;
		
		private MAV_ROI(int val){
			this.value = val;
		}
		
		public static MAV_ROI get(int value){
			for(MAV_ROI m : MAV_ROI.values())
				if(m.value==value)
					return m;
			return null;
		}	
		
	}
	
	public static enum MAV_CMD_ACK{
		OK(1), 									// Command / mission item is ok. | */
		ERR_FAIL(2), 							// Generic error message if none of the other reasons fails or if no detailed error reporting is implemented. | */
		ERR_ACCESS_DENIED(3), 					// The system is refusing to accept this command from this source / communication partner. | */
		ERR_NOT_SUPPORTED(4), 					// Command or mission item is not supported, other commands would be accepted. | */
		ERR_COORDINATE_FRAME_NOT_SUPPORTED(5), 	// The coordinate frame of this command / mission item is not supported. | */
		ERR_COORDINATES_OUT_OF_RANGE(6), 		// The coordinate frame of this command is ok, but he coordinate values exceed the safety limits of this system. This is a generic error, please use the more specific error messages below if possible. | */
		ERR_X_LAT_OUT_OF_RANGE(7), 				// The X or latitude value is out of range. | */
		ERR_Y_LON_OUT_OF_RANGE(8), 				// The Y or longitude value is out of range. | */
		ERR_Z_ALT_OUT_OF_RANGE(9); 				// The Z or altitude value is out of range. | */

		public final int value;
		
		private MAV_CMD_ACK(int val){
			this.value = val;
		}
		
		public static MAV_CMD_ACK get(int value){
			for(MAV_CMD_ACK m : MAV_CMD_ACK.values())
				if(m.value==value)
					return m;
			return null;
		}	
	}
	
	public static enum MAV_RESULT{
		ACCEPTED(0), 				// Command ACCEPTED and EXECUTED
		TEMPORARILY_REJECTED(1), 	// Command TEMPORARY REJECTED/DENIED
		DENIED(2), 					// Command PERMANENTLY DENIED
		UNSUPPORTED(3), 			// Command UNKNOWN/UNSUPPORTED
		FAILED(4); 					// Command executed, but failed

		public final int value;
		
		private MAV_RESULT(int val){
			this.value = val;
		}
		
		public static MAV_RESULT get(int value){
			for(MAV_RESULT m : MAV_RESULT.values())
				if(m.value==value)
					return m;
			return null;
		}	
	}
	
	public static enum MAV_MISSION_RESULT{
		ACCEPTED(0), 			// mission accepted OK
		ERROR(1), 				// generic error / not accepting mission commands at all right now
		UNSUPPORTED_FRAME(2), 	// coordinate frame is not supported
		UNSUPPORTED(3), 		// command is not supported
		NO_SPACE(4), 			// mission item exceeds storage space
		INVALID(5), 			// one of the parameters has an invalid value
		INVALID_PARAM1(6), 		// param1 has an invalid value
		INVALID_PARAM2(7), 		// param2 has an invalid value
		INVALID_PARAM3(8), 		// param3 has an invalid value
		INVALID_PARAM4(9), 		// param4 has an invalid value
		INVALID_PARAM5_X(10), 	// x/param5 has an invalid value
		INVALID_PARAM6_Y(11), 	// y/param6 has an invalid value
		INVALID_PARAM7(12), 	// param7 has an invalid value
		INVALID_SEQUENCE(13), 	// received waypoint out of sequence
		DENIED(14); 			// not accepting any mission commands from this communication partner

		public final int value;
		
		private MAV_MISSION_RESULT(int val){
			this.value = val;
		}
		
		public static MAV_MISSION_RESULT get(int value){
			for(MAV_MISSION_RESULT m : MAV_MISSION_RESULT.values())
				if(m.value==value)
					return m;
			return null;
		}	
	}
	
	public static enum MAV_SEVERITY{
		EMERGENCY(0), 	// System is unusable. This is a "panic" condition.
		ALERT(1), 		// Action should be taken immediately. Indicates error in non-critical systems.
		CRITICAL(2), 	// Action must be taken immediately. Indicates failure in a primary system.
		ERROR(3), 		// Indicates an error in secondary/redundant systems.
		WARNING(4), 	// Indicates about a possible future error if this is not resolved within a given timeframe. Example would be a low battery warning.
		NOTICE(5), 		// An unusual event has occured, though not an error condition. This should be investigated for the root cause.
		INFO(6), 		// Normal operational messages. Useful for logging. No action is required for these messages.
		DEBUG(7); 		// Useful non-operational messages that can assist in debugging. These should not occur during normal operation.

		public final int value;
		
		private MAV_SEVERITY(int val){
			this.value = val;
		}
		
		public static MAV_SEVERITY get(int value){
			for(MAV_SEVERITY m : MAV_SEVERITY.values())
				if(m.value==value)
					return m;
			return null;
		}	
	}
	
	public static enum MAV_MESSAGE_TYPE{
		HEARTBEAT(0),
		SYS_STATUS(1),
		SYSTEM_TIME(2),
		PING(4),
		CHANGE_OPERATOR_CONTROL(5),
		CHANGE_OPERATOR_CONTROL_ACK(6),
		AUTH_KEY(7),
		SET_MODE(11),
		PARAM_REQUEST_READ(20),
		PARAM_REQUEST_LIST(21),
		PARAM_VALUE(22),
		PARAM_SET(23),
		GPS_RAW_INT(24),
		GPS_STATUS(25),
		SCALED_IMU(26),
		RAW_IMU(27),
		RAW_PRESSURE(28),
		SCALED_PRESSURE(29),
		ATTITUDE(30),
		ATTITUDE_QUATERNION(31),
		LOCAL_POSITION_NED(32),
		GLOBAL_POSITION_INT(33),
		RC_CHANNELS_SCALED(34),
		RC_CHANNELS_RAW(35),
		SERVO_OUTPUT_RAW(36),
		MISSION_REQUEST_PARTIAL_LIST(37),
		MISSION_WRITE_PARTIAL_LIST(38),
		MISSION_ITEM(39),
		MISSION_REQUEST(40),
		MISSION_SET_CURRENT(41),
		MISSION_CURRENT(42),
		MISSION_REQUEST_LIST(43),
		MISSION_COUNT(44),
		MISSION_CLEAR_ALL(45),
		MISSION_ITEM_REACHED(46),
		MISSION_ACK(47),
		SET_GPS_GLOBAL_ORIGIN(48),
		GPS_GLOBAL_ORIGIN(49),
		SET_LOCAL_POSITION_SETPOINT(50),
		LOCAL_POSITION_SETPOINT(51),
		GLOBAL_POSITION_SETPOINT_INT(52),
		SET_GLOBAL_POSITION_SETPOINT_INT(53),
		SAFETY_SET_ALLOWED_AREA(54),
		SAFETY_ALLOWED_AREA(55),
		SET_ROLL_PITCH_YAW_THRUST(56),
		SET_ROLL_PITCH_YAW_SPEED_THRUST(57),
		ROLL_PITCH_YAW_THRUST_SETPOINT(58),
		ROLL_PITCH_YAW_SPEED_THRUST_SETPOINT(59),
		SET_QUAD_MOTORS_SETPOINT(60),
		SET_QUAD_SWARM_ROLL_PITCH_YAW_THRUST(61),
		NAV_CONTROLLER_OUTPUT(62),
		SET_QUAD_SWARM_LED_ROLL_PITCH_YAW_THRUST(63),
		STATE_CORRECTION(64),
		RC_CHANNELS(65),
		REQUEST_DATA_STREAM(66),
		DATA_STREAM(67),
		MANUAL_CONTROL(69),
		RC_CHANNELS_OVERRIDE(70),
		VFR_HUD(74);
		
		public final int value;
		
		private MAV_MESSAGE_TYPE(int mID){
			this.value = mID;
		}
		
		public static MAV_MESSAGE_TYPE get(int mID){
			for(MAV_MESSAGE_TYPE m : MAV_MESSAGE_TYPE.values())
				if(m.value==mID)
					return m;
			return null;
		}	
	}
}