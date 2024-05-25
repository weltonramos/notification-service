# Projeto de Notificação de Usuários

## Descrição do Projeto

Este projeto visa a criação de um sistema de notificação de usuários utilizando tecnologias modernas como Spring Boot,
AWS SQS, Docker, Docker Compose, Lombok e MongoDB. O objetivo é fornecer um meio eficiente e escalável para enviar
notificações para os usuários através de diferentes canais de mensageria.

## Tecnologias Utilizadas

- Java 17: Linguagem de programação principal do projeto.
- Spring Boot: Framework para facilitar o desenvolvimento de aplicações Java.
- AWS SQS: Serviço de fila de mensagens para garantir a entrega de mensagens entre sistemas distribuídos.
- Docker: Ferramenta para criar e gerenciar containers.
- Docker Compose: Ferramenta para definir e gerenciar multi-containers Docker.
- Lombok: Biblioteca para reduzir o código boilerplate em Java.
- MongoDB: Banco de dados NoSQL para armazenar informações dos usuários e notificações.

## Funcionalidades

Envio de Notificações: Permite o envio de notificações para os usuários através de diferentes canais (ex.: email, SMS).
Fila de Mensagens: Utiliza AWS SQS para garantir a entrega das mensagens de notificação.
Persistência de Dados: Armazena informações de usuários e notificações no MongoDB.
Gerenciamento de Containers: Utiliza Docker e Docker Compose para facilitar a execução e o gerenciamento dos containers
da aplicação.

# Configuração e Execução

## Pré-requisitos

- Docker e Docker Compose instalados
- Java 17 instalado
- Conta na AWS com acesso ao SQS

# Construção e Execução