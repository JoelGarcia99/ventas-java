package estructuras;

import java.io.Serializable;

public abstract class ModeloGenerico implements Serializable{

	private static final long serialVersionUID = -976186362788066030L;
	protected String ID;
	
	public ModeloGenerico(String id) {
		this.ID = id;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}
	
	public abstract String getCriterio();
	
}
