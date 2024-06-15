## Identificadores de command objects (backing beans)
Nas views, backing beans são referenciados por identificadores.
No controlador:

`model.addAttribute("fabricantes", fabricantes);`

Na view:

`th:each="f: ${fabricantes}"`

Um backing bean pode ser definido como parâmetro de método em um controlador:

```
public String fabricantesSalvar(
    @ModelAttribute("fabricante") @Valid Fabricante fabricante,
    BindingResult br,
    final RedirectAttributes rAttrs
)
```

Também é possível omitir o identificador:

```
public String fabricantesSalvar(
    @ModelAttribute @Valid Fabricante fabricante,
    BindingResult br,
    final RedirectAttributes rAttrs
)
```

Neste caso, Spring atribuirá um identificador padrão o qual é igual ao nome da classe do backing bean mas com a primeira letra em minúsculo. No exemplo a seguir, o backing bean terá `tipoVeiculo` como identficador.

```
@PostMapping("/salvar")
public String tipoVeiculoSalvar(
    @ModelAttribute @Valid TipoVeiculo tipoVeiculo,
    BindingResult br,
    final RedirectAttributes rAttrs
)
```

## Operador de navegação segura
É um recurso da Spring EL. Evita a ocorrência de `NullPointerException` caso tente-se usar/acessar uma variável nula. No exemplo a seguir, o atributo value receberá `null` caso fabricante seja nulo.

`th:value="*{fabricante?.id}"`

## Thymeleaf e componentes de formulário do Shoelace
Componentes Shoelace usam tags diferentes das tags HTML de campos de formulário. Consequentemente, `th:field` não funciona nos componentes Shoelace. Desta forma, temos que definir por conta própria os valores dos atributos `name` e `value`.

<pre>
&lt;form action="#" th:action="@{/modelos/salvar}" th:object="${modelo}" method="post">
…
&lt;sl-select label="Fabricante" <b>th:value="*{fabricante?.id}" th:name="fabricante"</b>>
    &lt;sl-option th:each="f : ${fabricantes}" <b>th:value="${f.id}"</b> th:text="${f.descricao}">
        Option 1
    &lt;/sl-option>
&lt;/sl-select>
…
&lt;/form>
</pre>

## Segurança
A anotação `@EnableWebSecurity` aplica a configuração padrão de segurança do Spring para uma aplicação web - todos os endpoints (URLs?) da aplicação só podem ser acessadas por usuários autenticados. Note que é necessário usar `@Configuration` na classe de configuração do Spring Security.
A anotação `@EnableMethodSecurity` habilita o uso das anotações de configuração para autorização de acesso.

### Logout
A implementação padrão define a URL `/logout`. Uma requisição GET `/logout` encaminha a aplicação para uma página de confirmação e NÃO invalida a autenticação do usuário. Para isso, deve ser realizada uma requisição POST `/logout`. Caso a proteção CSRF esteja ativada, é ncessário incluir o token CSRF na requisição POST.

## Submissão de formulários
Formulários submetidos por meio de HTTP GET não necessitam do token CSRF.
Formulários submetidos por meio de HTTP POST (e os outros métodos?) necessitam do token CSRF. Este token é inserido automaticamente pelo Thymeleaf quando a URL do formulário é indicada através do atributo `th:action`.

## Testes
`spring-boot-starter-test` “Starter”, which imports both Spring Boot test modules as well as JUnit Jupiter (Junit 5), AssertJ, Hamcrest, and a number of other useful libraries.

The `spring-boot-starter-test` “Starter” (in the test scope) contains the following provided libraries:

- JUnit 5: The de-facto standard for unit testing Java applications.
- Spring Test & Spring Boot Test: Utilities and integration test support for Spring Boot applications.
- AssertJ: A fluent assertion library.
- Hamcrest: A library of matcher objects (also known as constraints or predicates).
- Mockito: A Java mocking framework.
- JSONassert: An assertion library for JSON.
- JsonPath: XPath for JSON.
- Awaitility: A library for testing asynchronous systems.

Outras bibliotecas de testes podem ser usadas.

The `@SpringBootTest` annotation works by creating the `ApplicationContext` used in your tests through `SpringApplication`. In addition to `@SpringBootTest` a number of other annotations are also provided for testing more specific slices of an application. By default, `@SpringBootTest` will not start a server. You can use the `webEnvironment` attribute of `@SpringBootTest` to further refine how your tests run.

If you are familiar with the Spring Test Framework, you may be used to using `@ContextConfiguration(classes=…)` in order to specify which Spring `@Configuration` to load. When testing Spring Boot applications, this is often not required. Spring Boot’s `@*Test` annotations search for your primary configuration automatically whenever you do not explicitly define one.

The search algorithm works up from the package that contains the test until it finds a class annotated with `@SpringBootApplication` or `@SpringBootConfiguration`. As long as you structured your code in a sensible way, your main configuration is usually found.

## `@DataJpaTest`
You can use the `@DataJpaTest` annotation to test JPA applications. By default, it scans for `@Entity` classes and configures Spring Data JPA repositories. If an embedded database is available on the classpath, it configures one as well. SQL queries are logged by default by setting the `spring.jpa.show-sql` property to `true`. This can be disabled using the `showSql` attribute of the annotation.

Regular `@Component` and `@ConfigurationProperties` beans are not scanned when the `@DataJpaTest` annotation is used. `@EnableConfigurationProperties` can be used to include `@ConfigurationProperties` beans. By default, data JPA tests are transactional and roll back at the end of each test. If that is not what you want, you can disable transaction management for a test or for the whole class.

The standard properties file that Spring Boot picks up automatically when running an application is called `application.properties`. It resides in the `src/main/resources` folder. If we want to use different properties for tests, we can override the properties file in the main folder by placing another file with the same name in `src/test/resources`. The `application.properties` file in the `src/test/resources` folder should contain the standard key-value pairs necessary for configuring a data source. These properties are prefixed with spring.datasource.