# ePET

## Introdução 

Neste repositório está contida a implementação do back-end utilizando-se do framework Spring e a linguagem de programação JAVA, da plataforma do PET-CC UFRN, Programa de Educação Tutorial da Universidade do Rio Grande do Norte.

- [Pré-requisitos](#pré-requisitos) - Requisitos para executar o projeto em localmente.
- [Como executar](#como-executar) - Instruções sobre como executar o projeto em uma IDE.
- [Referências](#referências) - Link contendo material de referência.
- [Contribuidores](#contribuidores) - Pessoas que ajudaram no desenvolvimento do projeto.


## Pré-requisitos

Faz-se necessário os seguintes programas para executar o projeto com suas respectivas versões recomendadas:


| Nome: | Versão:  |    
| :---------- | :------------- |
|`JDK` 	| 11 |  
|`mysql` 	| any | 
|`IDE JAVA`| any|


## Como executar

Em seu computador execute os seguintes comandos para clonagem do repositório localmente em uma pasta de sua preferência:

``` bash
$ git clone https://github.com/PETCC-UFRN/ePET.git 
```

### Desenvolvimento

O primeiro passo é criar uma base de dados chamada PETCC em seu mysql ([Exemplo](https://www.devmedia.com.br/primeiros-passos-no-mysql/28438)) e o deixar rodando na porta padrão 3306.

Para facilitar o desenvolvimento, aconselha-se a usar uma IDE([Eclipse](https://www.eclipse.org/downloads/), [NetBeans](https://netbeans.org/downloads/8.2/rc/) e afins). Dessa forma, em sua IDE de preferência, importe o projeto, (algumas IDEs permitem a importação de projeto maven, outras basta abrir o projeto escolhendo o diretório clonado anteriormente.

O próximo passo é a criação do arquivo de configuração do spring chamado de application.properties, na pasta src/main/resources. Por tratar de dados sensíveis, resolvemos retirar esse arquivo do repositório, portanto entre em contato com os desenvolvedores para saber como criar este arquivo adequadamente.

Para rodar o programa, basta executar o arquivo src/main/java/br/ufrn/ePET/EPetApplication.java na IDE.

Após isso a API já estará em funcionamente, dessa forma você poderá acessar a documentação em [http://localhost:8443/swagger-ui.html#/](http://localhost:8443/swagger-ui.html#/) ou [https://localhost:8443/swagger-ui.html#/](https://localhost:8443/swagger-ui.html#/) e conhecer as rotas disponíveis.
##### Opcional
É interessante deixar o código do arquivo src/main/java/br/ufrn/ePET/config/ConnectorConfig.java comentado, uma vez que ele só é utilizado em produção para utilizar o padrão https, feature que em desenvolvimento não é necessária.


### Produção
TODO
## Referências
- [Exemplo de criação de banco no mysql](https://www.devmedia.com.br/primeiros-passos-no-mysql/28438)
- [Documentação com swagger ](https://www.treinaweb.com.br/blog/documentando-uma-api-spring-boot-com-o-swagger/amp/)
- [Curso de Spring boot](https://youtu.be/OHn1jLHGptw)


## Contribuidores

Abraão, Daniel, Henrique ,Ítalo, Jhonattan, Samuel.
