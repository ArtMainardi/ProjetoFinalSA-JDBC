# 📦 SkiloBox

### Sistema de Gerenciamento de Estoques desenvolvido com Java e JDBC

> **Projeto Final — UC Banco de Dados**
> Tema: **ERP com JDBC**
> Área de aplicação: **Gerenciamento de Estoques e Almoxarifado**

---

## 📌 Sobre o projeto

O **SkiloBox** é um sistema de gerenciamento de estoques desenvolvido como projeto final da Unidade Curricular de **Banco de Dados**.

A proposta inicial da atividade era desenvolver uma solução inspirada no conceito de **ERP (Enterprise Resource Planning)** utilizando **JDBC** para integração entre uma aplicação Java e um banco de dados relacional.

A partir desse tema, foi escolhido o segmento de **gerenciamento de estoques**, dando origem ao SkiloBox: um sistema voltado ao controle de produtos, movimentações de estoque, funcionários e informações operacionais de um almoxarifado.

O sistema foi desenvolvido com foco na aplicação prática dos conceitos estudados durante a UC, especialmente:

* modelagem e utilização de bancos de dados relacionais;
* operações CRUD;
* integração Java + MySQL por meio de JDBC;
* organização de código em camadas;
* consultas SQL;
* controle de usuários;
* registro de movimentações;
* geração de relatórios;
* registro de logs do sistema.

---

## 🎯 Objetivo

O objetivo do SkiloBox é fornecer uma ferramenta capaz de centralizar e organizar as operações relacionadas ao estoque de uma empresa.

Por meio do sistema, é possível controlar produtos e suas quantidades, registrar entradas e saídas, acompanhar movimentações e disponibilizar informações para auxiliar na gestão do almoxarifado.

Além de resolver um problema de gerenciamento, o projeto busca demonstrar na prática como uma aplicação Java pode utilizar um banco de dados relacional como parte central de seu funcionamento.

---

## 🏢 Contexto ERP

Um **ERP (Enterprise Resource Planning)** é um sistema utilizado para integrar e centralizar informações e processos de diferentes áreas de uma organização.

Dentro dessa proposta, o SkiloBox representa um módulo especializado em **gestão de estoque**.

Em vez de tratar o banco de dados apenas como uma forma de armazenar informações, o projeto utiliza o banco como parte fundamental da lógica operacional do sistema:

```text
                    ┌─────────────────────┐
                    │      SkiloBox       │
                    │   Gestão de Estoque │
                    └──────────┬──────────┘
                               │
              ┌────────────────┼────────────────┐
              │                │                │
              ▼                ▼                ▼
         Produtos        Movimentações     Funcionários
              │                │                │
              └────────────────┼────────────────┘
                               │
                               ▼
                       Banco de Dados
                            MySQL
```

---

## ⚙️ Funcionalidades

### 📦 Produtos

O módulo de produtos permite administrar os itens armazenados no estoque.

Entre as informações controladas estão:

* nome do produto;
* descrição;
* quantidade disponível;
* quantidade mínima;
* situação do produto;
* identificação do produto.

O sistema também permite realizar operações de cadastro, consulta, alteração e controle dos produtos.

---

### 🔄 Movimentações de estoque

As movimentações representam as alterações realizadas no estoque.

O sistema permite registrar operações como:

* entrada de produtos;
* saída de produtos;
* quantidade movimentada;
* produto relacionado;
* funcionário responsável;
* tipo de movimentação;
* data da operação.

Dessa forma, o estoque não depende apenas da quantidade atual: o sistema mantém o histórico das operações realizadas.

---

### 👤 Funcionários

O SkiloBox possui um módulo destinado ao gerenciamento dos funcionários que utilizam o sistema.

Os usuários possuem informações próprias e podem ter diferentes níveis de acesso.

O sistema diferencia usuários administradores dos demais usuários, permitindo restringir determinadas funcionalidades administrativas.

---

### 🔐 Autenticação e controle de acesso

O sistema possui autenticação de funcionários antes do acesso ao menu principal.

As permissões são utilizadas para determinar quais funcionalidades ficam disponíveis para cada usuário.

Administradores possuem acesso adicional a recursos como:

* gerenciamento de funcionários;
* relatórios;
* logs do sistema.

Usuários comuns possuem acesso às funcionalidades operacionais disponibilizadas para seu nível de permissão.

As senhas são tratadas utilizando a biblioteca **jBCrypt**, evitando o armazenamento direto de credenciais em texto puro.

---

### 📊 Relatórios

O sistema possui um módulo específico para geração de informações relacionadas ao estoque.

Os relatórios permitem transformar os dados armazenados no banco em informações úteis para acompanhamento e tomada de decisão.

Essa funcionalidade também demonstra a utilização do banco de dados não apenas para operações CRUD, mas para consultas destinadas à análise das informações armazenadas.

---

### 📝 Logs do sistema

O SkiloBox possui registro de logs para acompanhar ações realizadas no sistema.

Esse mecanismo permite manter um histórico das operações, contribuindo para:

* rastreabilidade;
* acompanhamento das ações dos usuários;
* auditoria;
* identificação de operações realizadas no sistema.

---

## 🏗️ Arquitetura

O projeto foi organizado em diferentes pacotes, buscando separar as responsabilidades da aplicação.

```text
src/
└── ProjetoSA/
    ├── connection/
    │   └── Conexao.java
    │
    ├── model/
    │   ├── FuncionarioModel.java
    │   ├── LogModel.java
    │   ├── MovimentacaoModel.java
    │   ├── ProdutoModel.java
    │   └── TipoMovimentacaoModel.java
    │
    ├── repository/
    │   ├── FuncionarioDAO.java
    │   ├── LogDAO.java
    │   ├── MovimentacaoDAO.java
    │   ├── ProdutoDAO.java
    │   ├── RelatorioDAO.java
    │   └── TipoMovimentacaoDAO.java
    │
    ├── service/
    │   ├── FuncionarioService.java
    │   ├── MovimentacaoService.java
    │   ├── ProdutoService.java
    │   └── TipoMovimentacaooService.java
    │
    ├── util/
    │   ├── FuncionarioMain.java
    │   ├── LogMain.java
    │   ├── MovimentacaoMain.java
    │   ├── ProdutoMain.java
    │   ├── RelatorioMain.java
    │   └── Style.java
    │
    └── Main.java
```

### Model

As classes `Model` representam as entidades utilizadas pela aplicação e os dados manipulados pelo sistema.

Entre elas estão:

* `ProdutoModel`;
* `MovimentacaoModel`;
* `FuncionarioModel`;
* `TipoMovimentacaoModel`;
* `LogModel`.

### Repository / DAO

A camada `repository` concentra o acesso aos dados.

As classes DAO são responsáveis por executar operações SQL e realizar a comunicação entre a aplicação e o banco de dados.

Entre os principais componentes estão:

* `ProdutoDAO`;
* `MovimentacaoDAO`;
* `FuncionarioDAO`;
* `TipoMovimentacaoDAO`;
* `LogDAO`;
* `RelatorioDAO`.

Essa separação permite que as operações de banco não fiquem concentradas diretamente na interface do sistema.

### Service

A camada `service` concentra regras e operações relacionadas ao funcionamento dos módulos.

Ela atua entre a interface e os repositórios, contribuindo para a separação das responsabilidades da aplicação.

### Util

O pacote `util` concentra as interfaces de interação pelo terminal e recursos auxiliares do sistema.

Entre eles estão os menus de:

* produtos;
* movimentações;
* funcionários;
* relatórios;
* logs.

Também existe a classe `Style`, utilizada para padronizar a apresentação do sistema no terminal.

### Connection

A classe `Conexao` centraliza a criação das conexões utilizadas para comunicação com o banco de dados.

---

## 🗄️ Banco de dados

O SkiloBox utiliza **MySQL** como sistema gerenciador de banco de dados e **JDBC** como tecnologia de comunicação entre Java e o banco.

A aplicação utiliza consultas SQL executadas por meio de objetos como `PreparedStatement`, permitindo parametrizar as consultas realizadas pelo sistema.

Exemplo conceitual do fluxo:

```text
Usuário
   │
   ▼
Interface Java
   │
   ▼
Service
   │
   ▼
DAO / Repository
   │
   ▼
JDBC
   │
   ▼
MySQL
```

Essa estrutura permite que as operações realizadas pelo usuário sejam persistidas no banco e posteriormente consultadas pelo sistema.

---

## 🧩 Principais entidades

O domínio do sistema é estruturado principalmente em torno de:

| Entidade                 | Responsabilidade                           |
| ------------------------ | ------------------------------------------ |
| **Produto**              | Representa os itens armazenados no estoque |
| **Movimentação**         | Registra alterações no estoque             |
| **Tipo de Movimentação** | Define a natureza da movimentação          |
| **Funcionário**          | Representa os usuários do sistema          |
| **Log**                  | Registra ações realizadas no sistema       |

Essas entidades permitem representar o fluxo básico de um almoxarifado:

```text
                    FUNCIONÁRIO
                         │
                         │ realiza
                         ▼
                  MOVIMENTAÇÃO
                    /         \
                   /           \
                  ▼             ▼
             PRODUTO      TIPO DE MOVIMENTAÇÃO
                  │
                  │ altera
                  ▼
                ESTOQUE
```

---

## 🛠️ Tecnologias utilizadas

| Tecnologia             | Utilização                                 |
| ---------------------- | ------------------------------------------ |
| **Java**               | Desenvolvimento da aplicação               |
| **JDBC**               | Comunicação entre Java e banco de dados    |
| **MySQL**              | Sistema de gerenciamento do banco de dados |
| **MySQL Connector/J**  | Driver JDBC para comunicação com MySQL     |
| **jBCrypt**            | Hashing de senhas                          |
| **Git**                | Controle de versão                         |
| **GitHub**             | Hospedagem do código-fonte                 |
| **Visual Studio Code** | Ambiente de desenvolvimento                |

As bibliotecas utilizadas no projeto estão disponibilizadas no diretório `lib/`.

---

## 📁 Estrutura do projeto

```text
ProjetoFinalSA-JDBC/
│
├── .vscode/
│   └── Configurações do projeto
│
├── lib/
│   ├── jbcrypt-0.4.jar
│   └── mysql-connector-j-9.7.0.jar
│
├── src/
│   └── ProjetoSA/
│       ├── connection/
│       ├── model/
│       ├── repository/
│       ├── service/
│       ├── util/
│       └── Main.java
│
├── .gitignore
└── README.md
```

---

## 🚀 Como executar

### 1. Pré-requisitos

Antes de executar o projeto, é necessário possuir:

* Java instalado;
* MySQL instalado e configurado;
* banco de dados do projeto criado;
* Visual Studio Code ou outra IDE compatível com Java;
* acesso às bibliotecas presentes no diretório `lib`.

### 2. Configuração do banco

Crie o banco de dados MySQL utilizado pelo projeto e configure os dados de conexão de acordo com o ambiente local.

A conexão é centralizada na classe:

```text
src/ProjetoSA/connection/Conexao.java
```

Configure nessa classe os parâmetros necessários para o acesso ao banco, como:

* endereço do servidor;
* porta;
* nome do banco;
* usuário;
* senha.

### 3. Dependências

As dependências utilizadas pelo sistema estão localizadas em:

```text
lib/
```

O projeto inclui o driver MySQL Connector/J e a biblioteca jBCrypt.

### 4. Execução

A aplicação é iniciada pela classe:

```text
ProjetoSA.Main
```

Após a conexão com o banco ser estabelecida, o sistema apresenta a tela de autenticação.

Depois do login, o usuário é direcionado ao menu correspondente ao seu nível de acesso.

---

## 🔄 Fluxo principal da aplicação

```text
                    INÍCIO
                      │
                      ▼
             Teste de conexão
                      │
                ┌─────┴─────┐
                │           │
             Falhou       Sucesso
                │           │
                ▼           ▼
               Fim        Login
                            │
                            ▼
                   Verificação do usuário
                            │
                  ┌─────────┴─────────┐
                  │                   │
               Usuário             Admin
                  │                   │
                  └─────────┬─────────┘
                            ▼
                         Menu
                            │
       ┌──────────┬─────────┼─────────┬──────────┐
       ▼          ▼         ▼         ▼          ▼
   Produtos   Movimentos Funcionários Relatórios Logs
       │          │         │         │          │
       └──────────┴─────────┴─────────┴──────────┘
                            │
                            ▼
                         MySQL
```

---

## 📚 Objetivos acadêmicos

O SkiloBox foi desenvolvido como uma aplicação prática dos conteúdos abordados na **UC de Banco de Dados**, especialmente:

* utilização de banco de dados relacional;
* criação e manipulação de registros;
* operações CRUD;
* consultas SQL;
* relacionamento entre entidades;
* integração de aplicações com bancos de dados;
* utilização de JDBC;
* organização de código em camadas;
* tratamento de exceções relacionadas ao banco;
* controle de usuários;
* armazenamento seguro de senhas;
* geração de consultas e relatórios;
* persistência de dados.

O projeto também permitiu integrar conhecimentos de programação orientada a objetos e desenvolvimento de sistemas à construção de uma aplicação baseada em banco de dados.

---

## 🎓 Contexto do projeto

O SkiloBox foi desenvolvido no contexto acadêmico como **projeto final da UC de Banco de Dados**.

A proposta de trabalhar com **ERP utilizando JDBC** serviu como ponto de partida para a definição do problema e do domínio da aplicação.

A equipe optou por concentrar a solução na área de **estoques e almoxarifado**, criando um sistema que representa um módulo de gestão empresarial e utiliza o banco de dados como elemento central da aplicação.

---

## 👥 Equipe

**Projeto desenvolvido por:**

* Arthur Iensen Mainardi
* Jullia de Souza dos Santos
* Leonardo Mafioleti

---

## 📌 Status

**Projeto acadêmico concluído.**

O sistema foi desenvolvido com foco na aplicação prática dos conceitos de Banco de Dados, Java e JDBC, podendo posteriormente ser expandido para incorporar outros módulos característicos de uma solução ERP.

---

## 🔮 Possíveis evoluções

Por representar inicialmente um módulo de estoque dentro de uma proposta de ERP, o SkiloBox pode futuramente ser expandido com outros módulos empresariais, como:

* fornecedores;
* compras;
* vendas;
* clientes;
* pedidos;
* financeiro;
* usuários e permissões mais avançadas;
* dashboards;
* notificações de estoque mínimo;
* integração com outros módulos do ERP.

A arquitetura atual permite utilizar o módulo de estoque como base para uma solução empresarial mais ampla.

---

## 📄 Licença

Este projeto foi desenvolvido para fins acadêmicos.
