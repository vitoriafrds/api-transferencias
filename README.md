## **API Transferências**  

### **Contexto de Negócio**  
A **API de Transferências** é um serviço responsável por realizar transações bancárias entre contas. O sistema conta com algumas integrações para garantir que a transferência seja realizada corretamente e notificada ao **Bacen**.  

---

### **Detalhamento Técnico**  

#### **Tecnologias Utilizadas**  
- Java  
- Spring Boot  
- Maven  
- AWS  
- JUnit  

---

### **Endpoints**  

#### **Criar uma Transferência**  

- **Método:** `POST`  
- **Rota:** `/transferencias`  

##### **Request Body:**  
```json
{
  "id_cliente": "67890",
  "valor": 150.75,
  "conta": {
    "id_origem": "b3a7f2a0-8c1f-4d76-b2e3-5d9f6a1b0c78",
    "id_destino": "f14c5d3e-7a2b-4e98-912f-3e6a8b9d2e45"
  }
}
```

##### **Response:**  
- **Status:** `201 Created`  

---

### **Retorno de Erros**

A API pode retornar diferentes erros com base nas validações e falhas durante a execução. Abaixo estão alguns exemplos de respostas de erro:

#### **1. Erro de Validação: Campos Obrigatórios Não Preenchidos**
- **Status:** `400 Bad Request`
- **Response Body:**  
```json
{
  "erro": "Campo obrigatório não preenchido",
  "detalhes": [
    "O campo 'id_cliente' é obrigatório",
    "O campo 'valor' deve ser um número maior que 0"
  ]
}
```

#### **2. Erro de Transferência Invalida (Saldo Insuficiente)**
- **Status:** `422 Unprocessable Entity`
- **Response Body:**  
```json
{
  "error": {
    "code": "CO02",
    "message": "Insufficient balance to complete the transfer.",
    "timestamp": "2025-03-19T21:39:19.3183581"
  }
}
```

#### **3. Erro de Transferência Invalida (Limite Insuficiente)**
- **Status:** `422 Unprocessable Entity`
- **Response Body:**
```json
{
  "error": {
    "code": "CO03",
    "message": "Insufficient daily account limit to complete the transfer",
    "timestamp": "2025-03-19T21:39:19.3183581"
  }
}
```

#### **4. Conta de Origem**
- **Status:** `422 Unprocessable Entity`
- **Response Body:**  
```json
{
  "error": {
    "code": "CO03",
    "message": "The specified account does not exist.",
    "timestamp": "2025-03-19T21:44:15.2515057"
  }
}
```

#### **5. Conta Inativa**
- **Status:** `422 Unprocessable Entity`
- **Response Body:**
```json
{
  "error": {
    "code": "CO01",
    "message": "The specified account is not active",
    "timestamp": "2025-03-19T21:44:15.2515057"
  }
}
```

#### **6. Conta Inativa**
- **Status:** `422 Unprocessable Entity`
- **Response Body:**
```json
{
  "error": {
    "code": "CLI001",
    "message": "The specified account is not activeThe specified customer does not exist.",
    "timestamp": "2025-03-19T21:44:15.2515057"
  }
}
```

#### **6. Erro na transferencia**
- **Status:** `422 Unprocessable Entity`
- **Response Body:**
```json
{
  "error": {
    "code": "CLI001",
    "message": "The specified account is not activeThe specified customer does not exist.",
    "timestamp": "2025-03-19T21:44:15.2515057"
  }
}
```

#### **5. Erro Interno do Servidor**
- **Status:** `500 Internal Server Error`
- **Response Body:**  
```json
{
  "error": {
    "code": "A001",
    "message": "Unexpected failure in the service.",
    "timestamp": "2025-03-19T21:44:15.2515057"
  }
}
```