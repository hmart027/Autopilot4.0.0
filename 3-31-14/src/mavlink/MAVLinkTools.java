package mavlink;

/* Has implementations to convert to Integer
 * Float
 * from byte arrays
 */

public class MAVLinkTools {
	
	/**
	 * Gets an internal array from a byte array. Start point is inclusive and end point is exclusive.
	 * @param start inclusive start point
	 * @param end exclusive end point
	 * @return sub array
	 */
	public static byte[] subArray(byte[] array, int start, int end){
		int length = end-start;
		if ( length < 0) return null;
		byte[] newArray = new byte[length];
		for(int i = 0; i<length; i++){
			newArray[i] = array[i+start];
		}
		return newArray;
	}
	
	/**
	 * This method creates a 32bit integer from and array. It gets an inclusive start byte and an exclsive end byte.
	 * @param info	array containing the Integer
	 * @param start	position of the first byte
	 * @param end 1+position of final byte
	 * @param signed whether the integer is signed or unsigned
	 * @return 32bit Integer
	 */
	public static int toInteger(byte[] info,int start, int end, boolean signed) {
		
		byte[] newArray = new byte[4];
		for (int i=0; i< end-start; i++)
			newArray[i]=info[end-1-i];

		int finalv;
		if(signed)
			finalv = newArray[0];
		else
			finalv = newArray[0] & 0x00FF;
		for (int i = 1; i < newArray.length; i++) {
			finalv = (finalv << 8) | (newArray[i] & 0x00FF);
		}
		return finalv;
	}
	
	public static float toFloat(int start, byte[] info) {
		
		byte[] newArray = new byte[4];
		for (int i=0; i<4; i++)
			newArray[i]=info[start+3-i];
		
		float finalv = 0;
		int sign = (newArray[0] & 0x00080) >> 7;
		byte[] mantisa = { (byte) (newArray[1] | 0X0080), newArray[2],
				newArray[3] };
		byte exponent = (byte) ((newArray[0] << 1) | ((newArray[1] >> 7) & 1));
		for (int x = 0; x < 3; x++) {
			finalv += (float) ((mantisa[x] & 0x00FF) * Math.pow(2,
					(exponent & 0x00FF) - 134 - x * 8));
		}
		if (sign != 0)
			finalv = finalv * (-1);
		return finalv;
	}


}