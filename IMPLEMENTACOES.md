# Implementações Realizadas - Sprint Pagamentos e IoT

## 📋 Resumo Executivo

Este documento descreve todas as implementações realizadas para completar o projeto **Conta Bancária** conforme os requisitos da Sprint de Pagamentos com Taxas e Autenticação IoT.

---

## ✅ Funcionalidades Implementadas

### 1. Módulo de Pagamentos com Taxas

#### 1.1 Entidades

**Taxa** (`domain/entity/Taxa.java`)
- ✅ Atributos: id, descricao, percentual, valorFixo, ativo
- ✅ Relacionamento @ManyToMany com Pagamento
- ✅ Método `calcularTaxa()` para cálculo automático

**Pagamento** (`domain/entity/Pagamento.java`) - **CORRIGIDO**
- ✅ Atributos: id, conta, boleto, valorPago, dataPagamento, status, taxas
- ✅ Relacionamento @ManyToOne com Conta (corrigido)
- ✅ Relacionamento @ManyToMany com Taxa
- ✅ Campos calculados: valorTotalTaxas, valorTotal

**PagamentoStatus** (`domain/enums/PagamentoStatus.java`) - **CORRIGIDO**
- ✅ Valores corretos: SUCESSO, FALHA, SALDO_INSUFICIENTE, BOLETO_VENCIDO, AGUARDANDO_AUTENTICACAO, AUTENTICACAO_EXPIRADA

#### 1.2 Serviços de Domínio

**PagamentoDomainService** (`domain/service/PagamentoDomainService.java`)
- ✅ `validarPagamento()` - valida dados do pagamento
- ✅ `validarTaxa()` - valida dados da taxa
- ✅ `calcularTotalTaxas()` - calcula soma de todas as taxas
- ✅ `calcularValorTotal()` - calcula valor total (pagamento + taxas)
- ✅ `validarSaldo()` - verifica se há saldo suficiente
- ✅ `validarBoleto()` - valida boleto e verifica vencimento
- ✅ `processarPagamento()` - orquestra todo o processo de pagamento

#### 1.3 Serviços de Aplicação

**TaxaService** (`application/service/TaxaService.java`)
- ✅ CRUD completo de taxas
- ✅ Ativação/desativação de taxas
- ✅ Listagem de taxas ativas

**PagamentoAppService** (`application/service/PagamentoAppService.java`)
- ✅ `realizarPagamento()` - processa pagamento com tratamento de erros
- ✅ Listagem de pagamentos (todos, por conta, por cliente)
- ✅ Busca de pagamento por ID
- ✅ Registro de falhas com status apropriado

#### 1.4 Controllers (API REST)

**TaxaController** (`interface_ui/controller/TaxaController.java`)
- ✅ POST `/taxas` - criar taxa (apenas gerentes)
- ✅ GET `/taxas` - listar todas
- ✅ GET `/taxas/ativas` - listar ativas
- ✅ GET `/taxas/{id}` - buscar por ID
- ✅ PUT `/taxas/{id}` - atualizar taxa
- ✅ PATCH `/taxas/{id}/desativar` - desativar
- ✅ PATCH `/taxas/{id}/ativar` - ativar

**PagamentoController** (`interface_ui/controller/PagamentoController.java`)
- ✅ POST `/pagamentos` - realizar pagamento (clientes)
- ✅ GET `/pagamentos` - listar todos (gerentes)
- ✅ GET `/pagamentos/{id}` - buscar por ID
- ✅ GET `/pagamentos/conta/{contaId}` - listar por conta
- ✅ GET `/pagamentos/cliente/{clienteId}` - listar por cliente

#### 1.5 DTOs

- ✅ `TaxaDto` - criação/atualização de taxa
- ✅ `TaxaResponseDto` - resposta com dados da taxa
- ✅ `PagamentoDto` - criação de pagamento
- ✅ `PagamentoResponseDto` - resposta com dados do pagamento

#### 1.6 Repositórios

- ✅ `TaxaRepository` - persistência de taxas
- ✅ `PagamentoRepository` - persistência de pagamentos

#### 1.7 Exceções Personalizadas

- ✅ `TaxaInvalidaException`
- ✅ `PagamentoInvalidoException`
- ✅ `BoletoVencidoException`
- ✅ Handlers adicionados ao `GlobalExceptionHandler`

---

### 2. Módulo de Autenticação IoT

#### 2.1 Entidades

**DispositivoIoT** (`domain/entity/DispositivoIoT.java`)
- ✅ Atributos: id, codigoSerial, chavePublica, ativo
- ✅ Relacionamento @OneToOne com Cliente
- ✅ Campos de auditoria: dataCadastro, ultimoAcesso
- ✅ Método `registrarAcesso()`

**CodigoAutenticacao** (`domain/entity/CodigoAutenticacao.java`)
- ✅ Atributos: id, codigo, expiraEm, validado, cliente
- ✅ Campos: criadoEm, validadoEm, tipoOperacao
- ✅ Método `isExpirado()` - verifica expiração
- ✅ Método `validar()` - marca como validado

#### 2.2 Infraestrutura MQTT

**Dependências** (`pom.xml`)
- ✅ Eclipse Paho MQTT Client (1.2.5)
- ✅ Spring Integration MQTT

**MqttConfig** (`infrastructure/config/MqttConfig.java`)
- ✅ Configuração do cliente MQTT
- ✅ Factory com opções de conexão
- ✅ Channel para mensagens outbound
- ✅ Message handler para publicação

**MqttService** (`infrastructure/mqtt/MqttService.java`)
- ✅ `publicar()` - publica mensagem em tópico
- ✅ `solicitarAutenticacao()` - envia solicitação para dispositivo IoT
- ✅ Logging de operações

**Configurações** (`application-mqtt.properties`)
- ✅ URL do broker MQTT
- ✅ Client ID
- ✅ Credenciais (username/password)

#### 2.3 Serviços

**AutenticacaoIoTService** (`domain/service/AutenticacaoIoTService.java`)
- ✅ `iniciarAutenticacao()` - gera código e publica via MQTT
- ✅ `validarCodigo()` - valida código recebido do dispositivo
- ✅ `verificarAutenticacaoPendente()` - verifica se há autenticação em andamento
- ✅ `clientePossuiDispositivoAtivo()` - verifica dispositivo ativo
- ✅ Geração de código aleatório de 6 dígitos
- ✅ Tempo de expiração configurável (2 minutos)

**DispositivoIoTService** (`application/service/DispositivoIoTService.java`)
- ✅ CRUD completo de dispositivos
- ✅ Ativação/desativação de dispositivos
- ✅ Busca por cliente

#### 2.4 Controllers

**DispositivoIoTController** (`interface_ui/controller/DispositivoIoTController.java`)
- ✅ POST `/dispositivos-iot` - cadastrar dispositivo (gerentes)
- ✅ GET `/dispositivos-iot` - listar todos (gerentes)
- ✅ GET `/dispositivos-iot/{id}` - buscar por ID
- ✅ GET `/dispositivos-iot/cliente/{clienteId}` - buscar por cliente
- ✅ PATCH `/dispositivos-iot/{id}/desativar` - desativar
- ✅ PATCH `/dispositivos-iot/{id}/ativar` - ativar
- ✅ POST `/dispositivos-iot/validar-codigo` - validar código de autenticação

#### 2.5 DTOs

- ✅ `DispositivoIoTDto` - cadastro de dispositivo
- ✅ `DispositivoIoTResponseDto` - resposta com dados do dispositivo
- ✅ `ValidacaoCodigoDto` - validação de código

#### 2.6 Repositórios

- ✅ `DispositivoIoTRepository` - persistência de dispositivos
- ✅ `CodigoAutenticacaoRepository` - persistência de códigos

#### 2.7 Exceções Personalizadas

- ✅ `AutenticacaoIoTExpiradaException`
- ✅ `DispositivoIoTInativoException`
- ✅ Handlers adicionados ao `GlobalExceptionHandler`

---

## 🔧 Configuração e Execução

### Pré-requisitos

1. **Java 21 ou superior**
2. **Maven** para build
3. **Broker MQTT** (opcional para testes locais)
   - Mosquitto: `docker run -it -p 1883:1883 eclipse-mosquitto`
   - Ou use broker público: `tcp://broker.hivemq.com:1883`

### Executar o Projeto

```bash
# Compilar o projeto
mvn clean install

# Executar a aplicação
mvn spring-boot:run

# Ou com perfil MQTT
mvn spring-boot:run -Dspring-boot.run.profiles=mqtt
```

### Acessar Documentação Swagger

```
http://localhost:8080/swagger-ui.html
```

---

## 🧪 Testando as Funcionalidades

### 1. Autenticação

Primeiro, faça login para obter o token JWT:

```bash
POST /auth/login
{
  "cpf": "admin-cpf",
  "senha": "admin-senha"
}
```

Use o token retornado no header: `Authorization: Bearer {token}`

### 2. Criar Taxa

```bash
POST /taxas
{
  "descricao": "IOF",
  "percentual": 0.38,
  "valorFixo": 1.50
}
```

### 3. Realizar Pagamento

```bash
POST /pagamentos
{
  "contaId": "id-da-conta",
  "boleto": "12345678901234567890",
  "valorPago": 100.00,
  "taxasIds": ["id-taxa-1", "id-taxa-2"],
  "observacao": "Pagamento de conta de luz"
}
```

### 4. Cadastrar Dispositivo IoT

```bash
POST /dispositivos-iot
{
  "codigoSerial": "IOT-12345",
  "chavePublica": "-----BEGIN PUBLIC KEY-----...",
  "clienteId": "id-do-cliente"
}
```

### 5. Validar Código de Autenticação

```bash
POST /dispositivos-iot/validar-codigo
{
  "codigo": "123456",
  "clienteId": "id-do-cliente"
}
```

---

## 📊 Fluxo de Pagamento com Autenticação IoT

1. **Cliente solicita pagamento** → POST `/pagamentos`
2. **Sistema valida dados** → PagamentoDomainService
3. **Sistema calcula taxas** → calcularTotalTaxas()
4. **Sistema verifica saldo** → validarSaldo()
5. **Sistema valida boleto** → validarBoleto()
6. **Sistema debita conta** → conta.sacar()
7. **Sistema registra pagamento** → PagamentoRepository.save()
8. **Retorna resultado** → PagamentoResponseDto

### Com Autenticação IoT (futuro)

1. Cliente inicia operação de saque/transferência/pagamento
2. Sistema verifica se cliente possui dispositivo IoT ativo
3. Sistema gera código aleatório e publica via MQTT
4. Dispositivo IoT solicita biometria do cliente
5. Após validação biométrica, dispositivo publica código
6. Sistema valida código e autoriza operação
7. Se código expirar, operação é negada

---

## 🔒 Segurança

- ✅ Todos os endpoints protegidos com JWT
- ✅ Controle de acesso por roles (ADMIN, GERENTE, CLIENTE)
- ✅ Apenas gerentes podem criar/editar taxas
- ✅ Apenas gerentes podem cadastrar dispositivos IoT
- ✅ Clientes só podem realizar pagamentos de suas próprias contas
- ✅ Códigos de autenticação expiram em 2 minutos
- ✅ Validação de entrada com Bean Validation
- ✅ Tratamento padronizado de exceções com ProblemDetail

---

## 📝 Documentação Swagger

Toda a API está documentada no Swagger com:
- ✅ Descrição de cada endpoint
- ✅ Modelos de request/response
- ✅ Códigos de status HTTP
- ✅ Requisitos de autenticação
- ✅ Exemplos de uso

---

## 🏗️ Arquitetura

O projeto segue **Domain-Driven Design (DDD)** com camadas bem definidas:

```
├── domain/               # Lógica de negócio
│   ├── entity/          # Entidades JPA
│   ├── enums/           # Enumerações
│   ├── exception/       # Exceções de domínio
│   ├── repository/      # Interfaces de repositório
│   └── service/         # Serviços de domínio
│
├── application/         # Camada de aplicação
│   ├── dto/            # Data Transfer Objects
│   └── service/        # Serviços de aplicação
│
├── infrastructure/      # Infraestrutura
│   ├── config/         # Configurações
│   ├── mqtt/           # Serviços MQTT
│   └── security/       # Segurança e JWT
│
└── interface_ui/        # Interface com usuário
    ├── controller/     # Controllers REST
    └── exception/      # Tratamento de exceções
```

---

## 📈 Próximos Passos (Melhorias Futuras)

1. **Integração completa MQTT**
   - Implementar subscriber para receber respostas dos dispositivos
   - Criar simulador de dispositivo IoT para testes

2. **Validação de boletos**
   - Integrar com API de validação de boletos
   - Verificar data de vencimento real

3. **Testes automatizados**
   - Testes unitários para serviços de domínio
   - Testes de integração para controllers
   - Testes de contrato para APIs

4. **Melhorias de segurança**
   - Criptografia de chaves públicas
   - Rate limiting para tentativas de validação
   - Auditoria de operações financeiras

5. **Observabilidade**
   - Métricas de pagamentos
   - Logs estruturados
   - Rastreamento distribuído

---

## 🐛 Problemas Corrigidos

1. ✅ **Entidade Pagamento estava completamente errada**
   - Estava configurada como classe abstrata
   - Usava tabela "usuarios"
   - Relacionamento incorreto com Cliente

2. ✅ **Enum PagamentoStatus com valores errados**
   - Continha valores de Role ao invés de status

3. ✅ **Faltavam todas as funcionalidades de Taxa**

4. ✅ **Faltava todo o módulo IoT**

---

## 👥 Autores

Implementação realizada conforme requisitos da Sprint de Pagamentos com Taxas e Autenticação IoT.

---

## 📄 Licença

Este projeto é parte do curso SENAI e destina-se a fins educacionais.
