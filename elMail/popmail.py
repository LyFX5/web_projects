#!/usr/local/bin/python

import poplib, getpass, sys, mailconfig

mailserver = mailconfig.popservername  # например: 'pop.rmi.net'
mailuser   = mailconfig.popusername     # например: 'lutz
mailpasswd = getpass.getpass('Password for %s?' % mailserver)

print(mailpasswd)


print('Connecting...')
server = poplib.POP3(mailserver)
server.user(mailuser)                   # соединение, регистрация на сервере
server.pass_(mailpasswd)                # pass ­ зарезервированное слово

try:
    print(server.getwelcome())  # вывод приветствия
    msgCount, msgBytes = server.stat()
    print('There are', msgCount, 'mail messages in', msgBytes, 'bytes')
    print(server.list())
    print('_' * 80)
    input('[Press Enter key]')

    for i in range(msgCount):
        hdr, message, octets = server.retr(i+1)    # octets ­ счетчик байтов
        for line in message: print(line.decode())  # читает, выводит
        print('_' * 80)                 # в 3.X сообщения ­ bytes
        if i < msgCount - 1:            # почтовый ящик блокируется
            input('[Press Enter key]')  # до вызова quit
finally:                                # снять блокировку с ящика
    server.quit()  # иначе будет разблокирован
print('Bye.')                           # по тайм­ауту