CREATE TABLE usuario (
	codigo BIGINT(20) NOT NULL PRIMARY KEY,
	nome VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	senha VARCHAR(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE permissao (
	codigo BIGINT(20) NOT NULL PRIMARY KEY,
	descricao VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE usuario_permissao (
	codigo_usuario BIGINT(20) NOT NULL,
	codigo_permissao BIGINT(20) NOT NULL,
	PRIMARY KEY (codigo_usuario, codigo_permissao),
	FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo),
	FOREIGN KEY (codigo_permissao) REFERENCES permissao(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

INSERT INTO usuario (codigo, nome, email, senha) VALUES (1, 'Administrador', 'admin@algamoney.com', '$2a$10$uzPEvg2aOgs3bk2z5gIVt.53vO26Oj99VCqqBjiDID5s/N3i/RCOS');
INSERT INTO usuario (codigo, nome, email, senha) VALUES (2, 'Maria Silva', 'maria@algamoney.com', '$2a$10$DNEp1PEvYiG1tj/61FnY4etJUmE.6hs8NMvWJPfWiZ8fnMZa6iKle');

INSERT INTO permissao (codigo, descricao) VALUES (1, 'ROLE_CADASTRAR_CATEGORIA');
INSERT INTO permissao (codigo, descricao) VALUES (2, 'ROLE_PESQUISAR_CATEGORIA');

INSERT INTO permissao (codigo, descricao) VALUES (3, 'ROLE_CADASTRAR_PESSOA');
INSERT INTO permissao (codigo, descricao) VALUES (4, 'ROLE_REMOVER_PESSOA');
INSERT INTO permissao (codigo, descricao) VALUES (5, 'ROLE_PESQUISAR_PESSOA');

INSERT INTO permissao (codigo, descricao) VALUES (6, 'ROLE_CADASTRAR_LANCAMENTO');
INSERT INTO permissao (codigo, descricao) VALUES (7, 'ROLE_REMOVER_LANCAMENTO');
INSERT INTO permissao (codigo, descricao) VALUES (8, 'ROLE_PESQUISAR_LANCAMENTO');

INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) VALUES (1, 1);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) VALUES (1, 2);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) VALUES (1, 3);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) VALUES (1, 4);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) VALUES (1, 5);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) VALUES (1, 6);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) VALUES (1, 7);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) VALUES (1, 8);

INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) VALUES (2, 2);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) VALUES (2, 5);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) VALUES (2, 8);