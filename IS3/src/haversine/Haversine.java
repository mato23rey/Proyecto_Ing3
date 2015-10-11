package haversine;

/**
 * @author Seba
 * Clase que se utiliza para calcular la distancia entre 2 coordenadas
 *  utilizando la formula de Haversine.
 */
public class Haversine {


	/**Radio de la tierra.*/
	private static final double R = 6371; // In kilometers

	/** Método que calcula la distancia real aproximada entre 2 coordenadas.
	 * @param origin Coordenada de origen
	 * @param destiny Coordenada de destino
	 * @return Distancia entre las coordenadas
	 */
	public static double calculateDistance(final Coordinate origin,	final Coordinate destiny) {
		double lat1 = origin.getLatitude();
		double lat2 = destiny.getLatitude();

		double lon1 = origin.getLongitude();
		double lon2 = destiny.getLongitude();

		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);

		double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
		double c = 2 * Math.asin(Math.sqrt(a));
		return R * c;
	}
}