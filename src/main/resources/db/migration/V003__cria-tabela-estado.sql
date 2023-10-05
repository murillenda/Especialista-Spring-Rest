CREATE TABLE estado (
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        nome VARCHAR(80) NOT NULL,

                        PRIMARY KEY (id)
) engine=InnoDB default charset=utf8;

INSERT INTO estado (nome)
SELECT DISTINCT nome_estado FROM cidade;

ALTER TABLE cidade add column estado_id BIGINT NOT NULL;

UPDATE cidade c set c.estado_id = (SELECT e.id FROM estado e where e.nome = c.nome_estado);

ALTER TABLE cidade add constraint fk_cidade_estado
    FOREIGN KEY (estado_id) references estado(id);

ALTER TABLE cidade DROP COLUMN nome_estado;

ALTER TABLE cidade CHANGE nome_cidade nome VARCHAR(80) NOT NULL;