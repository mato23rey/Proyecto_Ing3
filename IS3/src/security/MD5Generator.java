package security;

/**
 * @author Seba
 * Clase que sirve para encriptar utilizando el sistema MD5
 */
public class MD5Generator {

	/** M�todo que genera la encriptaci�n MD5 de una cadena de texto ingresada
	 * @param key Texto a encriptar
	 * @return Texto encriptado
	 */
	public static String generate(String key){

		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(key.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
		}
		return null;
	}

}
