package com.ec.prontiauto.exception;

public class ExceptionResponse {

	private String mensaje;
	private String codigo;

	public ExceptionResponse() {
		super();
	}

	public ExceptionResponse(String codigo, String mensaje) {
		super();
		this.codigo = codigo;
		this.mensaje = mensaje;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	
}
