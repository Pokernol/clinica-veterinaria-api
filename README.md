# Clínica Veterinária - Backend

## Descrição 📝
Este é o back-end para a aplicação de gerenciamento de uma clínica veterinária. Ele utiliza **Java** que implementa um sistema básico, utilizando **Programação Orientada a Objetos (POO)**.

## Índice 🔗

=================

* [Título](#clínica-veterinária-api)
* [Descrição](#descrição)
* [Estrutura do Projeto](#estrutura-do-projeto)
* [Entrega](#entrega)
* [Funcionalidades Previstas](#funcionalidades-previstas)
* [Classes Principais](#classes-principais)
* [Requisitos](#requisitos)
* [Como Executar](#como-executar)
* [Contribuição](#contribuição)
* [Autores](#autores)

=================

## Estrutura do Projeto 🗂
```
ClinicaVeterinaria
└───src
    ├───main
    │   ├───java
    │   │   └───br
    │   │       └───com
    │   │           └───fatecmogidascruzes
    │   │               ├───entities
    │   │               │   ├── Consulta.java
    │   │               │   ├── Diagnostico.java
    │   │               │   ├── Pet.java
    │   │               │   ├── Prescricao.java
    │   │               │   └── ServicoVeterinario.java
    │   │               └── Main.java
    │   └───resources
    └───test
        └───java
```

## Entrega 📦
Nesta entrega, foram desenvolvidas as classes que representam as entidades principais com seus respectivos atributos, métodos getters e setters, além da aplicação dos conceitos de herança e polimorfismo.

## Funcionalidades Previstas 🔮

### Atendimento de Pets 📝
- **Agendamento de Consultas**
- **Registro de Consultas**
- **Histórico Médico do Pet**

### Diagnóstico e Prescrição 📜💊
- **Diagnóstico de Doenças**
- **Prescrição de Medicamentos**
- **Controle de Dosagens**

## Classes Principais 📚

### Pet
Representa um animal de estimação no sistema.

### ServicoVeterinario
Interface que define um comportamento comum para os serviços veterinários.

### Consulta
Representa um agendamento de consulta para um pet com um veterinário.

### Diagnostico
Armazena diagnósticos realizados pelos veterinários.

### Prescricao
Guarda informações sobre medicamentos receitados e suas dosagens.

### Main
Classe principal que inicia a aplicação.

## Requisitos 📋

- **Java 21**

## Como Executar 🚀

1. Clone o repositório:
   ```bash
   git clone https://github.com/pokernol/clinica-veterinaria-api.git
   ```
2. Navegue até o diretório do projeto:
   ```bash
   cd clinica-veterinaria-api
   ```
3. Compile o código:
   ```bash
   javac Main.java
   ```
4. Execute a aplicação:
   ```bash
   java Main
   ```

## Contribuição 🤝

1. Faça um fork do projeto.
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`).
3. Commit suas mudanças (`git commit -am 'Adiciona nova feature'`).
4. Faça o push para a branch (`git push origin feature/nova-feature`).
5. Abra um Pull Request.

## Autores 👤

| [Leonardo Vinícius](https://www.linkedin.com/in/leonardo-vin%C3%ADcius25/)| [Vinicius Jose Alabarce](https://www.linkedin.com/in/viniciusjoseab/)|
|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| ![Leonardo](https://avatars.githubusercontent.com/u/100011077?v=4&s=200)| ![Vinicius](https://media.licdn.com/dms/image/v2/D4D03AQEQsvWxTFhmQQ/profile-displayphoto-shrink_200_200/profile-displayphoto-shrink_200_200/0/1706135886069?e=1748476800&v=beta&t=BHNz6htvJaK6V-Yzs8OdtNMl6L_D5UGj46m-axbRgy4)|
| [![Linkedin Badge](https://img.shields.io/badge/-Leonardo_Vinícius-blue?style=flat-square&logo=Linkedin&logoColor=white)](https://www.linkedin.com/in/leonardo-vin%C3%ADcius25/) | [![Linkedin Badge](https://img.shields.io/badge/-Vinicius_Jose_Alabarce-blue?style=flat-square&logo=Linkedin&logoColor=white)](https://www.linkedin.com/in/viniciusjoseab/)|  
| [![Linkedin Badge linkedin](https://img.shields.io/badge/-Leonardo_Vinícius-39E09B?style=flat-square&logo=linktree&logoColor=white)](https://linktr.ee/pokernol)||
