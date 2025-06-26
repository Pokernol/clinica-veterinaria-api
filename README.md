# Clínica Veterinária - Backend

## Descrição 📝
Este é o back-end para a aplicação de gerenciamento de uma clínica veterinária. Ele utiliza **Java** que implementa um sistema básico, aplicando conceitos de **Programação Orientada a Objetos (POO)**, incluindo padrões de projeto como **DAO (Data Access Object)**, **Facade**, e **Adapter**, além de um robusto tratamento de exceções e logging com **SLF4J/Logback**.

## Índice 🔗

* [Título](#clínica-veterinária---backend)
* [Descrição](#descrição-)
* [Estrutura do Projeto](#estrutura-do-projeto-)
* [Funcionalidades Implementadas](#funcionalidades-implementadas-%EF%B8%8F)
* [Funcionalidades Previstas](#funcionalidades-previstas-%EF%B8%8F)
* [Classes e Componentes Principais](#classes-e-componentes-principais-%F0%9F%93%9A)
* [Requisitos](#requisitos-%F0%9F%93%8B)
* [Como Executar](#como-executar-%F0%9F%9A%80)
* [Contribuição](#contribuição-%F0%9F%A4%9D)
* [Autores](#autores-%F0%9F%91%A4)

## Estrutura do Projeto 🗂
```
ClinicaVeterinaria
├───src
│   ├───main
│   │   ├───java
│   │   │   └───br
│   │   │       └───com
│   │   │           └───fatecmogidascruzes
│   │   │               │   Main.java
│   │   │               │
│   │   │               ├───adapters
│   │   │               │       DonoAdapter.java
│   │   │               │
│   │   │               ├───builders
│   │   │               │       ConsultaBuilder.java
│   │   │               │       DiagnosticoBuilder.java
│   │   │               │       PetBuilder.java
│   │   │               │       PrescricaoBuilder.java
│   │   │               │
│   │   │               ├───dao
│   │   │               │   │   ConsultaDAO.java
│   │   │               │   │   DAO.java
│   │   │               │   │   DiagnosticoDAO.java
│   │   │               │   │   DonoDAO.java
│   │   │               │   │   PetDAO.java
│   │   │               │   │   PrescricaoDAO.java
│   │   │               │   │   VeterinarioDAO.java
│   │   │               │   │
│   │   │               │   └───impl
│   │   │               │           ConsultaDAOImpl.java
│   │   │               │           DiagnosticoDAOImpl.java
│   │   │               │           DonoDAOImpl.java
│   │   │               │           PetDAOImpl.java
│   │   │               │           PrescricaoDAOImpl.java
│   │   │               │           VeterinarioDAOImpl.java
│   │   │               │
│   │   │               ├───dtos
│   │   │               │       DonoComDetalhes.java
│   │   │               │
│   │   │               ├───entities
│   │   │               │       Consulta.java
│   │   │               │       Diagnostico.java
│   │   │               │       Dono.java
│   │   │               │       Pet.java
│   │   │               │       Prescricao.java
│   │   │               │       ServicoVeterinario.java
│   │   │               │       Veterinario.java
│   │   │               │
│   │   │               ├───exceptions
│   │   │               │       DadoDuplicadoException.java
│   │   │               │
│   │   │               ├───facades
│   │   │               │       ClinicaVeterinariaFacade.java
│   │   │               │
│   │   │               ├───factories
│   │   │               │       ConnectionFactorySingleton.java
│   │   │               │       ConsultaFactory.java
│   │   │               │       DiagnosticoFactory.java
│   │   │               │       PetFactory.java
│   │   │               │       PrescricaoFactory.java
│   │   │               │       ServicoFactory.java
│   │   │               │
│   │   │               └───interacoes
│   │   │                       EscolherEntidade.java
│   │   │                       Menu.java
│   │   │
│   │   └───resources
│   │           schema.sql
│   │
│   └───test
│       └───java
└───target
    │   ClinicaVeterinaria-1.0-SNAPSHOT.jar
```

## Funcionalidades Implementadas 🛠️
Nesta fase do projeto, foram implementadas as seguintes funcionalidades:
* **Mapeamento de Entidades:** Definição das classes que representam as entidades principais (`Dono`, `Veterinario`, `Pet`, `Consulta`, `Diagnostico`, `Prescricao`) com seus respectivos atributos, métodos getters e setters.
* **Padrão DAO:** Implementação de interfaces e classes concretas (`*DAO` e `*DAOImpl`) para abstrair o acesso a dados, permitindo operações CRUD (Criar, Ler, Atualizar, Deletar) para cada entidade.
* **Padrão Builder:** Utilização de Builders para facilitar a criação de objetos complexos (entidades) de forma mais legível e com menos construtores.
* **Tratamento de Exceções:** Implementação de exceções personalizadas (`DadoDuplicadoException`) e tratamento robusto de `SQLException`, com mensagens amigáveis e logging detalhado.
* **Logging:** Configuração e uso de **SLF4J/Logback** para registrar eventos importantes da aplicação (erros, avisos, informações).
* **Padrão Facade:** Criação da `ClinicaVeterinariaFacade` para simplificar a interface de uso para a camada de apresentação, orquestrando as operações de negócio e gerenciando as interações com os DAOs.
* **Padrão Adapter:** Implementação de `DonoAdapter` para converter entidades do banco de dados (`Dono`) em DTOs mais ricos (`DonoComDetalhes`), que incluem dados de entidades relacionadas (como a lista de `Pets` de um `Dono`), facilitando a exibição na interface do usuário.
* **Menu Interativo:** Um menu de console (`Menu.java`) que permite ao usuário interagir com as funcionalidades de cadastro, agendamento e listagem da clínica.
* **Conexão com Banco de Dados:** Utilização de `ConnectionFactorySingleton` para gerenciar a conexão com o banco de dados (idealmente H2, SQLite ou similar para simplicidade local).

## Funcionalidades Previstas 🔮

### Atendimento de Pets 📝
- **Agendamento de Consultas** (Implementado o básico)
- **Registro de Consultas** (Implementado o básico)
- **Histórico Médico do Pet** (Pode ser melhorado com listagens mais detalhadas)

### Diagnóstico e Prescrição 📜💊
- **Diagnóstico de Doenças** (Implementado o básico)
- **Prescrição de Medicamentos** (Implementado o básico)
- **Controle de Dosagens**

### Próximos Passos
- Implementar operações de `update` e `delete` para todas as entidades no `Menu`.
- Expandir as funcionalidades de busca e filtragem.
- Adicionar mais validações de negócio na camada Facade.
- Possivelmente migrar para um framework web (Spring Boot, etc.) para uma interface mais robusta.

## Classes e Componentes Principais 📚

### `entities`
Representam as tabelas do banco de dados e os objetos de domínio.
- `Dono`: Informações do proprietário do pet.
- `Veterinario`: Dados dos profissionais veterinários.
- `Pet`: Informações detalhadas sobre os animais de estimação.
- `Consulta`: Registros de agendamentos e detalhes das consultas.
- `Diagnostico`: Detalhes sobre os diagnósticos realizados.
- `Prescricao`: Informações sobre medicamentos receitados.
- `ServicoVeterinario`: Interface para serviços gerais da clínica (pode ser expandido).

### `dao` e `dao.impl`
Implementam o padrão DAO para persistência de dados. Cada entidade tem sua interface DAO e uma classe de implementação.

### `builders`
Classes auxiliares para construir instâncias de entidades de forma mais estruturada.

### `factories`
- `ConnectionFactorySingleton`: Gerencia a conexão com o banco de dados, garantindo uma única instância da fábrica de conexões.

### `exceptions`
- `DadoDuplicadoException`: Exceção personalizada para tratar violações de unicidade no banco de dados.

### `facades`
- `ClinicaVeterinariaFacade`: Uma camada de serviço que orquestra as operações de negócio, interagindo com múltiplos DAOs e simplificando o uso para a camada de apresentação.

### `dtos`
- `DonoComDetalhes`: Um Data Transfer Object que encapsula informações de `Dono` e seus `Pets`, usado para apresentar dados combinados.

### `adapters`
- `DonoAdapter`: Componente que "adapta" a entidade `Dono` para o DTO `DonoComDetalhes`, realizando a lógica de buscar e incluir os pets relacionados.

### `interacoes`
- `Menu`: A interface de console para interação com o usuário.

### `Main`
Classe principal que inicia a aplicação e o menu interativo.

## Requisitos 📋

- **Java 22**
- **Maven** (Recomendado para gerenciamento de dependências, incluindo SLF4J/Logback e o driver JDBC do seu banco de dados, ex: H2)
- Um banco de dados JDBC (ex: H2 Database) configurado e acessível pelas propriedades em `src/main/resources/database.properties`.

## Como Executar 🚀

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/pokernol/clinica-veterinaria-api.git](https://github.com/pokernol/clinica-veterinaria-api.git)
    ```
2.  **Navegue até o diretório do projeto:**
    ```bash
    cd clinica-veterinaria-api
    ```
3.  **Configurar Dependências (usando Maven - recomendado):**
    Se você estiver usando Maven (altamente recomendado para gerenciar as dependências de SLF4J/Logback, H2, etc.), certifique-se de que o arquivo `pom.xml` está configurado corretamente. Em seguida, compile o projeto:
    ```bash
    mvn clean install
    ```
    Isso baixará as dependências e empacotará o projeto.

4.  **Configurar o Banco de Dados:**
    * Certifique-se de que seu arquivo `src/main/resources/database.properties` está configurado com as credenciais corretas para o seu banco de dados (ex: H2 em modo arquivo ou memória).
    * Exemplo de `database.properties` para H2 (modo arquivo):
        ```properties
        db.url=jdbc:h2:./data/clinicavet;AUTO_SERVER=TRUE;INIT=RUNSCRIPT FROM 'classpath:schema.sql'
        db.username=sa
        db.password=
        ```
    * Você também precisará de um arquivo `schema.sql` em `src/main/resources` para criar as tabelas do banco de dados na primeira execução (se usar `INIT=RUNSCRIPT`).

5.  **Executar a Aplicação:**
    * **Via Maven:**
        ```bash
        mvn exec:java -Dexec.mainClass="br.com.fatecmogidascruzes.Main"
        ```
    * **Via JAR (após `mvn clean install`):**
        ```bash
        java -jar target/clinica-veterinaria-api-1.0-SNAPSHOT.jar # O nome do JAR pode variar
        ```
    * **Via IDE (IntelliJ IDEA, Eclipse):**
        Importe o projeto Maven para sua IDE e execute a classe `br.com.fatecmogidascruzes.Main`.

## Contribuição 🤝

1.  Faça um fork do projeto.
2.  Crie uma branch para sua feature (`git checkout -b feature/nova-feature`).
3.  Commit suas mudanças (`git commit -am 'Adiciona nova feature'`).
4.  Faça o push para a branch (`git push origin feature/nova-feature`).
5.  Abra um Pull Request.

## Autores 👤

| [Leonardo Vinícius](https://www.linkedin.com/in/leonardo-vin%C3%ADcius25/)| [Vinicius Jose Alabarce](https://www.linkedin.com/in/viniciusjoseab/)|
|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| ![Leonardo](https://avatars.githubusercontent.com/u/100011077?v=4&s=200)| ![Vinicius](https://media.licdn.com/dms/image/v2/D4D03AQEQsvWxTFhmQQ/profile-displayphoto-shrink_200_200/profile-displayphoto-shrink_200_200/0/1706135886069?e=1748476800&v=beta&t=BHNz6htvJaK6V-Yzs8OdtNMl6L_D5UGj46m-axbRgy4)|
| [![Linkedin Badge](https://img.shields.io/badge/-Leonardo_Vinícius-blue?style=flat-square&logo=Linkedin&logoColor=white)](https://www.linkedin.com/in/leonardo-vin%C3%ADcius25/) | [![Linkedin Badge](https://img.shields.io/badge/-Vinicius_Jose_Alabarce-blue?style=flat-square&logo=Linkedin&logoColor=white)](https://www.linkedin.com/in/viniciusjoseab/)|  
| [![Linkedin Badge linkedin](https://img.shields.io/badge/-Leonardo_Vinícius-39E09B?style=flat-square&logo=linktree&logoColor=white)](https://linktr.ee/pokernol)||
```
