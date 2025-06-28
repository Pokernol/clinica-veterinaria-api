-- Garante que as tabelas são removidas na ordem correta para evitar problemas de chave estrangeira
DROP TABLE IF EXISTS Prescricoes;
DROP TABLE IF EXISTS Diagnosticos;
DROP TABLE IF EXISTS Consultas;
DROP TABLE IF EXISTS Pets;
DROP TABLE IF EXISTS Veterinarios;
DROP TABLE IF EXISTS Donos;

-- Criação da tabela Donos
CREATE TABLE Donos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(255)
);

-- Criação da tabela Veterinarios
CREATE TABLE Veterinarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    crmv VARCHAR(50) UNIQUE NOT NULL,
    especialidade VARCHAR(255)
);

-- Criação da tabela Pets
CREATE TABLE Pets (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    especie VARCHAR(255),
    raca VARCHAR(255),
    dataNascimento DATE,
    donoId INT,
    FOREIGN KEY (donoId) REFERENCES Donos(id)
);

-- Criação da tabela Consultas (ainda sem a FK para Prescricoes neste momento)
CREATE TABLE Consultas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    petId INT NOT NULL,
    veterinarioId INT NOT NULL,
    dataHora TIMESTAMP NOT NULL,
    motivoConsulta VARCHAR(500),
    diagnosticoId INT,
    prescricaoId INT,
    FOREIGN KEY (petId) REFERENCES Pets(id),
    FOREIGN KEY (veterinarioId) REFERENCES Veterinarios(id)
);

-- Criação da tabela Diagnosticos
CREATE TABLE Diagnosticos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    petId INT NOT NULL,
    consultaId INT,
    descricao VARCHAR(1000) NOT NULL,
    gravidade VARCHAR(50),
    FOREIGN KEY (petId) REFERENCES Pets(id),
    FOREIGN KEY (consultaId) REFERENCES Consultas(id)
);

-- Criação da tabela Prescricoes
CREATE TABLE Prescricoes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    petId INT NOT NULL,
    veterinarioId INT NOT NULL,
    consultaId INT,
    medicamento VARCHAR(255) NOT NULL,
    dosagem VARCHAR(255),
    instrucoesUso VARCHAR(1000),
    FOREIGN KEY (petId) REFERENCES Pets(id),
    FOREIGN KEY (veterinarioId) REFERENCES Veterinarios(id),
    FOREIGN KEY (consultaId) REFERENCES Consultas(id)
);

ALTER TABLE Consultas
ADD CONSTRAINT FK_ConsultaPrescricao FOREIGN KEY (prescricaoId)
REFERENCES Prescricoes(id);

ALTER TABLE Consultas
ADD CONSTRAINT FK_ConsultaDiagnostico FOREIGN KEY (diagnosticoId)
REFERENCES Diagnosticos(id);