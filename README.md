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
- **Status:** `400 Bad Request`
- **Response Body:**  
```json
{
  "erro": "Saldo insuficiente para realizar a transferência",
  "detalhes": [
    "A conta de origem não possui saldo suficiente para realizar a transferência."
  ]
}
```

#### **3. Conta de Origem ou Destino Não Encontrada**
- **Status:** `404 Not Found`
- **Response Body:**  
```json
{
  "erro": "Conta não encontrada",
  "detalhes": [
    "A conta de origem com id 'b3a7f2a0-8c1f-4d76-b2e3-5d9f6a1b0c78' não foi encontrada.",
    "A conta de destino com id 'f14c5d3e-7a2b-4e98-912f-3e6a8b9d2e45' não foi encontrada."
  ]
}
```

#### **4. Erro Interno do Servidor**
- **Status:** `500 Internal Server Error`
- **Response Body:**  
```json
{
  "erro": "Erro interno do servidor",
  "detalhes": [
    "Ocorreu um erro inesperado ao processar a transferência."
  ]
}
```