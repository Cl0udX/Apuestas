/**
 * 
 * @author Santiago
 *clase de datos que alamcenan temporalmete la informacion de cada apostador
 */
public class Datos{
		private String id;
		private String caballo;
		private String cantidad;
		private String fecha;
		
		public Datos(String id, String caballo,String cantidad,String fecha) {
			this.id=id;
			this.caballo=caballo;
			this.cantidad=cantidad;
			this.fecha=fecha;
			
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getCaballo() {
			return caballo;
		}
		public void setCaballo(String caballo) {
			this.caballo = caballo;
		}
		public String getCantidad() {
			return cantidad;
		}
		public void setCantidad(String cantidad) {
			this.cantidad = cantidad;
		}
		public String getFecha() {
			return fecha;
		}
		public void setFecha(String fecha) {
			this.fecha = fecha;
		}
		
		
	}
