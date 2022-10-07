# banckend_MS_Pedidos

Microserviço de cadastrar Pedido/Serviço e Pedido.

* Terá três entidades: Produto/Serviço, Pedido e Itens de Pedido.
* Todas as entidades deverão ter um ID únivo do tipo UUID.
* No cadastro de produto/serviço, deverá ter uma indicação para diferenciar um produto de um serviço.
* Deverá ser possível aplicar um percentual de desconto no pedido, porém apenas para os itens que sejam produto (não serviço). O desconto será sobre o valor total dos produtos  


* Somente será possivel aplicar o desconto se o pedido estiver na situação Aberto (Fechado bloqueia)
* Não deve ser possível excluir um produto/serviço se ele estiver associado a alguem pedido
* Não deve ser possível adiiconar em um pedido, um produto desativado.  

* Deverá ser possivel aplicar filtros na listagem
* As entidades deverão utilizar Bean Validation
* Deverá ser implementado um ControllerAdvice para customizar os HTTP response das requisições (minímo BAD REQUEST)  
  
Para tal foi modelado o banco de dados:  
![Screenshot](bancoDados.png)  

Foi utilizado o padrão Value Object, desta forma, o modelo do banco é diferente daquele passado.
O modelo VO que deve ser passado via body (json) é:
![Screenshot](Diagrama_de_classe.png)  


## Para rodar um projeto maven com spring:
Se a IDE não tiver o ícone para rodar a aplicação, poderá rodar via terminal:
```sh
mvn clean compile spring-boot:run
```
ou simplesmente  
```sh
mvn spring-boot:run
```

### Endpoint para cadastro de produto/serviço

| endpoint | HTTP|Descrição |
|----------|-----|-----------|
|/v1/produto-servico| POST | Salvar um Produto no banco - requer um body do produto|
|/v1/produto-servico| PUT | Atualizar um produto no banco - requer um body do produto|
|/v1/produto-servico/:id| GET | Busca um produto pelo id, que é passado como Path Variable na url|
|/v1/produto-servico| GET | Busca todos os produtos do banco|
|/v1/produto-servico/:id| DELETE | Deleta um produto pelo id, que é passado como Path Variable na url|
|/v1/produto-servico/search| GET| busca uma lista de produtos de acordo com o nome, é aceito os Request param 'searchTerm', 'page' e 'size' para fazer a paginação|
|/v1/produto-servico/pagination| GET | busca todos os produtos do banco de acordo com a paginação passada (objeto Pageable)|

## Endpoint para cadastro de Pedido (e item-pedido)

| endpoint | HTTP|Descrição |
|----------|-----|-----------|
|/v1/pedido| POST |Salva um pedido no banco, requer um body|
|/v1/pedido| PUT | Atualiza um pedido no banco, requer um body|
| /v1/pedido/:id | GET | Busca um pedido no banco de acordo com o id, que é passado como Path Variable na url|
|/v1/pedido| GET | Busca todos os pedidos no banco|
|/v1/pedido/:id| DELETE | Deleta o pedido no banco de dados de acordo com o id, que é passado como Path Variable na url|
|/v1/pedido/search| GET| busca uma lista de produtos de acordo com o desconto, que é passado como Request Param 'searchTerm', é permitido passar também os Request Param 'page' e 'size' para fazer a paginação|
|/v1/pedido/pagination| GET | Busca todos os pedidos no banco de acordo com a paginação passada (objecto Pageable)|

### Exemplo de body do produto:
```sh
{
    "nome": "teclado gamer pro 3",
    "descricao": "teclado gamer 3",
    "valor": 120,
    "tipo": 0,
    "status": 1
}
```

### Exemplo de body do pedido:
```sh
{
    "desconto": 10,
    "situacao": 1,
    "produtoServico": [
        {
            "id": "4cd9b09e-b7c7-442d-b952-a8c6a9895897"
        },
        {
            "id" : "3ea56436-cf56-46e7-b9e6-27a1db71a536"
        },
        {
            "id": "59cd5f8d-cbfb-41c9-9fc2-0918bb28c3a4"
        },
        {
            "id": "f2a6fa10-3335-4694-9219-08c18b564cfc"
        }
    ]
}
```

### Exemplo dos get com paginação
-> Produto:
1) Get com filtro (nome do produto) e paginação
```sh
localhost:8080/v1/produto-servico/search?searchTerm=teclado&page=0&size=3
```
retorno esperado:
```sh
{
    "content": [
        {
            "id": "23ea840a-766c-450a-b857-d257835644b9",
            "nome": "teclado ultra 6",
            "descricao": "teclado ultra 350/6",
            "valor": 80.0,
            "tipo": "0",
            "status": "1"
        },
        {
            "id": "2586dabb-7d6d-4a56-a322-650b5c39c731",
            "nome": "teclado gamer",
            "descricao": "teclado gamer 1",
            "valor": 100.0,
            "tipo": "0",
            "status": "1"
        },
        {
            "id": "423b453b-1c5b-4053-bdc2-b4171abbc9b6",
            "nome": "teclado ultra 10",
            "descricao": "teclado ultra 350/10",
            "valor": 100.0,
            "tipo": "0",
            "status": "1"
        }
    ],
    "pageable": {
        "sort": {
            "empty": false,
            "unsorted": false,
            "sorted": true
        },
        "offset": 0,
        "pageNumber": 0,
        "pageSize": 3,
        "paged": true,
        "unpaged": false
    },
    "last": false,
    "totalPages": 4,
    "totalElements": 11,
    "size": 3,
    "number": 0,
    "sort": {
        "empty": false,
        "unsorted": false,
        "sorted": true
    },
    "first": true,
    "numberOfElements": 3,
    "empty": false
}
```

2) Get com paginação (mas usando a interface Pageable)
```sh
localhost:8080/v1/produto-servico/pagination?page=0&size=5
```
retorno esperado:
```sh
{
    "content": [
        {
            "id": "23ea840a-766c-450a-b857-d257835644b9",
            "nome": "teclado ultra 6",
            "descricao": "teclado ultra 350/6",
            "valor": 80,
            "tipo": "0",
            "status": "1"
        },
        {
            "id": "2586dabb-7d6d-4a56-a322-650b5c39c731",
            "nome": "teclado gamer",
            "descricao": "teclado gamer 1",
            "valor": 100,
            "tipo": "0",
            "status": "1"
        },
        {
            "id": "3ea56436-cf56-46e7-b9e6-27a1db71a536",
            "nome": "Manutenção",
            "descricao": "Manutenção no laptop",
            "valor": 250,
            "tipo": "1",
            "status": "1"
        },
        {
            "id": "423b453b-1c5b-4053-bdc2-b4171abbc9b6",
            "nome": "teclado ultra 10",
            "descricao": "teclado ultra 350/10",
            "valor": 100,
            "tipo": "0",
            "status": "1"
        },
        {
            "id": "4cd9b09e-b7c7-442d-b952-a8c6a9895897",
            "nome": "teclado",
            "descricao": "teclado",
            "valor": 100,
            "tipo": "0",
            "status": "1"
        }
    ],
    "pageable": {
        "sort": {
            "empty": false,
            "unsorted": false,
            "sorted": true
        },
        "offset": 0,
        "pageNumber": 0,
        "pageSize": 5,
        "paged": true,
        "unpaged": false
    },
    "last": false,
    "totalPages": 4,
    "totalElements": 16,
    "size": 5,
    "number": 0,
    "sort": {
        "empty": false,
        "unsorted": false,
        "sorted": true
    },
    "first": true,
    "numberOfElements": 5,
    "empty": false
}

```

-> Pedido

1) Pedido com filtro (pelo valor) e paginação:
```sh
localhost:8080/v1/pedido/search?searchTerm=10&page=0&size=2
```
retorno esperado:
```sh
{
    "content": [
        {
            "desconto": 10.0,
            "situacao": "1",
            "total": 340.0,
            "produtoServico": [
                {
                    "id": "4cd9b09e-b7c7-442d-b952-a8c6a9895897",
                    "nome": "teclado",
                    "descricao": "teclado",
                    "valor": 100.0,
                    "tipo": "0",
                    "status": "1"
                },
                {
                    "id": "3ea56436-cf56-46e7-b9e6-27a1db71a536",
                    "nome": "Manutenção",
                    "descricao": "Manutenção no laptop",
                    "valor": 250.0,
                    "tipo": "1",
                    "status": "1"
                }
            ],
            "uuid": "073be6c4-2a7e-46d9-aa6e-2732aab16769"
        },
        {
            "desconto": 10.0,
            "situacao": "1",
            "total": 340.0,
            "produtoServico": [
                {
                    "id": "4cd9b09e-b7c7-442d-b952-a8c6a9895897",
                    "nome": "teclado",
                    "descricao": "teclado",
                    "valor": 100.0,
                    "tipo": "0",
                    "status": "1"
                },
                {
                    "id": "3ea56436-cf56-46e7-b9e6-27a1db71a536",
                    "nome": "Manutenção",
                    "descricao": "Manutenção no laptop",
                    "valor": 250.0,
                    "tipo": "1",
                    "status": "1"
                }
            ],
            "uuid": "07c37d95-8e28-4524-860c-4f8bc8293fb6"
        }
    ],
    "pageable": {
        "sort": {
            "empty": false,
            "sorted": true,
            "unsorted": false
        },
        "offset": 0,
        "pageSize": 2,
        "pageNumber": 0,
        "unpaged": false,
        "paged": true
    },
    "last": false,
    "totalElements": 3,
    "totalPages": 2,
    "size": 2,
    "number": 0,
    "sort": {
        "empty": false,
        "sorted": true,
        "unsorted": false
    },
    "first": true,
    "numberOfElements": 2,
    "empty": false
}

```
2) Get com paginação (mas usando a interface Pageable)
```sh

localhost:8080/v1/pedido/pagination?page=0&size=5
```
retorno esperado:
```sh
{
    "content": [
        {
            "desconto": 10.0,
            "situacao": "1",
            "total": 340.0,
            "produtoServico": [
                {
                    "id": "4cd9b09e-b7c7-442d-b952-a8c6a9895897",
                    "nome": "teclado",
                    "descricao": "teclado",
                    "valor": 100.0,
                    "tipo": "0",
                    "status": "1"
                },
                {
                    "id": "3ea56436-cf56-46e7-b9e6-27a1db71a536",
                    "nome": "Manutenção",
                    "descricao": "Manutenção no laptop",
                    "valor": 250.0,
                    "tipo": "1",
                    "status": "1"
                }
            ],
            "uuid": "073be6c4-2a7e-46d9-aa6e-2732aab16769"
        },
        {
            "desconto": 10.0,
            "situacao": "1",
            "total": 340.0,
            "produtoServico": [
                {
                    "id": "4cd9b09e-b7c7-442d-b952-a8c6a9895897",
                    "nome": "teclado",
                    "descricao": "teclado",
                    "valor": 100.0,
                    "tipo": "0",
                    "status": "1"
                },
                {
                    "id": "3ea56436-cf56-46e7-b9e6-27a1db71a536",
                    "nome": "Manutenção",
                    "descricao": "Manutenção no laptop",
                    "valor": 250.0,
                    "tipo": "1",
                    "status": "1"
                }
            ],
            "uuid": "07c37d95-8e28-4524-860c-4f8bc8293fb6"
        },
        {
            "desconto": 10.0,
            "situacao": "1",
            "total": 340.0,
            "produtoServico": [
                {
                    "id": "4cd9b09e-b7c7-442d-b952-a8c6a9895897",
                    "nome": "teclado",
                    "descricao": "teclado",
                    "valor": 100.0,
                    "tipo": "0",
                    "status": "1"
                },
                {
                    "id": "3ea56436-cf56-46e7-b9e6-27a1db71a536",
                    "nome": "Manutenção",
                    "descricao": "Manutenção no laptop",
                    "valor": 250.0,
                    "tipo": "1",
                    "status": "1"
                }
            ],
            "uuid": "2775af3e-27cd-4d4f-8972-55165d0d6685"
        },
        {
            "desconto": 0.0,
            "situacao": "1",
            "total": 135.0,
            "produtoServico": [
                {
                    "id": "4cd9b09e-b7c7-442d-b952-a8c6a9895897",
                    "nome": "teclado",
                    "descricao": "teclado",
                    "valor": 100.0,
                    "tipo": "0",
                    "status": "1"
                },
                {
                    "id": "9b406feb-08a9-47c9-a553-12df21c91cfe",
                    "nome": "mouse",
                    "descricao": "mouse briwax",
                    "valor": 35.0,
                    "tipo": "0",
                    "status": "0"
                }
            ],
            "uuid": "e11573bd-b634-40a5-9431-ab02d33be37c"
        },
        {
            "desconto": 0.0,
            "situacao": "0",
            "total": 350.0,
            "produtoServico": [
                {
                    "id": "4cd9b09e-b7c7-442d-b952-a8c6a9895897",
                    "nome": "teclado",
                    "descricao": "teclado",
                    "valor": 100.0,
                    "tipo": "0",
                    "status": "1"
                },
                {
                    "id": "3ea56436-cf56-46e7-b9e6-27a1db71a536",
                    "nome": "Manutenção",
                    "descricao": "Manutenção no laptop",
                    "valor": 250.0,
                    "tipo": "1",
                    "status": "1"
                }
            ],
            "uuid": "f41db5be-65ce-4862-ba12-97e6a7fff405"
        }
    ],
    "pageable": {
        "sort": {
            "empty": false,
            "sorted": true,
            "unsorted": false
        },
        "offset": 0,
        "pageSize": 5,
        "pageNumber": 0,
        "unpaged": false,
        "paged": true
    },
    "last": true,
    "totalElements": 5,
    "totalPages": 1,
    "size": 5,
    "number": 0,
    "sort": {
        "empty": false,
        "sorted": true,
        "unsorted": false
    },
    "first": true,
    "numberOfElements": 5,
    "empty": false
}

```