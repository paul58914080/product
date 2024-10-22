# Product service

<!-- 
this project was generated with https://github.com/devs-from-matrix/basic-template-repository. 
-->

## [Documentation](https://devs-from-matrix.github.io/basic-template-repository/)

## How to build ?

### Local jar

```shell
mvn clean install
```

### Docker image

```shell
docker build -t ecommerce/product-service -f .docker/Dockerfile .
```

## How to start ?

### Local jar

```shell
mvn spring-boot:run
```

### Docker image

```shell
docker run -p 3001:8080 ecommerce/product-service
```

## Contribution guidelines

We are really glad you're reading this, because we need volunteer developers to help this project come to fruition.

Request you to please read our [contribution guidelines](https://devs-from-matrix.github.io/basic-template-repository/#/README?id=contribution-guidelines)
