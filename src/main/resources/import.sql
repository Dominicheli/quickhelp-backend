insert into usuario (nome, email, senha_hash, perfil) values ('Maria Souza', 'maria.souza@email.com', '$2a$10$hzF3FOuRCZjYVkaojZwV2OEykb4DyTHFrizf3F98VgD/OSfopY5QS', 'USUARIA');

insert into usuario (nome, email, senha_hash, perfil) values ('Ana Ribeiro', 'ana.ribeiro@quickhelp.com', '$2a$10$hzF3FOuRCZjYVkaojZwV2OEykb4DyTHFrizf3F98VgD/OSfopY5QS', 'BACKOFFICE');

insert into endereco (fk_usuario, estado, cidade, bairro, logradouro, numero, complemento) values (1, 'SP', 'São Paulo', 'Centro', 'Rua das Flores', '100', 'Apto 12');

insert into contato_emergencia (fk_usuario, nome, telefone) values (1, 'Joana Souza', '11999990000');

insert into chamado_sos (fk_usuario, criado_em, status, modo_acionamento, fk_responsavel, encerrado_em) values (1, '2026-03-10 14:30:00', 'ABERTO', 'BOTAO_SOS', 2, null);

insert into chamado_sos (fk_usuario, criado_em, status, modo_acionamento, fk_responsavel, encerrado_em) values (1, '2026-03-01 22:10:00', 'ENCERRADO', 'DISFARCADO', 2, '2026-03-01 22:40:00');

insert into localizacao_chamado (fk_chamado, latitude, longitude, precisao_metros, registrada_em) values (1, -23.550520, -46.633308, 18.0, '2026-03-10 14:31:00');

insert into localizacao_chamado (fk_chamado, latitude, longitude, precisao_metros, registrada_em) values (1, -23.551100, -46.634000, 12.5, '2026-03-10 14:36:00');

insert into mensagem_contato (nome_remetente, email_remetente, texto, enviada_em) values ('Carla Lima', 'carla.lima@email.com', 'Gostaria de saber como cadastrar um contato de emergência.', '2026-03-11 09:00:00');

insert into preferencia_usuario (fk_usuario, tema, modo_disfarcado_ativo, permite_localizacao) values (1, 'ESCURO', false, true);
