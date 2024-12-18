# language: ru

@all
Функция: Тестирование страницы продуктов

  Предыстория:
    * пользователь открывает страницу продуктов

  @correct
  Сценарий: Добавление и удаление товара
    * открывает выпадающий список
    * открывает список товаров
    * список товаров открылся
    * пользователь добавляет товар "Огурец" типа "Овощ" с чекбоксом "false"
    * товар "Огурец" должен быть добавлен на страницу
    * товар "Огурец" должен быть добавлен в БД как "VEGETABLE" с экзотичностью 0
    * пользователь добавляет товар "Маракуйя" типа "Фрукт" с чекбоксом "true"
    * товар "Маракуйя" должен быть добавлен на страницу
    * товар "Маракуйя" должен быть добавлен в БД как "FRUIT" с экзотичностью 1
    * пользователь сбрасывает товары
    * товары должны быть удалены на сайте
    * товары должны быть удалены из БД
    * закрывает браузер и соединение с БД


  @fail
  Сценарий: Добавление уже существующего товара
    * открывает выпадающий список
    * открывает список товаров
    * пользователь добавляет товар "Яблоко" типа "Фрукт" с чекбоксом "false"
    * товар "Яблоко" не должен быть добавлен в БД как "Фрукт" с экзотичностью 0
    * закрывает браузер и соединение с БД

