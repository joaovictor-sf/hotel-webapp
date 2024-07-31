# Aplicação Spring de um Hotel
Meus conhecimentos e duvidas:

## Dependências
- Spring Web
- Spring Data JPA
- PostgreSQL Driver
- Lombok
- Security
- Validation
- jjwt
- S3

## Queries ultilizadas

***1***
```java
@Query("SELECT DISTINCT r.roomType FROM Room r")
```
Essa query está buscando todos os tipos de quartos (roomType) distintos.

Vamos detalhar:
- SELECT DISTINCT r.roomType: Seleciona todos os tipos de quartos distintos.
- FROM Room r: Seleciona os objetos Room e os referencia como r.

Portanto, a query completa retorna todos os tipos de quartos distintos.

***2***
```java
@Query("SELECT r FROM Room r WHERE r.id NOT IN (SELECT b.room.id FROM Booking b)")
```
Essa query está buscando todos os quartos (Room) que não estão associados a nenhuma reserva (Booking). 

Vamos detalhar:
- SELECT r FROM Room r: Seleciona todos os objetos Room e os referencia como r.
- WHERE r.id NOT IN: Filtra os quartos cuja id não está na lista especificada.
- (SELECT b.room.id FROM Booking b): Subquery que seleciona todas as id dos quartos que estão associados a alguma reserva (Booking).

Portanto, a query completa retorna todos os quartos que não estão reservados.

***3***
```java
@Query("SELECT r FROM Room r WHERE r.roomType LIKE %:roomType% AND r.id NOT IN (SELECT b.room.id FROM Booking b WHERE (b.checkInDate <= :checkOutDate) AND (b. checkOutDate >= :checkInDate))")
```
Essa query está buscando todos os quartos (Room) de um determinado tipo (roomType) que não estão reservados para um determinado período de tempo.

Vamos detalhar:

- SELECT r FROM Room r: Seleciona todos os objetos Room e os referencia como r.
- WHERE r.roomType LIKE %:roomType%: Filtra os quartos pelo tipo de quarto especificado.
- AND r.id NOT IN: Filtra os quartos cuja id não está na lista especificada.
- (SELECT b.room.id FROM Booking b WHERE (b.checkInDate <= :checkOutDate) AND (b. checkOutDate >= :checkInDate)): Subquery que seleciona todas as id dos quartos que estão reservados para o período de tempo especificado.

## Exceções
Foi criado a exceção OurException, que estende de RuntimeException, para representar exceções personalizadas na aplicação. Isso é útil para encapsular exceções específicas da aplicação e fornecer mensagens de erro personalizadas.

## Observações sobre algumas anotações e pequenas informações
### @NotNull 
A anotação @NotNull é usada para garantir que um campo não seja nulo. Se o campo for nulo, a validação falhará e uma mensagem de erro será retornada.

### @NotBlank
A anotação @NotBlank é usada para garantir que um campo não seja nulo e que contenha pelo menos um caractere não branco. Se o campo for nulo ou vazio, a validação falhará e uma mensagem de erro será retornada.

### @Future
A anotação @Future é usada para garantir que um campo contenha uma data no futuro. Se a data for anterior à data atual, a validação falhará e uma mensagem de erro será retornada.

"@Future tem que ser acompanhado com um @Past?": Não, o @Future é suficiente para garantir que a data seja no futuro.

### @Min
A anotação @Min é usada para garantir que um campo tenha um valor mínimo. Se o valor for menor que o valor mínimo especificado, a validação falhará e uma mensagem de erro será retornada.

### @ManyToOne
A anotação @ManyToOne é usada para mapear um relacionamento muitos-para-um entre duas entidades. Isso significa que uma entidade pode ter várias instâncias de outra entidade associadas a ela.

- FetcType.LAZY: Isso significa que os dados associados só serão recuperados quando forem acessados pela primeira vez.

- FetchType.EAGER: Isso significa que os dados associados serão recuperados imediatamente quando a entidade for recuperada.

#### @JoinColumn
A anotação @JoinColumn é usada para especificar a coluna de junção para a associação muitos-para-um. Isso é útil quando você deseja personalizar o nome da coluna de junção ou suas propriedades.

### @OneToMany
A anotação @OneToMany é usada para mapear um relacionamento um-para-muitos entre duas entidades. Isso significa que uma entidade pode ter várias instâncias de outra entidade associadas a ela.

- CascadeType.ALL: Isso significa que todas as operações de persistência (inserir, atualizar, excluir) serão propagadas para a entidade associada.
- CascadeType.PERSIST: Isso significa que apenas operações de persistência (inserir) serão propagadas para a entidade associada.
- CascadeType.MERGE: Isso significa que apenas operações de mesclagem (atualizar) serão propagadas para a entidade associada.
- CascadeType.REMOVE: Isso significa que apenas operações de remoção (excluir) serão propagadas para a entidade associada.
- CascadeType.REFRESH: Isso significa que apenas operações de atualização (refresh) serão propagadas para a entidade associada.
- CascadeType.DETACH: Isso significa que apenas operações de desanexação (detach) serão propagadas para a entidade associada.

### Optional
A classe Optional é uma classe utilitária introduzida no Java 8 para representar um valor opcional. Isso significa que um valor de um objeto Optional pode ser nulo ou não nulo.

"Por exemplo, em um repository, qual a diferença entre ultilizar um Optional e um objeto normal?": O uso de um objeto Optional em um repository é uma prática recomendada, pois permite que você lide com valores nulos de forma mais segura e explícita. Isso ajuda a evitar NullPointerExceptions e a tornar o código mais legível e robusto.