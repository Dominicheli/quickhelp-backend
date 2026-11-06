package br.com.fatecmaua.quickhelp.model;

public enum StatusChamadoEnum {

	ABERTO("Aberto"), EM_ATENDIMENTO("Em atendimento"),
	ENCERRADO("Encerrado"), CANCELADO("Cancelado");

	private String descricao;

	StatusChamadoEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

}
