# Clínica Veterinária - Backend

## Descrição 📝

Este é o back-end para a aplicação de gerenciamento de uma clínica veterinária. Ele utiliza **Java** que implementa um sistema básico, aplicando conceitos de **Programação Orientada a Objetos (POO)**, incluindo padrões de projeto como **DAO (Data Access Object)**, **Facade**, e **Adapter**, além de tratamento de exceções e logging com **SLF4J/Logback**.

## Índice 🔗

- [Descrição](#descrição-📝)
- [Estrutura do Projeto](#estrutura-do-projeto-🗂)
- [Funcionalidades Implementadas](#funcionalidades-implementadas-🛠️)
- [Funcionalidades Previstas](#funcionalidades-previstas-🔮)
- [Classes e Componentes Principais](#classes-e-componentes-principais-📚)
- [Requisitos](#requisitos-📋)
- [Como Executar](#como-executar-🚀)
- [Contribuição](#contribuição-🤝)
- [Autores](#autores-👤)

## Estrutura do Projeto 🗂

```plaintext

ClinicaVeterinaria
└───src
├───main
│   ├───java
│   │   └───br
│   │       └───com
│   │           └───fatecmogidascruzes
│   │               ├───adapters              \# Camada de Adapters para conversão de objetos (Ex: Entidade para DTO)
│   │               │   └── DonoAdapter.java
│   │               ├───builders              \# Classes Builder para construção de entidades de forma fluente
│   │               │   ├── ConsultaBuilder.java
│   │               │   ├── DiagnosticoBuilder.java
│   │               │   ├── PetBuilder.java
│   │               │   └── PrescricaoBuilder.java
│   │               ├───dao                   \# Interfaces de Data Access Object (DAO)
│   │               │   ├── ConsultaDAO.java
│   │               │   ├── DAO.java
│   │               │   ├── DiagnosticoDAO.java
│   │               │   ├── DonoDAO.java
│   │               │   ├── PetDAO.java
│   │               │   ├── PrescricaoDAO.java
│   │               │   └── VeterinarioDAO.java
│   │               ├───dao
│   │               │   └── impl              \# Implementações das interfaces DAO
│   │               │       ├── ConsultaDAOImpl.java
│   │               │       ├── DiagnosticoDAOImpl.java
│   │               │       ├── DonoDAOImpl.java
│   │               │       ├── PetDAOImpl.java
│   │               │       ├── PrescricaoDAOImpl.java
│   │               │       └── VeterinarioDAOImpl.java
│   │               ├───dtos                  \# Data Transfer Objects (DTOs) para a camada de apresentação
│   │               │   └── DonoComDetalhes.java
│   │               ├───entities              \# Entidades de domínio que mapeiam as tabelas do banco de dados
│   │               │   ├── Consulta.java
│   │               │   ├── Diagnostico.java
│   │               │   ├── Dono.java
│   │               │   ├── Pet.java
│   │               │   ├── Prescricao.java
│   │               │   ├── ServicoVeterinario.java
│   │               │   └── Veterinario.java
│   │               ├───exceptions            \# Exceções personalizadas para tratamento de erros de negócio
│   │               │   └── DadoDuplicadoException.java
│   │               ├───facades               \# Camada de Facade para orquestração de operações de negócio
│   │               │   └── ClinicaVeterinariaFacade.java
│   │               ├───factories             \# Fábricas de objetos (Ex: Conexão com o banco de dados)
│   │               │   ├── ConnectionFactorySingleton.java
│   │               │   ├── ConsultaFactory.java
│   │               │   ├── DiagnosticoFactory.java
│   │               │   ├── PetFactory.java
│   │               │   ├── PrescricaoFactory.java
│   │               │   └── ServicoFactory.java
│   │               ├───interacoes            \# Classes para interação com o usuário (Ex: Menu interativo pelo console)
│   │               │   ├── EscolherEntidade.java
│   │               │   └── Menu.java
│   │               └───Main.java             \# Classe principal que inicia a aplicação
│   └───resources
│       └── schema.sql                        \# Script SQL para criação do schema do banco de dados
└───test
└───java

```

## Funcionalidades Implementadas 🛠️

Nesta fase do projeto, foram implementadas as seguintes funcionalidades:

- **Mapeamento de Entidades:** Definição das classes que representam as entidades principais (Dono, Veterinario, Pet, Consulta, Diagnostico, Prescricao, ServicoVeterinario) com seus respectivos atributos, métodos getters e setters.
- **Padrão DAO:** Implementação de interfaces e classes concretas (`*DAO` e `*DAOImpl`) para abstrair o acesso a dados, permitindo operações CRUD (Criar, Ler, Atualizar, Deletar) para cada entidade. Inclui a nova interface `DAO.java` para operações genéricas.
- **Padrão Builder:** Utilização de Builders para facilitar a criação de objetos complexos (entidades como Consulta, Diagnostico, Pet, Prescricao) de forma mais legível e com menos construtores.
- **Tratamento de Exceções:** Implementação de exceções personalizadas (`DadoDuplicadoException`) e tratamento robusto de `SQLException`, com mensagens amigáveis e logging detalhado.
- **Logging:** Configuração e uso de **SLF4J/Logback** para registrar eventos importantes da aplicação (erros, avisos, informações).
- **Padrão Facade:** Criação da `ClinicaVeterinariaFacade` para simplificar a interface de uso para a camada de apresentação, orquestrando as operações de negócio e gerenciando as interações com os DAOs.
- **Padrão Adapter:** Implementação de `DonoAdapter` para converter entidades do banco de dados (Dono) em DTOs mais ricos (`DonoComDetalhes`), que incluem dados de entidades relacionadas (como a lista de `Pets` de um `Dono`), facilitando a exibição na interface do usuário.
- **Menu Interativo:** Um menu de console (`Menu.java`) que permite ao usuário interagir com as funcionalidades de cadastro, agendamento e listagem da clínica. A classe `EscolherEntidade.java` auxilia na seleção de entidades.
- **Conexão com Banco de Dados:** Utilização de `ConnectionFactorySingleton` para gerenciar a conexão com o banco de dados.
- **Fábricas de Entidades:** Implementação de `ConsultaFactory`, `DiagnosticoFactory`, `PetFactory`, `PrescricaoFactory` e `ServicoFactory` para encapsular a lógica de criação de instâncias de entidades, promovendo a coesão.

## Funcionalidades Previstas 🔮

### Atendimento de Pets 📝

- Agendamento de Consultas (Implementado o básico)
- Registro de Consultas (Implementado o básico)
- Histórico Médico do Pet (Pode ser melhorado com listagens mais detalhadas)

### Diagnóstico e Prescrição 📜💊

- Diagnóstico de Doenças (Implementado o básico)
- Prescrição de Medicamentos (Implementado o básico)
- Controle de Dosagens

### Próximos Passos

- Implementar operações de `update` e `delete` para todas as entidades no `Menu`.
- Expandir as funcionalidades de busca e filtragem.
- Adicionar mais validações de negócio na camada Facade.
- Possivelmente migrar para um framework web (Spring Boot, etc.) para uma interface mais robusta.

## Classes e Componentes Principais 📚

- **entities**
  Representam as tabelas do banco de dados e os objetos de domínio.

  - `Dono`: Informações do proprietário do pet.
  - `Veterinario`: Dados dos profissionais veterinários.
  - `Pet`: Informações detalhadas sobre os animais de estimação.
  - `Consulta`: Registros de agendamentos e detalhes das consultas.
  - `Diagnostico`: Detalhes sobre os diagnósticos realizados.
  - `Prescricao`: Informações sobre medicamentos receitados.
  - `ServicoVeterinario`: Interface para serviços gerais da clínica (pode ser expandido).

- **dao** e **dao.impl**
  Implementam o padrão DAO para persistência de dados. Cada entidade tem sua interface DAO e uma classe de implementação. A interface `DAO.java` define operações CRUD genéricas.

- **builders**
  Classes auxiliares para construir instâncias de entidades de forma mais estruturada: `ConsultaBuilder`, `DiagnosticoBuilder`, `PetBuilder`, `PrescricaoBuilder`.

- **factories**

  - `ConnectionFactorySingleton`: Gerencia a conexão com o banco de dados, garantindo uma única instância da fábrica de conexões.
  - `ConsultaFactory`, `DiagnosticoFactory`, `PetFactory`, `PrescricaoFactory`, `ServicoFactory`: Encapsulam a lógica de criação de instâncias das respectivas entidades.

- **exceptions**
  `DadoDuplicadoException`: Exceção personalizada para tratar violações de unicidade no banco de dados.

- **facades**
  `ClinicaVeterinariaFacade`: Uma camada de serviço que orquestra as operações de negócio, interagindo com múltiplos DAOs e simplificando o uso para a camada de apresentação.

- **dtos**
  `DonoComDetalhes`: Um Data Transfer Object que encapsula informações de `Dono` e seus `Pets`, usado para apresentar dados combinados.

- **adapters**
  `DonoAdapter`: Componente que "adapta" a entidade `Dono` para o DTO `DonoComDetalhes`, realizando a lógica de buscar e incluir os pets relacionados.

- **interacoes**

  - `Menu`: A interface de console para interação com o usuário.
  - `EscolherEntidade`: Classe auxiliar para permitir a seleção de entidades no menu interativo.

- **Main**
  Classe principal que inicia a aplicação e o menu interativo.

## Requisitos 📋

- Java 22
- Maven (Recomendado para gerenciamento de dependências, incluindo SLF4J/Logback e o driver JDBC do seu banco de dados, ex: H2)
- Um banco de dados JDBC (ex: H2 Database) configurado. O script `schema.sql` em `src/main/resources` é utilizado para criar as tabelas do banco de dados na primeira execução.

## Como Executar 🚀

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/pokernol/clinica-veterinaria-api.git](https://github.com/pokernol/clinica-veterinaria-api.git)
    ```
2.  **Navegue até o diretório do projeto:**
    ```bash
    cd clinica-veterinaria-api
    ```
3.  **Configurar Dependências (usando Maven - recomendado):** Se você estiver usando Maven, certifique-se de que o arquivo `pom.xml` está configurado corretamente. Em seguida, compile o projeto:

    ```bash
    mvn clean install
    ```

    Isso baixará as dependências e empacotará o projeto.

4.  **Configurar o Banco de Dados:**
    Certifique-se de que o seu banco de dados está acessível. O arquivo `src/main/resources/schema.sql` será executado na inicialização da conexão para criar as tabelas. Para H2 (modo arquivo, por exemplo), a URL de conexão pode ser similar a:

    ```
    jdbc:h2:./data/clinicavet;AUTO_SERVER=TRUE;INIT=RUNSCRIPT FROM 'classpath:schema.sql'
    ```

    As credenciais do banco de dados (usuário/senha) podem ser configuradas via código na `ConnectionFactorySingleton` ou, para projetos mais avançados, como parâmetros de VM ou um arquivo de configuração específico (não mais `database.properties`).

5.  **Executar a Aplicação:**
    - **Via Maven:**
      ```bash
      mvn exec:java -Dexec.mainClass="br.com.fatecmogidascruzes.Main"
      ```
    - **Via JAR (após `mvn clean install`):**
      ```bash
      java -jar target/clinica-veterinaria-api-1.0-SNAPSHOT.jar # O nome do JAR pode variar
      ```
    - **Via IDE (IntelliJ IDEA, Eclipse):** Importe o projeto Maven para sua IDE e execute a classe `br.com.fatecmogidascruzes.Main`.

## Contribuição 🤝

- Faça um fork do projeto.
- Crie uma branch para sua feature (`git checkout -b feature/nova-feature`).
- Commit suas mudanças (`git commit -am 'Adiciona nova feature'`).
- Faça o push para a branch (`git push origin feature/nova-feature`).
- Abra um Pull Request.

## Autores 👤

| [Leonardo Vinícius](https://www.linkedin.com/in/leonardo-vin%C3%ADcius25/)                                                                                                       | [Vinicius Jose Alabarce](https://www.linkedin.com/in/viniciusjoseab/)                                                                                                       |
| -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| ![Leonardo](https://avatars.githubusercontent.com/u/100011077?v=4&s=200)                                                                                                         | ![Vinicius](https://avatars.githubusercontent.com/u/163073329?v=4&s=200)                                                                                                    |
| [![Linkedin Badge](https://img.shields.io/badge/-Leonardo_Vinícius-blue?style=flat-square&logo=Linkedin&logoColor=white)](https://www.linkedin.com/in/leonardo-vin%C3%ADcius25/) | [![Linkedin Badge](https://img.shields.io/badge/-Vinicius_Jose_Alabarce-blue?style=flat-square&logo=Linkedin&logoColor=white)](https://www.linkedin.com/in/viniciusjoseab/) |
| [![Linkedin Badge linkedin](https://img.shields.io/badge/-Leonardo_Vinícius-39E09B?style=flat-square&logo=linktree&logoColor=white)](https://linktr.ee/pokernol)                 |                                                                                                                                                                             |
