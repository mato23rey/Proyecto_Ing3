package security;

import java.security.MessageDigest;

/**
 * @author Seba
 * Clase que sirve para encriptar utilizando el sistema MD5
 */
public final class MD5Generator {




	/**
	 * String de hashing.
	 */
	private static final String SALT = "aa%4355$GG";

	/** Método que genera la encriptación MD5 de una cadena de texto ingresada.
	 * @param key Texto a encriptar
	 * @return Texto encriptado
	 */
	public static String generate(String key) {
		key += SALT;
		try {
			MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(key.getBytes());
			StringBuffer sb = new StringBuffer();

			for (int i = 0; i < array.length; ++i) {
				String data = Integer.toHexString((array[i] & 0xFF) | 0x100);
				data = data.substring(1,3);
				sb.append(data);
			}

			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
		}
		return null;
	}

}
