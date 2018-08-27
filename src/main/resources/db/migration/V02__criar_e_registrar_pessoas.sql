CREATE TABLE pessoa(
	codigo BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    ativo BOOLEAN NOT NULL,
    logradouro VARCHAR(255),
    numero VARCHAR(10),
    complemento VARCHAR(255),
    bairro VARCHAR(50),
    cep VARCHAR(10),
    cidade VARCHAR(50),
    estado VARCHAR(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO pessoa(nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('João das Couves', true, 'Rua Magnólia', '500', 'Próximo à igreja batista', 'Vila Marina', '09176-203', 'Santo André', 'São Paulo');
INSERT INTO pessoa(nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('Maria Clotilde', true, 'Avenida Jão Domingues', '1362', null, 'Jardim das Flores', '09253-100', 'Santo Amaro', 'São Paulo');
INSERT INTO pessoa(nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('Bruna Paola de Almeida Pauluci', 1, 'Rua das Camélias', '203', 'Travessa com a Luiz Silva', 'Vila Marina', '09176-200', 'Santo André', 'São Paulo');
INSERT INTO pessoa(nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('Ricardo da Silva', false, 'Avenida Bandeirantes', '3542', 'Próximo ao aeroporto', 'Vila Matilda', '09155-211', 'São Paulo', 'São Paulo');
INSERT INTO pessoa(nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('Allan Tadares', true, 'Rua Firmino Paixão', '300', null, 'Jardim Pistache', '08543-302', 'Santo André', 'São Paulo');
INSERT INTO pessoa(nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('Lurdes Miranda', true, 'Rua das Coisas', '800', null, 'Vila Leopoldina', '05184-233', 'São Paulo', 'São Paulo');
INSERT INTO pessoa(nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('Armando Ferrone', false, 'Travessa dos Encravados', '1400', 'Rua sem saída', 'Vila Matilda', '04758-654', 'São Bernardo do Campo', 'São Paulo');
INSERT INTO pessoa(nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('Cristiano Dilpado', true, 'Rua Estonio', '700', null, 'Vila Mariana', '04444-741', 'São Paulo', 'São Paulo');
INSERT INTO pessoa(nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('Fernando Quente', false, 'Avenida Consolação', '2500', 'Próximo à estação do metro Consolação', 'Consolação', '01234-123', 'São Paulo', 'São Paulo');
INSERT INTO pessoa(nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('Aline Guilda', true, 'Rua das Violetas', '750', null, 'Vila Marina', '09256-125', 'Santo André', 'São Paulo');