package br.com.fatecmaua.quickhelp.model;

public enum AcionamentoEnum {

	BOTAO_SOS("Botão SOS"), DISFARCADO("Disfarçado");

	private String descricao;

	AcionamentoEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

}
