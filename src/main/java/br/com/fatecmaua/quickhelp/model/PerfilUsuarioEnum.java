package br.com.fatecmaua.quickhelp.model;

public enum PerfilUsuarioEnum {

	USUARIA("Usuária"), BACKOFFICE("Backoffice");

	private String descricao;

	PerfilUsuarioEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

}
