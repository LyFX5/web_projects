
popservername = 'pop.secureserver.net'
popusername = 'ed@learning_python.com'
smtpservername = 'smtpout.secureserver.net'

myaddress = 'ed@learning_python.com'
mysignature = ('Thanks,\n'    'Eduard')


smtpuser = None        # зависит от провайдера
smtppasswdfile = ''    # установите в значение '', чтобы обеспечить запрос



poppasswdfile = '' # установите в значение '', r'c:\temp\pymailgui.txt'
                                         # чтобы обеспечить запрос



sentmailfile = r'.\sentmail.txt'        # . означает текущий рабочий каталог
savemailfile = r'c:\temp\savemail.txt'  # не используется в PyMailGUI: диалог

fetchEncoding = 'utf8' # 4E: как декодировать и хранить текст сообщений
                       # (или latin1?)
headersEncodeTo = None # 4E: как кодировать не­ASCII заголовки при отправке
                       # (None=utf8)

fetchlimit = 25    # 4E: максимальное число загружаемых заголовков/сообщений



