# Основные абстракции

Все интерфейсы содержат подробное описание в javadoc

Главная абстракция - интерфейс 
[OpenApi](./src/main/java/io/github/marattim/raif_api_guide/OpenApi.java).
Предполагается примерно такое использование
```java
OpenApi openApi = new OpenApiImpl(...);
openApi.defects().forEach(defect -> ...);
```
