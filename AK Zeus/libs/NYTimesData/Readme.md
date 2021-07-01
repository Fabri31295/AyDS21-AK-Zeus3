# NYTimesData 📰
NYTimesData  es una librería que obtiene información de un artista a través del API del diario New York Times, la información buscada contiene:
- 👉 Información del artista
- 👉 URL del artículo donde se encontró esta información
- 👉 La URL de la imágen del logo del diario "The New York Times"

# Instalación 🦾
1. Incluir la librería en la carpeta libs.
2. Incluir la librería en settings.gradle de la siguiente manera:
```
include ':NYTimesData'
project(':NYTimesData').projectDir = new File(rootDir, 'libs/NYTimesData/NYTimesData/')
```
3. Incluir la librería en build.gradle:
```
implementation project(':NYTimesData')
```
4. Debería de mostrarse una opción para sincronizar el proyecto ("Sync Now"), seleccionarla.
5. Listo! ✅

# Uso 🙌
1. Obtener el servicio mediante su módulo 🤲 :
```
val nyTimesArticleService = NYTimesModule.nyTimesArticleService
```
2. Se puede obtener artículos a través de la función `getArticle(artistName)` de NYTimesArticleService
```
val article = nyTimesArticleService.getArticle("Drake")
```
El artículo va a contener toda la información mencionada anteriormente y estará disponible para utilizarla.


## Casos a tener en cuenta ✍️
1. En caso de no tener conexión a internet o no encontrar información del artista, se retornará un artículo vacio (EmptyArticle 🤷‍♂️ ).