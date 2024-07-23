# На всякий случай напоминаю, что Dockerfile всегда должен начинаться с импорта базового образа.
# Для этого используется ключевое слово 'FROM'.
# Здесь нам нужно импортировать образ python (с DockerHub).
# В результате мы, в качестве имени образа, указываем 'python', а в качестве версии - 'latest'.

FROM python:3.10

# Для того чтобы запустить в контейнере код, написанный на Python, нам нужно импортировать файлы 'app.py' и 'index.html'.
# Для того чтобы это сделать, мы используем ключевое слово 'ADD'.
# Первый параметр, 'app.py', представляет собой имя файла, хранящегося на компьютере.
# Второй параметр, '/server/', это путь, по которому нужно разместить указанный файл в образе.
# Здесь мы помещаем файл в папку образа '/server/'.

WORKDIR /server/

ADD requirements.txt /server/

RUN python3 -m pip install --upgrade pip
RUN pip install -r /server/requirements.txt

ADD main.py /server/
ADD db_api.py /server/
ADD loading_api.py /server/
ADD web_app.py /server/
ADD subtitles.db /server/
COPY ./templates/base.html /server/templates/base.html
COPY ./templates/find_usage_of_text.html /server/templates/find_usage_of_text.html
COPY ./templates/show_usages.html /server/templates/show_usages.html

# Здесь мы воспользуемся командой 'WORKDIR', возможно, новой для вас.
# Она позволяет изменить рабочую директорию образа.
# В качестве такой директории, в которой будут выполняться все команды, мы устанавливаем '/server/'.



# устанавливаем требуемые зависимости



