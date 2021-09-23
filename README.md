[![Build status](https://ci.appveyor.com/api/projects/status/ddjoa0kja2xtvy5q/branch/master?svg=true)](https://ci.appveyor.com/project/AnastasiaIQA12/automation5-2/branch/master)
# Домашнее задание к занятию «2.3. Patterns»

## Настройка CI   
Настройка CI осуществляется аналогично предыдущему заданию.

## Задача №2 - Тестовый режим

Разработчики Интернет Банка изрядно поворчав предоставили вам тестовый режим запуска целевого сервиса, в котором открыта программная возможность создания Клиентов Банка, чтобы вы могли протестировать хотя бы функцию входа.

**Важно**: задача заключается в том, чтобы протестировать функцию входа через Web интерфейс с использованием Selenide.

Для удобства вам предоставили "документацию", которая описывает возможность программного создания Клиентов Банка через API. Вот представленное ими описание (дословно):
```
Для создания клиента нужно делать запрос вида:

POST /api/system/users
Content-Type: application/json

{
    "login": "vasya",
    "password": "password",
    "status": "active" 
}

Возможные значения поля статус:
* "active" - пользователь активен
* "blocked" - пользователь заблокирован

В случае успешного создания пользователя возвращается код 200

При повторной передаче пользователя с таким же логином будет выполнена перезапись данных пользователя
```

В логах теста вы увидите:
```
Request method:	POST
Request URI:	http://localhost:9999/api/system/users
Proxy:			<none>
Request params:	<none>
Query params:	<none>
Form params:	<none>
Path params:	<none>
Headers:		Accept=application/json, application/javascript, text/javascript, text/json
				Content-Type=application/json; charset=UTF-8
Cookies:		<none>
Multiparts:		<none>
Body:
{
    "login": "vasya",
    "password": "password",
    "status": "active" 
}
```

Для активации этого тестового режима при запуске SUT нужно указать флаг `-P:profile=test`, т.е.:
`java -jar app-ibank.jar -P:profile=test`.

**Важно:** если вы не активируете тестовый режим, любые запросы на http://localhost:9999/api/system/users будут вам возвращать 404 Not Found. 

Нужно самостоятельно изучить реакцию приложения на различные комбинации случаев:
* наличие пользователя;
* статус пользователя;
* невалидный логин;
* невалидный пароль.
