# Реализация линтера с помощью llm

## Использование

```java
OpenApi api = new OpenApiUsingLlm(
    new FullSpec(
        """
            openapi: 3.0.0
            paths:
              /user:
                get:
                  responses:
                    200:
                      description: OK
            """
    ),
    System.getenv("LLM_API_KEY")
);
api.

defects().

forEach(
    defect ->...
    );
```

## Детали реализации

Для взаимодействия с llm используется библиотека
[langchain4j](https://github.com/langchain4j/langchain4j), 
поэтому есть совместимость как с моделями openai, 
так и с другими, например с локальной ollama

Главный класс - 
[OpenApiUsingLlm](./src/main/java/io/github/marattim/raif_api_guide/llm_impl/OpenApiUsingLlm.java).
Для его работы необходимо 3 вещи:
- Набор правил
- Open api спецификация, разделенная на части
- Пайплайн обработки одного правила и части спецификации

В нем предусмотрен конструктор без аргументов.
Он задает параметры, которые показали себя лучше всего в экспериментах

Пример использования можно найти в юнит тестах 
или в модуле [integration-tests](./../integration-tests)

В проекте 3 основных пакета:
- [part](./src/main/java/io/github/marattim/raif_api_guide/llm_impl/part),
в котором содержатся необходимые классы для разбиения openapi спецификации на части. 
Главный класс - [FullSpec](./src/main/java/io/github/marattim/raif_api_guide/llm_impl/part/FullSpec.java),
с помощью которого можно превратить большой файл 
или разделенную по папкам и файликам спецификацию в коллекцию 
[SpecPart](https://github.com/Marat-Tim/Raiffeisen-ApiGuideLinterUsingLlm/blob/07f61d9aed1f1431c6cf8b6d216f7eb5f5eacc0f/core/src/main/java/io/github/marattim/raif_api_guide/SpecPart.java)
- [prompt](./src/main/java/io/github/marattim/raif_api_guide/llm_impl/prompt),
в котором находится пример реализации [простого промпта](./src/main/java/io/github/marattim/raif_api_guide/llm_impl/prompt/OnlyLinesPrompt.java) 
- [rule](./src/main/java/io/github/marattim/raif_api_guide/llm_impl/rule),
в котором есть парсинг [списка всех правил из репозитория Райфа](https://github.com/Raiffeisen-DGTL/rest-api-guide/blob/main/rules/rules.md)

## Проблемы

- В основном я использую внутри банковские модели для экспериментов.
  По соображения безопасности я не хочу публиковать их url, поэтому использую
  ```java
  System.getenv("LLM_API_URL")
  ```
