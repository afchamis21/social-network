# API de Rede Social

Bem-vindo à API de Rede Social, uma plataforma robusta e repleta de recursos projetada para permitir que os usuários se conectem, compartilhem postagens, interajam com amigos e participem de conversas significativas. Esta API capacita desenvolvedores a criar aplicativos de rede social com facilidade, fornecendo uma ampla variedade de endpoints para autenticação de usuários, gerenciamento de postagens, comentários, interações de amizade e muito mais.

## Tabela de Conteúdos

<!-- TOC -->
* [API de Rede Social](#api-de-rede-social)
  * [Tabela de Conteúdos](#tabela-de-conteúdos)
  * [Recursos](#recursos)
  * [Começando](#começando)
    * [Pré-requisitos](#pré-requisitos)
    * [Instalação](#instalação)
  * [Autenticação](#autenticação)
    * [Endpoints](#endpoints)
  * [Tratamento de Erros](#tratamento-de-erros)
  * [Paginação](#paginação)
<!-- TOC -->

## Recursos

- **Gerenciamento de Usuários**: Registro, atualização e recuperação de informações de usuário.
- **Postagens**: Criar, editar, excluir e recuperar postagens.
- **Comentários**: Criar, editar, excluir e recuperar comentários em postagens.
- **Solicitações de Amizade**: Enviar, aceitar e cancelar solicitações de amizade.
- **Relações de Amizade**: Listar amigos do usuário e remover amigos.
- **Autenticação**: Entrar, atualizar tokens e sair.
- **Autenticação Baseada em Token**: Utiliza Tokens JSON Web (JWT) para autenticação segura de usuário e gerenciamento de sessão.
- **Tratamento de Erros**: Tratamento abrangente de erros com respostas de erro padronizadas.
- **Suporte à Paginação**: Navegação fácil por resultados paginados.
- **Integração Fácil**: A API é construída com Java e Spring Boot, facilitando sua integração em seus projetos.

## Começando

### Pré-requisitos

- Kit de Desenvolvimento Java (JDK) 8 ou posterior
- Ferramenta de Compilação Gradle (recomendado) ou Apache Maven
- Seu Ambiente de Desenvolvimento Integrado (IDE) preferido

### Instalação

1. Clone este repositório em sua máquina local:

   ```bash
   git clone https://github.com/seu-nome-de-usuário/api-rede-social.git
   ```

2. Navegue até o diretório do projeto:

   ```bash
   cd api-rede-social
   ```

3. Personalize as propriedades do aplicativo:

   Renomeie o arquivo `application.sample.properties` no diretório `src/main/resources` para `application.properties`. Configure as configurações do banco de dados e outras propriedades de acordo com seu ambiente.

4. Compile e execute o aplicativo:

   Se estiver usando o Gradle:
   ```bash
   ./gradlew bootRun
   ```

   Se estiver usando o Maven:
   ```bash
   ./mvnw spring-boot:run
   ```

A API agora deve estar em execução em `http://localhost:8080`.

## Autenticação

A API utiliza autenticação baseada em tokens usando Tokens JSON Web (JWT). Para fazer solicitações autenticadas, inclua o cabeçalho `Authorization` com o valor `Bearer <seu-token-de-acesso>`.

Para atualizar o token, use o endpoint `/auth/refresh` fornecido com um token de atualização válido.

### Endpoints

Para uma lista abrangente de endpoints disponíveis, formatos de solicitação/resposta e exemplos de uso, consulte o endpoint `/swagger-ui/index.html/`.

## Tratamento de Erros

A API garante respostas claras e consistentes de erro por meio do tratamento centralizado de erros. Diversas exceções são tratadas pela classe [`ErrorHandler`](src/main/java/andre/chamis/socialnetwork/controller/ErrorHandler.java), fornecendo mensagens informativas e códigos de status para facilitar a depuração e o desenvolvimento.

## Paginação

Os endpoints que retornam vários itens oferecem suporte à paginação. Utilize os parâmetros de consulta `page` e `size` para controlar o número de itens exibidos e navegar pelas páginas de resultado.