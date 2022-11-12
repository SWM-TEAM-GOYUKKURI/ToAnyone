FROM python:3.9.13

# COPY . /app
WORKDIR /src 
COPY requirements.txt requirements.txt
RUN pip install --upgrade pip
RUN pip install --no-cache-dir -r requirements.txt
COPY . .
EXPOSE 5000
CMD ["app.py"]
ENTRYPOINT [ "python3" ]