package it.polito.tdp.metroparis.model;

import java.util.Objects;

//Nuovo oggetto di appoggio per il metodo 3

public class CoppieF {
	
	Fermata fermataP;
	Fermata fermataA;
	
	public CoppieF(Fermata fermataP, Fermata fermataA) {
		super();
		this.fermataP = fermataP;
		this.fermataA = fermataA;
	}
	
	public Fermata getFermataP() {
		return fermataP;
	}
	public void setFermataP(Fermata fermataP) {
		this.fermataP = fermataP;
	}
	public Fermata getFermataA() {
		return fermataA;
	}
	public void setFermataA(Fermata fermataA) {
		this.fermataA = fermataA;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fermataA, fermataP);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CoppieF other = (CoppieF) obj;
		return Objects.equals(fermataA, other.fermataA) && Objects.equals(fermataP, other.fermataP);
	}
	

	
}
