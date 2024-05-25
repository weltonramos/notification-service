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

## Estrutura do Projeto

O diagrama abaixo mostra como a arquitetura foi elaborada para permitir resiliência e disponibilidade além da
segregação de componentes facilitando a manutenabilidade e escala em caso de alto volume de requisições.

![](documentation\notification-service.png)

## Funcionalidades

### Notification service

Serviço responsável por receber requisições com dados de notificações e preferências dos usuários para envio de mensagens.
Faz-se uso de cache para prover melhor desempenho caso mais de uma mensagem seja enviada para um mesmo usuário e serviço 
de mensageria para conceder resiliencia a aplicação em caso de eventual indisponibilidade do sistema. Após a consulta,
caso o usuário seja elegível, a mensagem é enviada para uma fila para garantir que seja enviada ao destinatário.

Este design garante escalabilidade e resiliência, podendo escalar o número de instâncias, porém será necessário um load
balancer.

### Fila de Mensagens (SQS):

Responsável por recebe as mensagens vindas do Notification Service e distribui-las para os workers expecíficos de cada 
tipo de mensagem (E-mail, SMS, Push Notifications, etc). Seu uso assegura resiliência para casos em que workers fiquem
indisponíveis, pois, em caso de falha, haverão retentativas de entregas de mensagens para seus destinatários até que 
seja entregue com sucesso ou o limite de tentativas esgote, neste cenários, as mensagens serão classificadas como
dead letters para futura analise do motivo de sua não entrega.
