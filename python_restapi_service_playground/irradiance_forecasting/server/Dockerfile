
FROM python:3.10

WORKDIR /server/

ADD requirements.txt /server/

RUN python3 -m pip install --upgrade pip
RUN pip install --default-timeout=1000 -r /server/requirements.txt

ADD main.py /server/
ADD weather.db /server/
COPY app /server/app

