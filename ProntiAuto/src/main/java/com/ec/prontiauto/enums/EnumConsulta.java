package com.ec.prontiauto.enums;

public enum EnumConsulta {
	SKIP("SKIP", 0), TAKE("TAKE", 10);

	private String clave;
	private Integer valor;

	private EnumConsulta(String clave, Integer valor) {
		this.clave = clave;
		this.valor = valor;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}
	
	

}
