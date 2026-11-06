package br.com.fatecmaua.quickhelp.model;

public enum TemaEnum {

	CLARO("Claro"), ESCURO("Escuro");

	private String descricao;

	TemaEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

}
