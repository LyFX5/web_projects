#!/usr/bin/env python3

# Импортируем системную библиотеку Python.
# Она используется для загрузки файла 'index.html' с сервера.
# Ничего особенного устанавливать не нужно, эта библиотека устанавливается вместе с Python.

##import urllib.request

# Эта переменная содержит запрос к 'http://localhost:1234/'.
# Возможно, сейчас вы задаётесь вопросом о том, что такое 'http://localhost:1234'.
# localhost указывает на то, что программа работает с локальным сервером.
# 1234 - это номер порта, который вам предлагалось запомнить при настройке серверного кода.

##fp = urllib.request.urlopen("http://localhost:1234/")

# 'encodedContent' соответствует закодированному ответу сервера ('index.html').
# 'decodedContent' соответствует раскодированному ответу сервера (тут будет то, что мы хотим вывести на экран).

##encodedContent = fp.read()
##decodedContent = encodedContent.decode("utf8")

# Выводим содержимое файла, полученного с сервера ('index.html').

##print(decodedContent)

# Закрываем соединение с сервером.

##fp.close()



# import requests
# api_url = "https://127.0.0.1:5000/countries"
# fp = urllib.request.urlopen(api_url)
# encodedContent = fp.read()
# decodedContent = encodedContent.decode("utf8")
# print(decodedContent)
# fp.close()
# response = requests.get(api_url)
# response.json()
print("help me!")


