FROM python:3.9-slim-buster

WORKDIR /fin-mine

COPY ./requirements.txt /fin-mine/requirements.txt

RUN pip install --no-cache-dir --upgrade -r /fin-mine/requirements.txt

COPY ./app /fin-mine/app

CMD ["uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "8000"]